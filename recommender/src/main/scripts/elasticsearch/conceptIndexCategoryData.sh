curl -XPOST 'localhost:9200/events/category?parent=1' -d '{ "category" : "/musica/pop" }'
curl -XPOST 'localhost:9200/events/category?parent=1' -d '{ "category" : "/musica/rock" }'
curl -XPOST 'localhost:9200/events/category?parent=1' -d '{ "category" : "/musica/salsa" }'
curl -XPOST 'localhost:9200/events/category?parent=2' -d '{ "category" : "/musica/rock" }'
curl -XPOST 'localhost:9200/events/category?parent=2' -d '{ "category" : "/musica/salsa" }'
curl -XPOST 'localhost:9200/events/category?parent=2' -d '{ "category" : "/cine/terror" }'
curl -XPOST 'localhost:9200/events/category?parent=3' -d '{ "category" : "/cine/infantil" }'
curl -XPOST 'localhost:9200/events/category?parent=3' -d '{ "category" : "/cine/comedia" }'
curl -XPOST 'localhost:9200/events/category?parent=3' -d '{ "category" : "/teatro/musical" }'
