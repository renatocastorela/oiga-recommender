package org.oiga.recommender.web;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.oiga.recommender.services.EventRecommenderService;

public class RecommenderBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(getBean(EventRecommenderService.class)).to(
				EventRecommenderService.class);

	}
	private <T> T getBean(Class<T> clazz) {
		BeanManager bm = CDI.current().getBeanManager();
		Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, clazz, ctx);
	}
}
