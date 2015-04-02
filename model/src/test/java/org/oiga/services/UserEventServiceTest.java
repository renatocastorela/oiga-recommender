package org.oiga.services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.LikeInteraction;
import org.oiga.model.entities.User;
import org.oiga.repositories.EventRepository;
import org.oiga.repositories.UserRepository;
import org.oiga.services.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:db-context.xml")
public class UserEventServiceTest {

	@Autowired
	UserEventService userEventService;
	@Autowired 
	UserRepository userRepository;
	@Autowired
	EventRepository eventRepository; 
	
	@Test
	public void test() {
		Event e = eventRepository.findOne(533L);
		User u = userRepository.findOne(40547L);
		LikeInteraction interaction = new LikeInteraction();
		interaction.setEvent(e);
		interaction.setUser(u);
		interaction.setInteractionTime(new Date().getTime());
		userEventService.liked(interaction);
	}
	

}
