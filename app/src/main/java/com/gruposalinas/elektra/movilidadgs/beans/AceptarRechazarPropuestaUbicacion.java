package com.gruposalinas.elektra.movilidadgs.beans;

/**
 * Created by yvegav on 06/10/2016.
 */
public class AceptarRechazarPropuestaUbicacion extends BaseBean
{
    String id_num_empleado1;
    String va_numero_pos;
    String va_nombre_pos;
    String typeMov;
    String motivo;


    public String getId_num_empleado1() {
        return id_num_empleado1;
    }

    public void setId_num_empleado1(String id_num_empleado1) {
        this.id_num_empleado1 = id_num_empleado1;
    }

    public String getVa_numero_pos() {
        return va_numero_pos;
    }

    public void setVa_numero_pos(String va_numero_pos) {
        this.va_numero_pos = va_numero_pos;
    }

    public String getVa_nombre_pos() {
        return va_nombre_pos;
    }

    public void setVa_nombre_pos(String va_nombre_pos) {
        this.va_nombre_pos = va_nombre_pos;
    }

    public String getTypeMov() {
        return typeMov;
    }

    public void setTypeMov(String typeMov) {
        this.typeMov = typeMov;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
