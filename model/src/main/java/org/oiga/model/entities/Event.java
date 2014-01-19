package org.oiga.model.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.format.annotation.DateTimeFormat;

@NodeEntity
public class Event {
	@GraphId
	private Long nodeId;
	@Indexed(indexType=IndexType.FULLTEXT, indexName="event_name")
	private String name;
	@Indexed(indexType=IndexType.FULLTEXT, indexName="event_description")
	private String description;
	private String url;
	private String host;
	private String hoursDetails;
	private String location;
	private String locationAdress;
	private String picture;
	private String audience;
	private String ticketPrices;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	private Date updatedTime = new Date();
	@RelatedTo(type="PERFORMED")
	@Fetch
	private SimpleVenue venue;
	@RelatedToVia(type="INTERACTS")
	private Set<Interaction> interactions;
	@RelatedTo(type = "SOURCED")
	@Fetch	
	private Repository repository;
	private List<String> tags;
	private List<String> otherDetails;
	private List <String> hours;
	@RelatedTo(type = "CATEGORIZED")
	@Fetch	
	private List<EventCategory> categories;
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getTicketPrices() {
		return ticketPrices;
	}
	public void setTicketPrices(String ticketPrices) {
		this.ticketPrices = ticketPrices;
	}
	public Set<Interaction> getInteractions() {
		return interactions;
	}
	public void setInteractions(Set<Interaction> interactions) {
		this.interactions = interactions;
	}
	public List<String> getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(List<String> otherDetails) {
		this.otherDetails = otherDetails;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getLocationAdress() {
		return locationAdress;
	}
	public void setLocationAdress(String locationAdress) {
		this.locationAdress = locationAdress;
	}
	public String getHoursDetails() {
		return hoursDetails;
	}
	public void setHoursDetails(String hoursDetails) {
		this.hoursDetails = hoursDetails;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public SimpleVenue getVenue() {
		return venue;
	}
	public void setVenue(SimpleVenue venue) {
		this.venue = venue;
	}
	public List<EventCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<EventCategory> categories) {
		this.categories = categories;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getHours() {
		return hours;
	}
	public void setHours(List<String> hours) {
		this.hours = hours;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
