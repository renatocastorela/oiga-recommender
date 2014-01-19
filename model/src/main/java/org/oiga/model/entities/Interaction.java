package org.oiga.model.entities;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="INTERACTS")
public class Interaction {
	public enum State{ATTENDING, DECLINED, MAYBE, NOREPLY, NOT_INVITED};
	private Boolean liked;
	private Integer views;
	private Date lastInteraction;
	private State state;
	@StartNode
	private User user;
	@EndNode
	private Event event;
	
	
}
