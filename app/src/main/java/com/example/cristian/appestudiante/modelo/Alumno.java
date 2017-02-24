package com.example.cristian.appestudiante.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cristian on 06/02/2017.
 */

public class Alumno implements Serializable {

    private String dni;
    private String nombre;
    private String ape1;
    private String ape2;
    private String domicilio;
    private String localidad;
    private String codigoPostal;
    private String nacionalidad;
    private Date fecha;
    private String edad;
    private String telefono;
    private String movil;
    private String email;

    public Alumno() {
    }

    public Alumno(String dni, String nombre, String ape1, String ape2, String domicilio, String localidad, String codigoPostal, String nacionalidad, Date fecha, String edad, String telefono, String movil, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codigoPostal = codigoPostal;
        this.nacionalidad = nacionalidad;
        this.fecha = fecha;
        this.edad = edad;
        this.telefono = telefono;
        this.movil = movil;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ape1='" + ape1 + '\'' +
                ", ape2='" + ape2 + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", localidad='" + localidad + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", telefono=" + telefono +
                ", movil=" + movil +
                ", email='" + email + '\'' +
                '}';
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
