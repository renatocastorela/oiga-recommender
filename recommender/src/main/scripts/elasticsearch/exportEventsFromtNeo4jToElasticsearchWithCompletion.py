#!/usr/bin/python
from elasticsearch import Elasticsearch
from py2neo import neo4j
import re
import json

def get_prefix_inputs(input):
    inputs = re.split(delReg, input, flags=re.UNICODE )
    inputs.append(input)
    return filter(None, inputs) 

def get_full_category_path(node):
    path = []
    traverse_category(node, path)
    return path;

def traverse_category(node, path):
    path.insert(0, node["name"] )
    try: 
        rel = next(node.match_incoming(rel_type="HAS") );
        if rel.start_node._id == node._id :
            return
        traverse_category( rel.start_node, path )
    except StopIteration:
        pass


#Cypher query
query = """
    START n=node(*)
    MATCH n
    WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.Event"
    RETURN n 
    """

#Expresion regular para extrar un punto de wkt 
wktReg = "POINT\( (-?\d+\.\d*) (-?\d+\.\d*) \)"
delReg = "\W+"
#Conexion a Neo4j
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/");
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();

#Conexion con elasticsearch
es = Elasticsearch();

# Cuidado! el comportamiento de nodes cambia cuando solo viene un resultado.
#Creacion de documentos en elastic search
for n in nodes[0]:
    node = n.values[0];
    simple_venue = next(node.match(rel_type="PERFORMED")).end_node; 
    adress = next( simple_venue.match(rel_type="IS_LOCATED") ).end_node
    organizer = next(node.match(rel_type="HOSTED")).end_node; 
    m = re.search(wktReg, adress["wkt"]);
    point = [float(m.group(1)), float(m.group(2))];
    body = {
        "uuid" : node["uuid"],
        "name" : node["name"],
        "name_suggest" : {
            "input" : get_prefix_inputs(node["name"]),
            "output" : node["name"],
        },
        "hyphen" : node["hyphen"],
        "description" : node["description"],
        "organizer" : organizer["name"],
        "organizer_hyphen" : organizer["hyphen"],
        "organizer_uuid" : organizer["uuid"],

        "organizer_suggest" :  {
            "input" : filter(None, re.split(delReg, node["host"], flags=re.UNICODE)),
            "output" : node["host"],
        },
        "location" : node["location"],
        "venue_name" : simple_venue["name"],
        "venue_uuid" : simple_venue["uuid"],
        "venue_hyphen" : simple_venue["hyphen"],
        "foursquareId" : simple_venue["foursquareId"],
        "venue_name_suggest" :  {
            "input" : filter(None, re.split(delReg, simple_venue["name"], flags=re.UNICODE)),
            "output" : simple_venue["name"],
        },
        "wkt" : point,
        "start_date" : node["startDate"],
        "end_date" : node["endDate"]
    };
    print "Indexando evento: {0} " .format( str( node._id )+ ":"+node["name"].encode('utf-8')  )
    categories = []
    for rel in list(node.match(rel_type="CATEGORIZED")):
        category = rel.end_node
        path= get_full_category_path(category)
        category = {
            "category" : category["path"],
            "hyphen" : category["hyphen"],
            "uuid" : category["uuid"],
            "icon": category["icon"],
            "color" : category["color"]

        }
        categories.append(category)
    body["categories"] =  categories
    es.index(index="events", doc_type="event", id=node["uuid"], body=body );
#   print [ x["category"].encode('utf-8') for x in categories ] 
#   es.index(index="events", doc_type="category", parent=node["uuid"], body=body);
#   print "\t > {0} ".format( category["name"].encode('utf-8') )

