#!/usr/bin/python
from elasticsearch import Elasticsearch
import json;
import pprint;

q = "UNAM";
iniDate = "1390716000000";
endDate = "1391148000000";
distance = "200km"
lon = "-99.13";
lat = "19.43";
json_string = open("event_query.json").read() \
    .replace("@query",q) \
    .replace("@iniDate", iniDate) \
    .replace("@endDate", endDate) \
    .replace("@distance", distance) \
    .replace("@lat", lat) \
    .replace("@lon", lon);
print(json_string)
json_data = json.loads(json_string); 
es = Elasticsearch();

result = es.search(index="events", doc_type="event", body=json_data );
print json.dumps(result, indent=4);    

