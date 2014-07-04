package org.oiga.web.context.eventlisteners;

import java.util.Calendar;

import javax.inject.Inject;

import org.oiga.model.entities.Event;
import org.oiga.model.entities.User;
import org.oiga.model.entities.ViewInteraction;
import org.oiga.model.services.UserEventService;
import org.oiga.web.context.events.UserViewEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserViewEventListener extends UserAbstractEventListener implements ApplicationListener<UserViewEvent> {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserViewEventListener.class);
	@Inject
	private UserEventService userEventService;
	
	@Override
	public void onApplicationEvent(UserViewEvent userViewEvent) {
		try{
			Event event = userViewEvent.getEvent();
			User user = getUser();
			ViewInteraction interaction = createViewInteraction(user, event);
			userEventService.viewed(interaction);
		}catch(Exception e){
			LOG.error("Ocurrio un error al dispara el evento 'UserViewEvent' :"+e.toString(), e);
		}
		
	}
	
	private ViewInteraction createViewInteraction(User user, Event event){
		ViewInteraction vi = new ViewInteraction();
		vi.setEvent(event);
		vi.setUser(user);
		vi.setInteractionTime(Calendar.getInstance().getTimeInMillis());
		return vi;
	}
}
