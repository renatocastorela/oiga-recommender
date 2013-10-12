package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Stats {
	private int checkinsCount;
	private int usersCount;
	private int tipCount;
	
	
}
