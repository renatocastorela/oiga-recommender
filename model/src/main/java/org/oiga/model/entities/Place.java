package org.oiga.model.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

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
	private List<String> tags;
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Adress getAdress() {
		return adress;
	}
	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public Stats getStats() {
		return stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
