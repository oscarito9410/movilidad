package com.gruposalinas.elektra.movilidadgs.beans;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Adrian Guzman on 20/09/2016.
 */
public class Permisos extends BaseBean implements Serializable
{
    String id_numero_empleado;
    String estatus;
    String busqueda;
    String tipo;
    JSONArray datos;
    String fecha_inicial;
    String fecha_final;
    String tipo_exclusion;

    public String getTipo_exclusion() {
        return tipo_exclusion;
    }

    public void setTipo_exclusion(String tipo_exclusion) {
        this.tipo_exclusion = tipo_exclusion;
    }

    public String getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(String fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public JSONArray getDatos() {
        return datos;
    }

    public void setDatos(JSONArray datos) {
        this.datos = datos;
    }

    public String getId_numero_empleado() {
        return id_numero_empleado;
    }

    public void setId_numero_empleado(String id_numero_empleado) {
        this.id_numero_empleado = id_numero_empleado;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
