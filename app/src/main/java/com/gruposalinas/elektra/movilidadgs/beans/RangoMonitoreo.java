package com.gruposalinas.elektra.movilidadgs.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rangos_monitoreo")
public class RangoMonitoreo {
	public static final String FECHA     	= "da_fecha";
	public static final String NUMERO_DIA   = "numero_dia";
	public static final String HORA_INICIAL	= "tm_hora_inicial";
	public static final String HORA_FINAL	= "tm_hora_final";
	public static final String IS_EXCLUSION	= "is_exclusion";

	 
	@DatabaseField(columnName = FECHA)
	private String fecha;
	 
	@DatabaseField(columnName = NUMERO_DIA)
	private int numeroDia;
	 
	@DatabaseField(columnName = HORA_INICIAL)
	private String horaInicial;
	 
	@DatabaseField(columnName = HORA_FINAL)
	private String horaFinal;
	 
	@DatabaseField(columnName = IS_EXCLUSION)
	private boolean isExclusion;

	public String getFecha() {
	    return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getNumeroDia() {
		return numeroDia;
	}

	public void setNumeroDia(int numeroDia) {
		this.numeroDia = numeroDia;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public boolean isExclusion() {
		return isExclusion;
	}

	public void setExclusion(boolean isExclusion) {
		this.isExclusion = isExclusion;
	}
}