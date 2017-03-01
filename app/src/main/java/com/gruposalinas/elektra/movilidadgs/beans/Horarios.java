package com.gruposalinas.elektra.movilidadgs.beans;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Adrian Guzman on 20/01/2016.
 */
public class Horarios extends BaseBean implements Serializable
{
    boolean bit_valido= false;
    String id_empleado="";
    String ti_dia_semana="";
    String tm_hora_entrada="";
    String tm_hora_salida="";
    JSONArray DatosParse;
    ArrayList <String> lista;
    //
    ArrayList <Integer> activos;
    ArrayList <Integer> pendiente;
    ArrayList <Integer> liberar;
    JSONArray misHorarios;
    ArrayList <String> numeros;

    public ArrayList<String> getNumeros() {
        return numeros;
    }

    public void setNumeros(ArrayList<String> numeros) {
        this.numeros = numeros;
    }

    public ArrayList<Integer> getLiberar() {
        return liberar;
    }

    public void setLiberar(ArrayList<Integer> liberar) {
        this.liberar = liberar;
    }

    public JSONArray getMisHorarios() {
        return misHorarios;
    }

    public void setMisHorarios(JSONArray misHorarios) {
        this.misHorarios = misHorarios;
    }

    public ArrayList<Integer> getActivos() {
        return activos;
    }

    public void setActivos(ArrayList<Integer> activos) {
        this.activos = activos;
    }

    public ArrayList<Integer> getPendiente() {
        return pendiente;
    }

    public void setPendiente(ArrayList<Integer> pendiente) {
        this.pendiente = pendiente;
    }
// para validar o rechazar//

    String dia;
    String comentario;
    String numerodelEmpleado;


    public String getNumerodelEmpleado() {
        return numerodelEmpleado;
    }

    public void setNumerodelEmpleado(String numerodelEmpleado) {
        this.numerodelEmpleado = numerodelEmpleado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ArrayList<String> getLista() {
        return lista;
    }

    public void setLista(ArrayList<String> lista) {
        this.lista = lista;
    }

    public JSONArray getDatosParse() {
        return DatosParse;
    }

    public void setDatosParse(JSONArray datosParse) {
        DatosParse = datosParse;
    }

    public boolean isBit_valido() {
        return bit_valido;
    }

    public void setBit_valido(boolean bit_valido) {
        this.bit_valido = bit_valido;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getTi_dia_semana() {
        return ti_dia_semana;
    }

    public void setTi_dia_semana(String ti_dia_semana) {
        this.ti_dia_semana = ti_dia_semana;
    }

    public String getTm_hora_entrada() {
        return tm_hora_entrada;
    }

    public void setTm_hora_entrada(String tm_hora_entrada) {
        this.tm_hora_entrada = tm_hora_entrada;
    }

    public String getTm_hora_salida() {
        return tm_hora_salida;
    }

    public void setTm_hora_salida(String tm_hora_salida) {
        this.tm_hora_salida = tm_hora_salida;
    }



}
