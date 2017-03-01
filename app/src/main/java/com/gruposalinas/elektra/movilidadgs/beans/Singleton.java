package com.gruposalinas.elektra.movilidadgs.beans;


public class Singleton extends BaseBean {
	private static Singleton _mInstance;
 
    private String imei;
    String numero_empleado;
    String log;

    public String getNumero_empleado() {
        return numero_empleado;
    }

    public void setNumero_empleado(String numero_empleado) {
        this.numero_empleado = numero_empleado;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Singleton(){
    }
 
    public static Singleton getInstance(){
        if(_mInstance == null){
            _mInstance = new Singleton();
        }
        return _mInstance;
    }
 

    public String getImei(){ return this.imei; }
    public void setImei(String value){ imei = value;  }

}
