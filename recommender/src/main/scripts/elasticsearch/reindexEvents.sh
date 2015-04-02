#!/bin/bash

./deleteAllEventCategoryDocuments.sh
./deleteAllEventDocuments.sh
curl -XDELETE 'localhost:9200/events/event'
curl -XDELETE 'localhost:9200/events/category'
curl -XPUT 'localhost:9200/events/event/_mapping' -d @event_mapping.json
curl -XPUT 'localhost:9200/events/category/_mapping' -d @event_category_mapping.json
./exportEventsFromtNeo4jToElasticsearchWithCompletion.py
./exportEventCategoryFromNeo4jToElasticSearch.py 

