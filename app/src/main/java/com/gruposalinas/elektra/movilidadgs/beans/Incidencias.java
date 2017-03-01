package com.gruposalinas.elektra.movilidadgs.beans;

import android.content.Context;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Adrian Guzman on 04/03/2016.
 */
public class Incidencias extends BaseBean implements Serializable
{
    String numero_empleado;
    String fecha_inicio;
    String fecha_fin;
    JSONArray lista;
    JSONArray listaCatalogo;
     String d_num_empleado_justifica;
    String archivo;
    int tamano;
    String extension;
    String sistema;
    String version;
    String URL;
    String FECHA_LIMITE;
    String tipo;
    int id_csc_justificacion;
    JSONArray adjunto;
    boolean bit_temp_fija;
    int numeroEmpleadoValida;
    String ID_Dispositivo;
    String Modelo_Celular;
    String Version_S_O;
    String numero_jefe;
    // contadores de autorizar, pendiente,,//

    ArrayList <Integer> contador1;
    ArrayList <Integer> contador2;
    ArrayList <Integer> contador3;

    public ArrayList<Integer> getContador1() {
        return contador1;
    }

    public void setContador1(ArrayList<Integer> contador1) {
        this.contador1 = contador1;
    }

    public ArrayList<Integer> getContador2() {
        return contador2;
    }

    public void setContador2(ArrayList<Integer> contador2) {
        this.contador2 = contador2;
    }

    public ArrayList<Integer> getContador3() {
        return contador3;
    }

    public void setContador3(ArrayList<Integer> contador3) {
        this.contador3 = contador3;
    }

    public String getNumero_jefe() {
        return numero_jefe;
    }

    public void setNumero_jefe(String numero_jefe) {
        this.numero_jefe = numero_jefe;
    }

    public String getID_Dispositivo() {
        return ID_Dispositivo;
    }

    public void setID_Dispositivo(String ID_Dispositivo) {
        this.ID_Dispositivo = ID_Dispositivo;
    }

    public String getModelo_Celular() {
        return Modelo_Celular;
    }

    public void setModelo_Celular(String modelo_Celular) {
        Modelo_Celular = modelo_Celular;
    }

    public String getVersion_S_O() {
        return Version_S_O;
    }

    public void setVersion_S_O(String version_S_O) {
        Version_S_O = version_S_O;
    }

    public int getNumeroEmpleadoValida() {
        return numeroEmpleadoValida;
    }

    public void setNumeroEmpleadoValida(int numeroEmpleadoValida) {
        this.numeroEmpleadoValida = numeroEmpleadoValida;
    }

    public boolean isBit_temp_fija() {
        return bit_temp_fija;
    }

    public void setBit_temp_fija(boolean bit_temp_fija)
    {
        this.bit_temp_fija = bit_temp_fija;
    }

    public JSONArray getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(JSONArray adjunto) {
        this.adjunto = adjunto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_csc_justificacion() {
        return id_csc_justificacion;
    }

    public void setId_csc_justificacion(int id_csc_justificacion) {
        this.id_csc_justificacion = id_csc_justificacion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getFECHA_LIMITE() {
        return FECHA_LIMITE;
    }

    public void setFECHA_LIMITE(String FECHA_LIMITE) {
        this.FECHA_LIMITE = FECHA_LIMITE;
    }

    public String getSistema() {

        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getD_num_empleado_justifica() {
        return d_num_empleado_justifica;
    }

    public void setD_num_empleado_justifica(String d_num_empleado_justifica) {
        this.d_num_empleado_justifica = d_num_empleado_justifica;
    }

    public String getVa_comentarios() {
        return va_comentarios;
    }

    public void setVa_comentarios(String va_comentarios) {
        this.va_comentarios = va_comentarios;
    }

    String va_comentarios;

    public int getId_justificacion() {
        return id_justificacion;
    }

    public void setId_justificacion(int id_justificacion) {
        this.id_justificacion = id_justificacion;
    }

    int id_justificacion;
    int CSCINCD;

    public int getCSCINCD() {
        return CSCINCD;
    }

    public void setCSCINCD(int CSCINCD) {
        this.CSCINCD = CSCINCD;
    }

    public JSONArray getListaCatalogo() {
        return listaCatalogo;
    }

    public void setListaCatalogo(JSONArray listaCatalogo) {
        this.listaCatalogo = listaCatalogo;
    }

    public JSONArray getLista() {
        return lista;
    }

    public void setLista(JSONArray lista) {
        this.lista = lista;
    }

    public String getNumero_empleado() {
        return numero_empleado;
    }

    public void setNumero_empleado(String numero_empleado) {
        this.numero_empleado = numero_empleado;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }


}
