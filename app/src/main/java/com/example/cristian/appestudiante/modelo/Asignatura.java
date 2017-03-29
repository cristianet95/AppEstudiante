package com.example.cristian.appestudiante.modelo;

/**
 * Created by Cristian on 29/03/2017.
 */

public class Asignatura {


    private String id;
    private String nombre;
    private String descripcion;
    private String idCentro;


    public Asignatura() {
    }

    public Asignatura(String id, String nombre, String descripcion, String idCentro) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCentro = idCentro;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }
}
