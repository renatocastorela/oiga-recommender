#!/usr/bin/python
from py2neo import neo4j
import uuid

#Cypher query
query= """
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.SimpleVenue"
RETURN n
"""
#Conexion a Neo4j 
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/")
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();

for n in nodes[0]:
    node = n.values[0];
    hyphen = node['name'].encode("utf-8").replace(" ", "-").lower();
    uuid5 = uuid.uuid5(uuid.NAMESPACE_URL, hyphen + node['foursquareId'].encode("utf-8") + str(node._id) );
    str_uuid = str( uuid5.hex );
    properties = { 'uuid' : str_uuid , 'hyphen' : hyphen};
    print properties;
    node.update_properties( properties );
   # print node['uuid'] +'-> '+ url;

