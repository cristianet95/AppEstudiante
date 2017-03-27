package com.example.cristian.appestudiante.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.fragment.AlumnoListener;
import com.example.cristian.appestudiante.fragment.FragmentListaAlumno;
import com.example.cristian.appestudiante.modelo.Alumno;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Cristian on 16/02/2017.
 */

public class ListaAlumnos extends AppCompatActivity {

    private FragmentListaAlumno frgListaAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_alumnos);

        frgListaAlumno = (FragmentListaAlumno) getSupportFragmentManager().findFragmentById(R.id.frgListaAlumnos);
    }
}
