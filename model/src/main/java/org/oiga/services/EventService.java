package org.oiga.services;

import org.oiga.model.entities.Event;

/**
 * Interfaz del EventService
 *
 * @author Jaime Renato Castorela Castro | renato.castorela@gmail.com
 *
 */
public interface EventService {
	
	/**
	 * Encuentra un evento por su uuid.  
	 * @param uuid 
	 * @return El evento asociado al UUID o nulo si es que no encuentra ningun evento.
	 */
	Event findByUuid(String uuid);
}
