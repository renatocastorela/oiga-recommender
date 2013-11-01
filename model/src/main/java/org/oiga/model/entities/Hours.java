package org.oiga.model.entities;

import java.util.List;

import org.springframework.data.neo4j.annotation.NodeEntity;

public class Hours {
	private String status;
	private boolean open;
	/*TODO:Agregar timeframes*/
	private List<String> segments;
	private String lable;
	private String renderedTime;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<String> getSegments() {
		return segments;
	}
	public void setSegments(List<String> segments) {
		this.segments = segments;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getRenderedTime() {
		return renderedTime;
	}
	public void setRenderedTime(String renderedTime) {
		this.renderedTime = renderedTime;
	}
}
