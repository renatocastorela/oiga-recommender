package org.oiga.web.context.events;

import org.oiga.model.entities.Event;
import org.springframework.context.ApplicationEvent;

public class UserRateEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	private double score;
	
	public UserRateEvent(Object source, double score) {
		super(source);
		this.score = score;
	}
	
	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	
	
	
	public Event getEvent(){
		return (Event) source;
	}

}
