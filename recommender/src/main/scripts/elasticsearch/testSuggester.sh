#!/bin/bash

curl -XPOST 'localhost:9200/events/_search?pretty=true' -d @event_suggester.json
