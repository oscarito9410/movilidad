package com.gruposalinas.elektra.movilidadgs.beans;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "empleados")
public class Empleado extends BaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String DB_NAME  = "empleados";
	public static final String ID 		= "id";
    public static final String NOMBRE 	= "nombre";
    public static final String PUESTO 	= "puesto";
    public static final String EMPRESA 	= "empresa";
    public static final String TELEFONO = "telefono";
    public static final String EMAIL 	= "email";
    public static final String IMEI     = "imei";


	@DatabaseField(columnName = ID)
    private String idEmployee;
    @DatabaseField(columnName = NOMBRE)
    private String name;
    @DatabaseField(columnName = PUESTO)
    private int position;    
    @DatabaseField(columnName = EMPRESA)
    private String enterprise;
    @DatabaseField(columnName = TELEFONO)
    private String phoneNumber;
    @DatabaseField(columnName = IMEI)
    private String imei;
    @DatabaseField(columnName = EMAIL)
    private String email;

	public String getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getEmail() {
		return email;
	}

}
