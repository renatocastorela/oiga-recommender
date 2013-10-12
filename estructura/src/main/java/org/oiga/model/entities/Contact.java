package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Contact {
	@GraphId
	private Long nodeId;
	private String twitter;
	private String phone;
	private String formattedPhone;
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFormattedPhone() {
		return formattedPhone;
	}
	public void setFormattedPhone(String formattedPhone) {
		this.formattedPhone = formattedPhone;
	}
}
