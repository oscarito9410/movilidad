package com.gruposalinas.elektra.movilidadgs.beans;

public class BaseBean {
	private boolean success = false;
    private String mensajeError = "";
	private  String RESPUESTA;
	private String edicion="";
	private String id_empleadoLogeado;
	private String comentario;

	public String getId_empleadoLogeado() {
		return id_empleadoLogeado;
	}

	public void setId_empleadoLogeado(String id_empleadoLogeado) {
		this.id_empleadoLogeado = id_empleadoLogeado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEdicion() {
		return edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public String getComentarioH() {
		return comentarioH;
	}

	public void setComentarioH(String comentarioH) {
		this.comentarioH = comentarioH;
	}

	private  String comentarioH="";

	public String getRESPUESTA() {
		return RESPUESTA;
	}

	public void setRESPUESTA(String RESPUESTA) {
		this.RESPUESTA = RESPUESTA;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}   
}