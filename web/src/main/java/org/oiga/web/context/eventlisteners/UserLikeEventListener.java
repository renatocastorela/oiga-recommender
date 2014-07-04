package org.oiga.web.context.eventlisteners;

import java.util.Calendar;

import org.oiga.model.entities.Event;
import org.oiga.model.entities.LikeInteraction;
import org.oiga.model.entities.User;
import org.oiga.web.context.events.UserLikeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserLikeEventListener  extends UserAbstractEventListener implements ApplicationListener<UserLikeEvent>{
	private static final Logger LOG = LoggerFactory
			.getLogger(UserLikeEventListener.class.getName());
	
	@Override
	public void onApplicationEvent(UserLikeEvent userLikeEvent) {
		try{
			Event event = userLikeEvent.getEvent();
			User user = getUser();
			LikeInteraction interaction = createLikeInteraction(user, event);
			getUserEventService().liked(interaction);
		}catch(Exception e){
			LOG.error("Ocurrio un error al dispara el evento 'UserLikeEvent' :"+e.toString(), e);
		}
	}
	
	//FIXME: Pasar a un  factory a nivel de la clase abstracta padre
	private LikeInteraction createLikeInteraction(User user, Event event){
		LikeInteraction interaction = new LikeInteraction();
		interaction.setEvent(event);
		interaction.setUser(user);
		interaction.setInteractionTime(Calendar.getInstance().getTimeInMillis());
		return interaction;
	}
	

}
