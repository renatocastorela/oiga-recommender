package org.oiga.recommender.system.precompute;

import java.io.File
;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
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
	static String defaultPath = "ratings.dat";

	public static void main(String[] args) throws IOException, TasteException {
		String path = null;
		if (args.length != 1) {
			logger.info("No se especifico path, usando path por default");
			path = defaultPath;
		} else {
			path = args[0];
		}
		File resultFile = new File("similarities.csv");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		logger.info("Cargando modelo de datos");
		DataModel dataModel = new FileDataModel(new File(path));
		
		
		ItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(
				dataModel, new LogLikelihoodSimilarity(dataModel));
		
		BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(
				recommender, 2);

		int numSimilarities = batch.computeItemSimilarities(Runtime
				.getRuntime().availableProcessors(), 1,
				new FileSimilarItemsWriter(resultFile));

		logger.info("Computed " + numSimilarities + " similarities for "
				+ dataModel.getNumItems() + " items " + "and saved them to "
				+ resultFile.getAbsolutePath());
	}
}
