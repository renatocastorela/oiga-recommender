package org.oiga.recommender.web;

import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

public class OigaWebApplication extends ResourceConfig {
	Logger logger = Logger.getLogger(OigaWebApplication.class.getName());

	public OigaWebApplication() {
		logger.info(
				 "\n\t****************************************\n"
				+ "\tInicializando Oiga Web Recommender 1.0"
				+"\n\t****************************************");
		register(new RecommenderBinder());
		packages(true, "org.oiga.recommender.resources; org.oiga.recommender.services");
	}

}
