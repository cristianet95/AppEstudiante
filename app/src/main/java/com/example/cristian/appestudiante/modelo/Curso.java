package com.example.cristian.appestudiante.modelo;

import java.io.Serializable;

/**
 * Created by Cristian on 22/02/2017.
 */

public class Curso  implements Serializable {


    private String id;
    private String periodo;
    private String nombre;

    public Curso() {
    }

    public Curso(String id, String periodo, String nombre) {
        this.id = id;
        this.periodo = periodo;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
