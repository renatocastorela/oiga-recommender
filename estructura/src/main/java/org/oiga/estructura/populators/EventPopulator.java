package org.oiga.estructura.populators;

import java.util.List;

import org.oiga.model.entities.Event;
import org.oiga.model.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;

public class EventPopulator extends AbstractPopulator<Event>{
	@Autowired
	private EventService eventService;

	@Override
	public void populate() {
		List<Event> events = super.getData(); 
		eventService.getEventRepository().save(events);
		
	}
	
	
}
