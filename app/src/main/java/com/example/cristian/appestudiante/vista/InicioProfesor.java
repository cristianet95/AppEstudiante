package com.example.cristian.appestudiante.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Profesor;

public class InicioProfesor extends AppCompatActivity {

    private Toolbar toolbar;
    private Profesor profesor;
    private ListView listaAsignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_profesor);

        toolbar = (Toolbar) findViewById(R.id.iniprofetoolbar);
        toolbar.setTitle("Inicio profesor");

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();

            if(bundle == null){
                profesor = null;
            }else{
                profesor = (Profesor) bundle.getSerializable("profesor");
            }
        }else{
            profesor = (Profesor) savedInstanceState.getSerializable("profesor");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("profesor", profesor);
    }
}
