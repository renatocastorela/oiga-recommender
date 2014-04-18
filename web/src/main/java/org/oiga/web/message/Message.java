package org.oiga.web.message;

public class Message {
	private final MessageType type;
	private final String text;
	
	public Message(MessageType type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public MessageType getType() {
		return type;
	}
	public String getText() {
		return text;
	}
	
	public String toString() {
		return type + ": " + text;
	}
	
}
