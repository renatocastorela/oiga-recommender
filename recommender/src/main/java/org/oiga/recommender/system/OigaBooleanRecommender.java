package org.oiga.recommender.system;

import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class OigaBooleanRecommender implements Recommender {
	private  Recommender recommender;
	
	public OigaBooleanRecommender(DataModel bcModel) throws TasteException {
		// Configuracion de la implementacion de la recomendacion
		UserSimilarity similarity = new CachingUserSimilarity(
				new LogLikelihoodSimilarity(bcModel), bcModel);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
				Double.NEGATIVE_INFINITY, similarity, bcModel, 1.0);
		recommender = new GenericBooleanPrefUserBasedRecommender(bcModel,
				neighborhood, similarity);
	}

	 @Override
	  public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
	    return recommender.recommend(userID, howMany);
	  }

	  @Override
	  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
	    return recommender.recommend(userID, howMany, rescorer);
	  }

	  @Override
	  public float estimatePreference(long userID, long itemID) throws TasteException {
	    return recommender.estimatePreference(userID, itemID);
	  }

	  @Override
	  public void setPreference(long userID, long itemID, float value) throws TasteException {
	    recommender.setPreference(userID, itemID, value);
	  }

	  @Override
	  public void removePreference(long userID, long itemID) throws TasteException {
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
