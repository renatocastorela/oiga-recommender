package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class SimpleVenue {
	@GraphId
	private Long nodeId;
	private String uuid;
	private String hyphen;
	private String foursquareId;
	@Indexed(indexName="venue_name")
	private String name;
	@RelatedTo(type = "IS_LOCATED")
	@Fetch
	private Adress adress;
	
	public String getFoursquareId() {
		return foursquareId;
	}
	public void setFoursquareId(String foursquareId) {
		this.foursquareId = foursquareId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Adress getAdress() {
		return adress;
	}
	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getHyphen() {
		return hyphen;
	}
	public void setHyphen(String hyphen) {
		this.hyphen = hyphen;
	}
	
}
