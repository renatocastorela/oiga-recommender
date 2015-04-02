#!/usr/bin/python
from elasticsearch import Elasticsearch
from py2neo import neo4j
import re

#Cypher query
query = """
    START n=node(*)
    MATCH n
    WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.Event"
    RETURN n 
    """

#Expresion regular para extrar un punto de wkt 
wktReg = "POINT\( (-?\d+\.\d*) (-?\d+\.\d*) \)"
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
    m = re.search(wktReg, adress["wkt"]);
    point = [float(m.group(1)), float(m.group(2))];
    body = {
        "uuid" : node["uuid"],
        "name" : node["name"],
        "description" : node["description"],
        "host" : node["host"],
        "location" : node["location"],
        "venue_name" : simple_venue["name"],
        "wkt" : point,
        "start_date" : node["startDate"],
        "end_date" : node["endDate"]
    };
    es.index(index="events", doc_type="event", id=node["uuid"], body=body );
    for rel in list(node.match(rel_type="CATEGORIZED")):
        category = rel.end_node
        es.index(index="events", doc_type="category", parent=node["uuid"], body= { "category" : category["name"]});
 


