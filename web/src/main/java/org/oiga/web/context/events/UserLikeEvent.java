package org.oiga.web.context.events;

import org.oiga.model.entities.Event;
import org.springframework.context.ApplicationEvent;

public class UserLikeEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param source
	 */
	public UserLikeEvent(Object source) {
		super(source);
	}	
	
	public Event getEvent(){
		return (Event) source;
	}
}
