package org.oiga.model.services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.LikeInteraction;
import org.oiga.model.entities.User;
import org.oiga.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:db-context.xml")
public class EventServiceTest {
	@Autowired
	private EventService service;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserEventService userEventService;
	
	
	@Test
	public void test() {
		Event e = service.getEventRepository().findOne(533L);
		User u = userRepository.findOne(40547L);
		LikeInteraction interaction = new LikeInteraction();
		interaction.setEvent(e);
		interaction.setUser(u);
		interaction.setInteractionTime(new Date().getTime());
		userEventService.liked(interaction);
//		Interaction relationShip = service.getRelationShip(e, u);
//		System.out.println("Interacit:"+relationShip.getLastInteraction());
		//service.liked(e, u);
		//service.importEvents("exp_cultura_unam_20140101_20140131.json");
	}

}
