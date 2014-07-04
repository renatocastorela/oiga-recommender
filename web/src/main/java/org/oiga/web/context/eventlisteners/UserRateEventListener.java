package org.oiga.web.context.eventlisteners;

import java.util.Calendar;

import org.oiga.model.entities.Event;
import org.oiga.model.entities.RateInteraction;
import org.oiga.model.entities.User;
import org.oiga.web.context.events.UserRateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRateEventListener extends UserAbstractEventListener implements ApplicationListener<UserRateEvent> {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserRateEventListener.class.getName());
	
	@Override
	public void onApplicationEvent(UserRateEvent userRateEvent) {
		try{
			Event event = userRateEvent.getEvent();
			User user = getUser();
			RateInteraction interaction = createRateInteraction(user, event, userRateEvent.getScore());
			getUserEventService().rated(interaction);
		}catch(Exception e){
			LOG.error("Ocurrio un error al dispara el evento 'UserRateEvent' :"+e.toString(), e);
		}
	}

	private RateInteraction createRateInteraction(User user, Event event,
			double score) {
		RateInteraction interaction = new RateInteraction();
		interaction.setEvent(event);
		interaction.setUser(user);
		interaction.setRate(score);
		interaction.setInteractionTime(Calendar.getInstance().getTimeInMillis());
		return interaction;
	}
}
