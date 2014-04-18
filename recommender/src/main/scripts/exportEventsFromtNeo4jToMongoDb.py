#!/usr/bin/python
from pymongo import MongoClient
from py2neo import neo4j

#Cypher query
query = """
start u=node(*) 
match u-[i:INTERACTS]-e
where i.liked = true and has(e.__type__) and e.__type__ = "org.oiga.model.entities.Event"
return ID(u) as user_id, ID(e) as item_id, 1.0 as preference, i.lastInteraction as created_at
"""
#Conexion a Neo4j
neo4jClient = neo4j.GraphDatabaseService("http://localhost:7474/db/data/")
readBatch = neo4j.ReadBatch(neo4jClient)
readBatch.append_cypher(query)
nodes = readBatch.submit();

#Conexion a Mongo
mongoClient = MongoClient('mongodb://localhost:27017')
#Nombre de la bd 'events'
mongoDb = mongoClient.events
#Nombre de la coleccion 'likes'
likes = mongoDb.likes
for n in nodes[0]:
	like = {"user_id" : n.values[0],
		"item_id" : n.values[1],
		"preference" : n.values[2],
		"created_at" : int(n.values[3])
	}
	likes.insert(like)

