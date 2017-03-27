package com.example.cristian.appestudiante.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.fragment.FragmentListaProfesor;
import com.example.cristian.appestudiante.fragment.ProfesorListener;
import com.example.cristian.appestudiante.modelo.Profesor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListaProfesor extends AppCompatActivity {

    private FragmentListaProfesor frgListaProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_profesores);

        frgListaProfesor = (FragmentListaProfesor) getSupportFragmentManager().findFragmentById(R.id.frgListaProfesores);
    }
}
