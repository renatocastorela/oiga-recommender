package org.oiga.recommender.system;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * Prueba de concepto
 * @author jaime.renato
 *
 */
@Deprecated
public class OigaBooleanRecommenderBuilder implements RecommenderBuilder{

	@Override
	public Recommender buildRecommender(DataModel bcModel) throws TasteException {
		return new OigaItemBasedBooleanRecommender(bcModel);
	}

}
