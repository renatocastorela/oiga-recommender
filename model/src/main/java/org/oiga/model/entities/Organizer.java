package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Organizer {
	@GraphId
	private Long nodeId;
	@Indexed(indexType=IndexType.UNIQUE, indexName="organizer_uuid")
	private String uuid;
	private String name;
	private String hyphen;
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHyphen() {
		return hyphen;
	}
	public void setHyphen(String hyphen) {
		this.hyphen = hyphen;
	}
	
	
}
