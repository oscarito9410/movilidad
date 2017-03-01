package com.gruposalinas.elektra.movilidadgs.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Adrian Guzman on 31/03/2016.
 */
@DatabaseTable(tableName = "eventos")
public class ObtenerEstatusAlerta extends BaseBean implements Serializable
{
    public static final String ID="ID";
    public static final String SUCCESS="success";
    public static final String EVENTO="EVENTO";
    public static final String FECHA="FECHA";
    public static final String BETERIA="BATERIA";
    public static final String IDEVENTO="IDEVENTO";

    @DatabaseField(columnName = ID)
    private   String id_numero_empleado;

    @DatabaseField(columnName = SUCCESS)
    private    boolean success;

    @DatabaseField(columnName = FECHA)
     private String fecha;

    @DatabaseField(columnName = EVENTO)
   private int evento;

    @DatabaseField(columnName = BETERIA)
   private float bateria;

    @DatabaseField(id =true ,columnName = IDEVENTO)
    private String idEvento;

    String nombre_desactivo;


    public ObtenerEstatusAlerta() {
    }

    public ObtenerEstatusAlerta(String numeroEmpleado,boolean success,String fecha,int evento,float bateria,String idevento)
    {
        super();
        this.id_numero_empleado=numeroEmpleado;
        this.success=success;
        this.fecha=fecha;
        this.evento=evento;
        this.bateria=bateria;
        this.idEvento=idevento;

    }

    public double getBateria() {
        return bateria;
    }

    public void setBateria(float bateria) {
        this.bateria = bateria;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public boolean issuccess() {
        return success;
    }

    public void setsuccess(boolean success) {
        this.success = success;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre_desactivo() {
        return nombre_desactivo;
    }

    public void setNombre_desactivo(String nombre_desactivo) {
        this.nombre_desactivo = nombre_desactivo;
    }

    public String getId_numero_empleado() {
        return id_numero_empleado;
    }

    public void setId_numero_empleado(String id_numero_empleado) {
        this.id_numero_empleado = id_numero_empleado;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
}
