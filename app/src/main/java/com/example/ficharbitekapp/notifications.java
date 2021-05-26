package com.example.ficharbitekapp;

public class notifications {

    String id_reg;
    String fecha;
    String hora;
    String accion;
    String acep_emp;
    String acep_empr;
    String estado;

    public notifications(String id_reg, String fecha, String hora, String accion, String acep_emp, String acep_empr, String estado) {
        this.id_reg = id_reg;
        this.fecha = fecha;
        this.hora = hora;
        this.accion = accion;
        this.acep_emp = acep_emp;
        this.acep_empr = acep_empr;
        this.estado = estado;
    }

    public String getId_reg() {
        return id_reg;
    }

    public void setId_reg(String id_reg) {
        this.id_reg = id_reg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAcep_emp() {
        return acep_emp;
    }

    public void setAcep_emp(String acep_emp) {
        this.acep_emp = acep_emp;
    }

    public String getAcep_empr() {
        return acep_empr;
    }

    public void setAcep_empr(String acep_empr) {
        this.acep_empr = acep_empr;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
