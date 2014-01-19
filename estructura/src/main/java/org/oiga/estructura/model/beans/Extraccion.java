package org.oiga.estructura.model.beans;

import java.util.Date;
import java.util.List;

public class Extraccion {
	private String urlRepositorio;
	private String nombreRepositorio;
	private String nombreArchivo;
	private Date fechaExtraccion;
	private List<Documento> documentos;
	
	public List<Documento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	public String getUrlRepositorio() {
		return urlRepositorio;
	}
	public void setUrlRepositorio(String urlRepositorio) {
		this.urlRepositorio = urlRepositorio;
	}
	public String getNombreRepositorio() {
		return nombreRepositorio;
	}
	public void setNombreRepositorio(String nombreRepositorio) {
		this.nombreRepositorio = nombreRepositorio;
	}
	public Date getFechaExtraccion() {
		return fechaExtraccion;
	}
	public void setFechaExtraccion(Date fechaExtraccion) {
		this.fechaExtraccion = fechaExtraccion;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
}
