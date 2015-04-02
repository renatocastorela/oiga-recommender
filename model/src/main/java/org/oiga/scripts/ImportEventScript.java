package org.oiga.scripts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.oiga.model.entities.Event;
import org.oiga.repositories.EventRepository;
import org.oiga.services.SimpleVenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ImportEventScript {
	private static final Logger LOG = LoggerFactory
			.getLogger(ImportEventScript.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private Neo4jTemplate template;
	@Autowired
	private SimpleVenueService venueService;
	@Autowired
	private EventRepository eventRepository;
	
	public static void main(String[] args) {
		
	}
	
	public void importEvents(String fileName){
		Validate.notNull(fileName);
		Validate.notEmpty(fileName);
		List<Event> events;
		try {
			events = mapper.readValue(new File(fileName), new TypeReference<List<Event>>(){});
			for (Event event : events) {
				eventRepository.save(event);
			}
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(new File(fileName), events);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
