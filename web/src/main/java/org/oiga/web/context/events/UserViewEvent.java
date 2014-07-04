package org.oiga.web.context.events;

import org.oiga.model.entities.Event;
import org.springframework.context.ApplicationEvent;

public class UserViewEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param source
	 */
	public UserViewEvent(Object source) {
		super(source);
	}
	
	
	public Event getEvent(){
		return (Event) source;
	}
}
