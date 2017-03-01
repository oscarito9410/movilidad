package com.gruposalinas.elektra.movilidadgs.beans;

import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "configuracion")
public class Configuracion extends BaseBean{
	 public static final String ID     		= "id";
	 public static final String TIEMPO     	= "int_tiempo";
	 public static final String DISTANCIA	= "int_distancia";
	 public static final String RADIO       = "int_radio";
	 public static final String RANGO     	= "rango";
	 
	 @DatabaseField(columnName = ID)
	 private String idEmployee;
	 
	 @DatabaseField(columnName = TIEMPO)
	 private int tiempo;
	 
	 @DatabaseField(columnName = DISTANCIA)
	 private double distancia;
	 
	 @DatabaseField(columnName = RANGO)
	 private double rango;
	 
	 private ArrayList<RangoMonitoreo> rangosMonitoreo;
	 
	 public String getIdEmployee() {
		return idEmployee;
	 }

	 public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	 }

	 public int getTiempo() {
		return tiempo;
	 }

	 public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	 }

	 public double getDistancia() {
		return distancia;
	 }

	 public void setDistancia(double distancia) {
		this.distancia = distancia;
	 }

	 public double getRango() {
		return rango;
	 }

	 public void setRango(double rango) {
		this.rango = rango;
	 }

	public ArrayList<RangoMonitoreo> getRangosMonitoreo() {
		return rangosMonitoreo;
	}

	public void setRangosMonitoreo(ArrayList<RangoMonitoreo> rangosMonitoreo) {
		this.rangosMonitoreo = rangosMonitoreo;
	}
}