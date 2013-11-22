package org.oiga.model.repositories;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.collections.graphdb.impl.EmbeddedGraphDatabase;
import org.neo4j.graphdb.GraphDatabaseService;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.EventCategory;
import org.oiga.model.entities.SimpleVenue;
import org.oiga.model.services.SimpleVenueService;
import org.oiga.vertex.services.FoursquareVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fi.foyt.foursquare.api.entities.CompactVenue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:db-context.xml"})
public class EventRepositoryTest {
	private Event event = new Event();
	private FoursquareVenueService venueService = new FoursquareVenueService();
	@Autowired 
	private EventRepository eventRepository;
	
	
	@Before
	public void setUp(){
		EventCategory category = new EventCategory();
		category.setName("Artes");
		category.setIcon("http://icon");
		CompactVenue compactVenue = venueService.findOneByName( "Javier Barros Sierra", "Ciudad de Mexico");
		SimpleVenue simpleVenue = SimpleVenueService.toSimpleVenue(compactVenue);
		event.setCategory(category);
		event.setVenue(simpleVenue);
		event.setDescription("Exposicion interesante");
		event.setEndDate(new Date());
		event.setHost("Facultad de Ingenieria");
		event.setHours(Arrays.asList(new String[]{"11:30/12:30"}));
		event.setName("Exposicion de Arte Moderno y Sueco");
		event.setStartDate(new Date());
		event.setTags(Arrays.asList(new String[]{"arte","sueco","Ingenieria","FI"}));
		event.setUrl("http://");
		
	}
	
	@Test
	public void test() {
		System.out.println("Iniciando test");
		String function = String.format(EventRepository.WITHIN_DISTANCE, 19.54, -99.20, 35.0);
		System.out.println("Func"+function);
		Iterable<Event> result = eventRepository.getLocation(function);
		
		for(Event r:result){
			System.out.println(r.getName());
			assertNotNull(r.getName());
		}
	}
	
	
	

}
