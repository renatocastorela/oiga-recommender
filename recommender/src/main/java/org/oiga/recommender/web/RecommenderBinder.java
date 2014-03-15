package org.oiga.recommender.web;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.oiga.recommender.services.EventRecommenderService;

public class RecommenderBinder extends AbstractBinder{

	@Override
	protected void configure() {
		System.out.println("Configurando..................");
		 bind(EventRecommenderService.class).to(EventRecommenderService.class);
		
	}

}
