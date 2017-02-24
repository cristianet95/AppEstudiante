package com.example.cristian.appestudiante.modelo;

import java.io.Serializable;

/**
 * Created by Cristian on 16/02/2017.
 */

public class Profesor implements Serializable {



    private String dni;
    private String nombre;
    private String ape1;
    private String ape2;
    private String email;
    private String idCentro;


    public Profesor() {
    }

    public Profesor(String dni, String nombre, String ape1, String ape2, String email, String idCentro) {
        this.dni = dni;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.email = email;
        this.idCentro = idCentro;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }
}
