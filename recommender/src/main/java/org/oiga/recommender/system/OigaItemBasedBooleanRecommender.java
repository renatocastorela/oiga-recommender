package org.oiga.recommender.system;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;


public class OigaItemBasedBooleanRecommender implements Recommender {
	private Recommender recommender;
	static Logger logger = Logger.getLogger(OigaItemBasedBooleanRecommender.class
			.getName());
	public OigaItemBasedBooleanRecommender(DataModel model)
			throws TasteException {
		// Configuracion de la implementacion de la recomendacion
//		ItemSimilarity similarity = new CachingItemSimilarity(
//				new FileItemSimilarity(new File("similarities.csv")), model);
		ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
		logger.info(">>Usuarios en el recommender :"+model.getNumUsers());
		logger.info(">>Items en el recommender :"+model.getNumItems());
		
		long id;
		String out = "";
		
		
		
		recommender = new GenericBooleanPrefItemBasedRecommender(model,
				similarity);
		LongPrimitiveIterator ut = model.getUserIDs();
		LongPrimitiveIterator it = model.getItemIDs();
		MongoDBDataModel m = (MongoDBDataModel)model;
		while(ut.hasNext()){
			id = ut.nextLong();
			out += id+"->"+m.fromLongToId(id)+",";
		}
		logger.info("Usuarios : "+out);
		out = "";
		while(it.hasNext()){
			id = it.nextLong();
			out += id+"->"+m.fromLongToId(id)+",";
		}
		logger.info("Items : "+out);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany)
			throws TasteException {
		return recommender.recommend(userID, howMany);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany,
			IDRescorer rescorer) throws TasteException {
		return recommender.recommend(userID, howMany, rescorer);
	}

	@Override
	public float estimatePreference(long userID, long itemID)
			throws TasteException {
		return recommender.estimatePreference(userID, itemID);
	}

	@Override
	public void setPreference(long userID, long itemID, float value)
			throws TasteException {
		recommender.setPreference(userID, itemID, value);
	}

	@Override
	public void removePreference(long userID, long itemID)
			throws TasteException {
		recommender.removePreference(userID, itemID);
	}

	@Override
	public DataModel getDataModel() {
		return recommender.getDataModel();
	}

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		recommender.refresh(alreadyRefreshed);
	}

	@Override
	public String toString() {
		return "OigaBooleanRecommender[recommender:" + recommender + ']';
	}
}
