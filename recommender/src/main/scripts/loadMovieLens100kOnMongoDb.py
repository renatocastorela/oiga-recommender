#!/usr/bin/python
from pymongo import MongoClient

#Conexion a Mongo
client = MongoClient('mongodb://localhost:27017')
db = client.movieLens
ratings = db.ratings
#Lectura de archivo
dataFile = open('ml-100k/u.data','r')
for eachLine in dataFile:
	substr = eachLine.rstrip('\n').split('\t')
	rating = {"user_id" : substr[0],
		"item_id" : substr[1],
		"preference" : substr[2],
		"created_at" : substr[3]		
	}
	ratings.insert(rating)
dataFile.close()




