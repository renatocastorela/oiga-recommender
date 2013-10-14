package org.oiga.vertex.scrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class FoursquareCategoryScraperTest {

	@Test
	public void testScrape() {
		System.out.println("Iniciando test");
		try{
		FoursquareCategoryScraper scraper = new FoursquareCategoryScraper();
		scraper.scrape();}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
