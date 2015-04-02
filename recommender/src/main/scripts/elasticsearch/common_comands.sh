#Busquedas
curl -XGET 'localhost:9200/events/category/_search?pretty=1' -d '{ query: { term: { category : "pop" } }}'
curl -XGET 'localhost:9200/events/_search?pretty=1' -d '{ query:{ has_child: { type: "category", query: { term: { category : "musica" } }}  } }'
#Merge mapeos:
curl -XPUT 'localhost:9200/events/event/_mapping' -d @event_mapping.json
curl -XPUT 'localhost:9200/events/category/_mapping' -d @event_category_mapping.json
#Consulta de mapeo
curl -XGET 'localhost:9200/events/event/_mapping?pretty=1' 
#box filtering
curl -XGET 'localhost:9200/events/event/_search?pretty=1' -d '{filter:{ geo_bounding_box: {location:{top_left:"100.47, -1.903", bottom_right: "200.85, 200.305"} } }}'
#Añadiendo configuracion settings
curl -XPUT 'localhost:9200/events/event/' -d @event_setting.json
#Nuevo documento
curl -XPOST 'localhost:9200/events/event' -d @event_document_test_mapping.json
curl -XPOST 'localhost:9200/events/category' -d @event_category_mapping.json
#Retrieve everything in the index:
curl "http://localhost:9200/events/_search?pretty=true"
#Borrar index
curl -XDELETE 'localhost:9200/events/event'
#Consultar mapping
curl -XGET 'localhost:9200/events/category/_mapping?pretty=true'

#Nuevo mapeo
curl -XPUT 'localhost:9200/events/category/_mapping' -d @event_category_mapping.json
