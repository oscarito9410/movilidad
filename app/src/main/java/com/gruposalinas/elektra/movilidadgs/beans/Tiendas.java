package com.gruposalinas.elektra.movilidadgs.beans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Adrian Guzman on 05/02/2016.
 */
public class Tiendas extends BaseBean implements Serializable
{
    String latitud;
    String longitud;
    String NombreTienda;
    String Id_empleado;
    JSONArray arregloTiendas;
    JSONArray zona;
    String buscar;
    int respuesta;
   ArrayList<String> numero_empleados;
    ArrayList <String> mostrarCompleto;
    ArrayList<String> mostrar_Nombres;
    String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getNumero_empleados() {
        return numero_empleados;
    }

    public void setNumero_empleados(ArrayList<String> numero_empleados) {
        this.numero_empleados = numero_empleados;
    }

    public ArrayList<String> getMostrarCompleto() {
        return mostrarCompleto;
    }

    public void setMostrarCompleto(ArrayList<String> mostrarCompleto) {
        this.mostrarCompleto = mostrarCompleto;
    }

    public ArrayList<String> getMostrar_Nombres() {
        return mostrar_Nombres;
    }

    public void setMostrar_Nombres(ArrayList<String> mostrar_Nombres) {
        this.mostrar_Nombres = mostrar_Nombres;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public int getIdposicion() {
        return idposicion;
    }

    public void setIdposicion(int idposicion) {
        this.idposicion = idposicion;
    }

    int idposicion;

    public JSONArray getZona() {
        return zona;
    }

    public void setZona(JSONArray zona) {
        this.zona = zona;
    }

    String posicion;// para enviar propuesta y tambien eliminar tienda//

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public JSONArray getArregloTiendas() {
        return arregloTiendas;
    }

    public void setArregloTiendas(JSONArray arregloTiendas) {
        this.arregloTiendas = arregloTiendas;
    }

    public String getId_empleado() {
        return Id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        Id_empleado = id_empleado;
    }

    boolean bit_valido;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombreTienda() {
        return NombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        NombreTienda = nombreTienda;
    }

    public boolean isBit_valido() {
        return bit_valido;
    }

    public void setBit_valido(boolean bit_valido) {
        this.bit_valido = bit_valido;
    }
}
