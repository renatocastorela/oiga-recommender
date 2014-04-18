/*	Obtener nodos y calificaciones por nodo		*/
start u=node(*) 
match u<-[i:INTERACTS]-e
where i.liked = true
return ID(u) as user_id, ID(e) as item_id, 1.0 as preference, i.lastInteraction as created_at;

/*	Obtener un evento en un rango de tiempo dado	*/
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.Event"
AND n.updatedTime > 1390721553359 and n.updatedTime < 1390721554198
RETURN ID(n) as node_id, n.updatedTime, n.startDate ORDER BY n.startDate

/*	Obtener un nodo a una distancia dada	*/
START e=node(*), a=node:geo_location("withinDistance:[ 19.43 -99.13, 25.00]") 
MATCH e-[:PERFORMED]->(v)-[:IS_LOCATED]->a 
RETURN e

/* Obtener eventos */
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.Event"
return count(n)

/* Obtener usuarios */
START n=node(*)
MATCH n
WHERE has(n.__type__) and n.__type__ = "org.oiga.model.entities.User"
return n
