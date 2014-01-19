package org.oiga.estructura.extractors;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oiga.estructura.cultura.unam.extractors.CulturaUnamRepositoryExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CulturaUnamRepositoryExtractorTest {
	public static Logger logger = LoggerFactory.getLogger(CulturaUnamRepositoryExtractorTest.class); 
	
	@BeforeClass
	public static void init() {
	   logger.info("Inicializando ...");
	   
	}
	
	@Test
	public void getIdstest() {
		CulturaUnamRepositoryExtractor extractor = new CulturaUnamRepositoryExtractor();
		extractor.extract();
//		extractor.setUp();
//		List<String> ids = extractor.getIds();
////		List<String> ids = new ArrayList<String>();
////		ids.add("22089");
////		ids.add("21145");
//		extractor.getDocuments(ids);
	
		}

}
