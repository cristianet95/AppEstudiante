package com.example.cristian.appestudiante.vista;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.fragment.FragmentDetallesAlumno;
import com.example.cristian.appestudiante.modelo.Alumno;

public class DetallesAlumno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalles_alumno);

        Bundle bundle = this.getIntent().getExtras();
        Alumno alumno = (Alumno) bundle.getSerializable("alumno");

        FragmentDetallesAlumno frgDt = (FragmentDetallesAlumno) getSupportFragmentManager().findFragmentById(R.id.frgDetalleAlumno);

        if(alumno != null){
            frgDt.cargarDatosAlumno(alumno);
        }
    }
}
