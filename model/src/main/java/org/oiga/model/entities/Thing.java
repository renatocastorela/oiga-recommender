package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

public class Thing {
	@GraphId Long nodeId;
	String name;
	String description;
		
}
