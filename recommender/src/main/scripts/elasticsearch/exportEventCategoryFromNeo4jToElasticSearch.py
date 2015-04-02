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
    WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.EventCategory"
    RETURN n 
    """
#Expresion regular para extrar un punto de wkt 
delReg = "\W+"
#Conexion a Neo4j
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/");
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();

#Conexion con elasticsearch
es = Elasticsearch();

#Creacion de documentos en elastic search
for n in nodes[0]:
    node = n.values[0];
    path= get_full_category_path(node)
    body = {
        "category" : "/"+"/".join(path),
        "category_suggest" : { 
            "input" : node["name"],
            "output" : node["name"]    
            }
    }
    print "Indexando categoria evento: {0} " .format( str( node._id )+ ":"+node["name"].encode('utf-8')  )
    es.index(index="events", doc_type="category", body=body);
#    print json.dumps( body, indent=4)
#    es.index(index="events", doc_type="event", id=node["uuid"], body=body );
#   print [ x["category"].encode('utf-8') for x in categories ] 
#   print "\t > {0} ".format( category["name"].encode('utf-8') )

