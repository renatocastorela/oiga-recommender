#!/usr/bin/python
from py2neo import neo4j
import uuid

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

for n in nodes[0]:
    #n.update_properties(
    #    "uuid" : 
    #);
    node = n.values[0];
    #TODO: Agregar canonical name
    url = 'oiga.mx/'+ str(node['updatedTime']);
    uuid5 = uuid.uuid5(uuid.NAMESPACE_URL, url );
    str_uuid = str( uuid5.hex );
    node.update_properties( { 'uuid' :  str_uuid } );
    print node['uuid'] +'-> '+ url;

