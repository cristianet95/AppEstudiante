package com.example.cristian.appestudiante.vista;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.fragment.FragmentDetallesProfesor;
import com.example.cristian.appestudiante.modelo.Profesor;

public class DetallesProfesor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalles_profesor);

        Bundle bundle = getIntent().getExtras();
        Profesor profesor = (Profesor) bundle.getSerializable("profesor");

        FragmentDetallesProfesor frgDetallesProfesor = (FragmentDetallesProfesor) getSupportFragmentManager().findFragmentById(R.id.frgDetalleProfesor);

        if(profesor != null){
            frgDetallesProfesor.cargarDatosProfesor(profesor);
        }
    }
}
