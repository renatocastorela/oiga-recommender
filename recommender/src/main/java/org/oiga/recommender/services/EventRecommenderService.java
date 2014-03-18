package org.oiga.recommender.services;

import java.util.logging.Logger;

import org.apache.mahout.cf.taste.recommender.Recommender;

public class EventRecommenderService {
	Logger logger = Logger.getLogger(EventRecommenderService.class.getCanonicalName());
	
	Recommender recommender;
	
	public EventRecommenderService() {
		super();
		logger.info("Inicializando servicio de recomendacion");
	}
	public String recommendByLikes(int userId){
		return "[[1, 4.5],[9, 3.0],[3, 3.4]]";
	}
}
