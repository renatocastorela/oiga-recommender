package org.oiga.repositories;

import java.util.Map;

import org.oiga.model.entities.EventCategory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface EventCategoryRepository extends GraphRepository<EventCategory>{
	@Query("START e=node(*)  MATCH e-[:CATEGORIZED]->v RETURN v.name as name, count( e ) as count")
	Iterable<Map<String,Object>> getCategoriesCount();
	@Query( "START n=node(*) "
			+ "MATCH n-[:HAS]->() "
			+ "WHERE has(n.__type__) AND n.__type__ = 'org.oiga.model.entities.EventCategory' "
			+ "AND NOT( n<-[:HAS]-() ) "
			+ "return DISTINCT n ")
	EndResult<EventCategory> findParentCategories();
	@Query( "START n=node(*) "
			+ "MATCH n-[:HAS]->s WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.EventCategory' "
			+ "and n.hyphen = {0} return DISTINCT s ")
	EndResult<EventCategory> findSubcategories(String cateogory);
	EventCategory findByPath(String path);
	@Query( "START n=node(*) "
			+ "MATCH n WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.EventCategory' "
			+ "and n.hyphen = {0} return DISTINCT n LIMIT 1 ")
	EventCategory findByHyphen(String hyphen);
}
