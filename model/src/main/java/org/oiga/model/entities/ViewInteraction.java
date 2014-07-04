package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="VIEWED")
public class ViewInteraction {
	@GraphId
    private Long nodeId;
	private Long interactionTime;
	@StartNode
	private User user;
	@EndNode
	private Event event;
	
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Long getInteractionTime() {
		return interactionTime;
	}
	public void setInteractionTime(Long interactionTime) {
		this.interactionTime = interactionTime;
	}
}
