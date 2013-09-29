package org.oiga.estructura.repository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CulturaUnamRepositoryReader implements RepositoryReader<Map>{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> read(Document document) {
		List<Map> contents = new ArrayList<Map>();
		Elements divs = document.select("div[class=eventos]");
		for (Element e : divs) {
			Map obj=new LinkedHashMap();


			Elements titulo = e.select("div[class=eventos-titulo] div");
			try{
				
				obj.put("titulo", titulo.get(0).text());
				obj.put("tipo-evento", titulo.get(1).text());
			}catch(IndexOutOfBoundsException iob){
				obj.put("tipo-evento", "Sin descripcion");
			}
			Elements contenedores = e.select("div[class=eventos-contenedor]");
			for(Element c:contenedores){
				String propiedad = c.select("div[class=eventos-columna1]").text().replace(":", "").replace(" ", "").toLowerCase();
				String valor = c.select("div[class=eventos-columna2]").text();
				if(propiedad.isEmpty()) continue;
				obj.put(propiedad, valor);
			}
			contents.add(obj);
		}
		return contents;
	}

}
