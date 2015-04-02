#!/usr/bin/python
from py2neo import neo4j
from unidecode import unidecode
import uuid, re, string

#Cypher query
query= """
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.Event"
RETURN n
"""
#Conexion a Neo4j 
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/")
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();
pattern = re.compile('[\W_]+');

def to_hyphen_name(name):
    #removemos todas los caracters con acentos    
    hyphen = unidecode ( unicode(name.encode("utf-8"), "utf-8") );
    hyphen = pattern.sub(" ", hyphen);
    hyphen = " ".join(hyphen.split()).strip();
    hyphen = hyphen.replace(" ","-").lower();
    return hyphen;

for n in nodes[0]:
    #n.update_properties(
    #    "uuid" : 
    #);
    node = n.values[0];
    hyphen  = to_hyphen_name( node["name"] ); 
    properties = { 'hyphen': hyphen }
    node.update_properties(properties); 
    #node.update_properties( { 'uuid' :  str_uuid } );
    print node['name'] +'-> '+ node['hyphen']; 

