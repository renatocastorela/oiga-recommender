package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Tag {
	@GraphId
	private Long nodeId;
	private String keyword;
	private Integer count;

	/**
	 * @param keyword
	 * @param count
	 */
	public Tag(String keyword, Integer count) {
		super();
		this.keyword = keyword;
		this.count = count;
	}
			
	/**
	 * 
	 */
	public Tag() {
		super();
	}



	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
