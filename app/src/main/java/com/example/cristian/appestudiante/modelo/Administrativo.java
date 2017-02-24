package com.example.cristian.appestudiante.modelo;

import java.io.Serializable;

/**
 * Created by Cristian on 12/02/2017.
 */

public class Administrativo implements Serializable {

    private String dni;
    private String ape1;
    private String ape2;
    private String idCentro;

    public Administrativo() {
    }

    public Administrativo(String dni, String ape1, String ape2, String idCentro) {
        this.dni = dni;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.idCentro = idCentro;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }
}
