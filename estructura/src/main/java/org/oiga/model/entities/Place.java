package org.oiga.model.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Place {
	@GraphId
	private Long nodeId;
	private String id;
	@Indexed
	private String name;
	@RelatedTo(type = "IS_CONTACTED")
	private Contact contact;
	@RelatedTo(type = "IS_LOCATED")
	private Adress adress;
	
	@Indexed(indexType=IndexType.POINT, indexName="geo_location")
	private String geo;
	@Fetch @RelatedTo(type = "CATEGORIZED")
	private Set<Category> categories = new HashSet<Category>();
	private Boolean verified;
	@RelatedTo(type="HAS_STATS")
	private Stats stats;
	private String url;
	
	
}
