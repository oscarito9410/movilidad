package com.gruposalinas.elektra.movilidadgs.beans;

import java.io.Serializable;

/**
 * Created by Adrian Guzman on 28/01/2016.
 */
public class Horapendiente extends BaseBean implements Serializable
{
    boolean bit_valido;
    String id_empleado;
    String ti_dia_semana;
    String tm_hora_entrada;
    String tm_hora_salida;
    String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Horapendiente()
    {
         bit_valido= false;
         id_empleado="";
        ti_dia_semana="";

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
