package org.oiga.recommender.services;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.oiga.recommender.system.OigaItemBasedBooleanRecommender;

public class EventRecommenderService {
	Logger logger = Logger.getLogger(EventRecommenderService.class.getCanonicalName());
	
	Recommender recommender;
	DataModel model;
	
	public EventRecommenderService() throws UnknownHostException, TasteException {
		super();
		logger.info("Inicializando modelo de datos.");
		model =  new MongoDBDataModel("127.0.0.1", 27017,"events", "likes", false, false, 
				new SimpleDateFormat("EE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)", Locale.ENGLISH));
		logger.info("Inicializando servicio de recomendacion");
		
		recommender = new OigaItemBasedBooleanRecommender(model);
	}
	public List<RecommendedItem> recommendByLikes(int userId) throws TasteException{
		return recommender.recommend(userId, 10);
	}
}
