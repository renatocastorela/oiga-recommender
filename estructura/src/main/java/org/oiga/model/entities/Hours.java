package org.oiga.model.entities;

import java.util.List;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Hours {
	private String status;
	private boolean open;
	
	private List<String> segments;
	private String lable;
	private String renderedTime;
}
