package org.oiga.web.context.events;

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

	

}
