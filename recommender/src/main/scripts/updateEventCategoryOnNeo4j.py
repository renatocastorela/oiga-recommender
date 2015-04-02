#!/usr/bin/python
from py2neo import neo4j
import uuid

#Cypher query
query= """
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.EventCategory"
RETURN n
"""
#Conexion a Neo4j 
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/")
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();

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



for n in nodes[0]:
    #n.update_properties(
    #    "uuid" : 
    #);
    node = n.values[0];
    category_list = get_full_category_path(node);
    path = "/"+"/".join(category_list);
    path = path.encode("utf-8").replace(" ","-").lower();
    hyphen = node['name'].encode("utf-8").replace(" ", "").lower();
    uuid5 = uuid.uuid5(uuid.NAMESPACE_URL, path );
    str_uuid = str( uuid5.hex );
    properties = { 'uuid' : str_uuid , 'path' : path, 'hyphen' : hyphen};
    print properties;
    node.update_properties( properties );
   # print node['uuid'] +'-> '+ url;

