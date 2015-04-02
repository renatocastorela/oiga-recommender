package org.oiga.services;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.model.entities.Event;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test del EventService
 *
 * @author Jaime Renato Castorela Castro | renato.castorela@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class EventServiceTest {

	/**
	 * Servicio
	 */
	@Inject
	private EventService service;
	
	/**
	 * Test del metodo findByUuid
	 */
	@Test
	public void testFindByUuid() {
		Event event = service.findByUuid("5d10b7d47e89526ead8187d83eca3fa3");
		Assert.assertNotNull(event);
	}

}
