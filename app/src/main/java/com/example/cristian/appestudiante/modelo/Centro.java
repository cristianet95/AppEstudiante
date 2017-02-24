package com.example.cristian.appestudiante.modelo;

import java.io.Serializable;

/**
 * Created by Cristian on 15/02/2017.
 */

public class Centro implements Serializable {

    private String id;
    private String nombre;
    private String ca;
    private String localidad;
    private String calle;
    private String telefono;
    private String fax;
    private String web;
    private String titularidad;


    public Centro() {
    }

    public Centro(String id, String nombre, String ca, String localidad, String calle, String telefono, String fax, String web, String titularidad) {
        this.id = id;
        this.nombre = nombre;
        this.ca = ca;
        this.localidad = localidad;
        this.calle = calle;
        this.telefono = telefono;
        this.fax = fax;
        this.web = web;
        this.titularidad = titularidad;
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

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTitularidad() {
        return titularidad;
    }

    public void setTitularidad(String titularidad) {
        this.titularidad = titularidad;
    }
}
