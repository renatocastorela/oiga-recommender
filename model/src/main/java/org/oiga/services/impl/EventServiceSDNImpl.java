/**
 * 
 */
package org.oiga.services.impl;

import javax.inject.Inject;

import org.oiga.model.entities.Event;
import org.oiga.repositories.EventRepository;
import org.oiga.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @author jaime.renato
 *
 */
@Service
public class EventServiceSDNImpl implements EventService{
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(EventService.class);
	/**
	 * Repositorio de eventos
	 */
	@Inject
	private EventRepository eventRepository;
	
	/*
	 * (non-Javadoc)
	 * @see org.oiga.services.EventService#findByUuid(java.lang.String)
	 */
	@Override
	public Event findByUuid(String uuid){
		return eventRepository.findByUuid(uuid);
	}
}
