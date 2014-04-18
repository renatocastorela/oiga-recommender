package org.oiga.web.message;

public enum MessageType {
	INFO, 
	SUCCESS, 
	WARNING, 
	ERROR;

	private final String cssClass;

	private MessageType() {
		cssClass = name().toLowerCase(); 
	}
	
	public String getCssClass() {
		return cssClass;
	}

}
