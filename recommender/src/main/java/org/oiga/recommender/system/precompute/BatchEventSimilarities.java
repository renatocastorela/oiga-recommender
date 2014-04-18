package org.oiga.recommender.system.precompute;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

/**
 * Proceso batch que calcula similitudes
 * 
 * @author jaime.renato
 * 
 */
public class BatchEventSimilarities {
	static Logger logger = Logger.getLogger(BatchEventSimilarities.class
			.getName());
	

	public static void main(String[] args) throws IOException, TasteException {
		File resultFile = new File("similarities.csv");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		logger.info("Cargando modelo de datos desde Mahout");
		DataModel dataModel = new MongoDBDataModel("127.0.0.1", 27017,"events", "likes", false, false, 
				new SimpleDateFormat("EE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)", Locale.ENGLISH));
	
		ItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(
				dataModel, new LogLikelihoodSimilarity(dataModel));
		logger.info("Se detectaron "+Runtime.getRuntime().availableProcessors()+" procesadores disponibles");
		BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(
				recommender, 3);
		
		int numSimilarities = batch.computeItemSimilarities(Runtime
				.getRuntime().availableProcessors(), 1,
				new FileSimilarItemsWriter(resultFile));
		logger.info("Computed " + numSimilarities + " similarities for "
				+ dataModel.getNumItems() + " items " + "and saved them to "
				+ resultFile.getAbsolutePath());
	}
}
