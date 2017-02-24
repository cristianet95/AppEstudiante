package com.example.cristian.appestudiante.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorFragmentUsuarios;
import com.example.cristian.appestudiante.modelo.Alumno;

import java.util.ArrayList;

/**
 * Created by mati on 17/02/2017.
 */

public class FragmentListaAlumno extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listaAlumnos;
    private Toolbar toolbar;
    private AlumnoListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_alumnos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaAlumnos = (ListView) getView().findViewById(R.id.listaAlumnos);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbarlistalumnos);
        toolbar.setTitle("Lista Alumnos");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    public void mostrarAlumnos(ArrayList<Alumno> alumnos){
        listaAlumnos.setAdapter(new AdaptadorFragmentUsuarios(this, alumnos));
        listaAlumnos.setOnItemClickListener(this);
    }

    public void setListener(AlumnoListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onAlumnoSeleccionado((Alumno) listaAlumnos.getAdapter().getItem(position));
        }
    }
}
