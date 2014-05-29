package org.oiga.web.context.eventlisteners;

import org.oiga.web.context.events.UserViewEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class UserViewEventListener implements ApplicationListener<UserViewEvent>{
	private static final Logger LOG = LoggerFactory
			.getLogger(UserViewEventListener.class);
	
	@Override
	public void onApplicationEvent(UserViewEvent event) {
		System.out.println("dasdas");
		LOG.info("El usuario a visto al evento + "+event.getEvent().getName());
	}

}
