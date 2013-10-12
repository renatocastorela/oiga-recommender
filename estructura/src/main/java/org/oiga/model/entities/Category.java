package org.oiga.model.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class Category {
	@GraphId 
	private Long nodeId;
	private Long id;
	@Indexed
	private String name;
	private String description;
	private String icon;
	@RelatedToVia(type="subcategory", direction=Direction.BOTH) @Fetch 
	private Set<Category> subcategories = new HashSet<Category>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Set<Category> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<Category> subcategories) {
		this.subcategories = subcategories;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
}
