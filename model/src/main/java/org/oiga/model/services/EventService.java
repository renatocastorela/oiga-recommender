package org.oiga.model.services;

import java.util.ArrayList;
import java.util.List;

import org.oiga.model.entities.Event;
import org.oiga.model.repositories.EventRepository;
import org.oiga.vertex.services.FoursquareVenueService;
import org.oiga.vertex.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.foyt.foursquare.api.entities.CompactVenue;


@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private SimpleVenueService venueService;
	
	public List<Event> getByGeolocation(){
		ArrayList<Event> events = new ArrayList<Event>();
		
		return events;
	}
	
	public static void populate(Event ev, CompactVenue v){
		
	}

	public EventRepository getEventRepository() {
		return eventRepository;
	}

	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	
}
