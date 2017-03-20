package com.example.cristian.appestudiante.adapter;

/**
 * Created by Cristian on 19/02/2017.
 */

public class ListaItems {


    private String titulo;
    private int imagen;

    public ListaItems() {
    }

    public ListaItems(String titulo, int imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
