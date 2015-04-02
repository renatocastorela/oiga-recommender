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
    event = n.values[0];
    for rel in event.match(rel_type="HOSTED"):
        #neo4jClient.delete( rel.end_node );
        neo4jClient.delete(rel);
    name = event['host'];
    hyphen = name.encode("utf-8").replace(" ", "-").lower();
    uuid5 = uuid.uuid5(uuid.NAMESPACE_URL, "oiga.mx/organizer/"+hyphen );
    str_uuid = str( uuid5.hex );
    properties = { 'uuid' : str_uuid , 'name' : name, 'hyphen' : hyphen, '__type__': 'org.oiga.model.entities.Organizer' };
    print properties;
    organizer, = neo4jClient.create(properties);
    rel, =  neo4jClient.create((event, "HOSTED", organizer));
    print rel;
   # node.update_properties( properties );
   # print node['uuid'] +'-> '+ url;

