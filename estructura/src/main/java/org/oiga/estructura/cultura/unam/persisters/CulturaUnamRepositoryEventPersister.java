package org.oiga.estructura.cultura.unam.persisters;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.oiga.estructura.model.beans.Documento;
import org.oiga.estructura.model.beans.Extraccion;
import org.oiga.estructura.utils.FileNamingUtils;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.Repository;


public class CulturaUnamRepositoryEventPersister extends EventPersister{
	
	/*TODO: Pasar este metodo a la clase abstracta*/
	@Override
	public List<Event> parseEvents(Extraccion extraccion) {
		Repository repo = new Repository();
		ArrayList<Event> events = new ArrayList<Event>();
		repo.setName(extraccion.getNombreRepositorio());
		repo.setUrl(extraccion.getUrlRepositorio());
		for(Documento d:extraccion.getDocumentos()){
			Document dom = Jsoup.parse(d.getContenido()); 
			Event e = parseEvent(dom);
			e.setRepository(repo);
		}
		return events;
	}
	private Event parseEvent(Document dom){
		Event e = new Event();
		Elements titulo = dom.select("div[class=eventos-titulo] div");
		try{
			//obj.put("titulo", titulo.get(0).text());
			//obj.put("tipo-evento", titulo.get(1).text());
		}catch(IndexOutOfBoundsException iob){
			//obj.put("tipo-evento", "Sin descripcion");
		}
		return e;
	}
}
