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
import com.example.cristian.appestudiante.adapter.AdaptadorProfesor;
import com.example.cristian.appestudiante.modelo.Profesor;

import java.util.ArrayList;

/**
 * Created by Cristian on 23/02/2017.
 */

public class FragmentListaProfesor extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listaProfesores;
    private Toolbar toolbar;
    private ProfesorListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_profesor, container, false);

        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaProfesores = (ListView) getView().findViewById(R.id.listaProfesor);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbarlistaprofesor);
        toolbar.setTitle("Lista Profesores");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    public void mostrarProfesores(ArrayList<Profesor> profesores){
        listaProfesores.setAdapter(new AdaptadorProfesor(this, profesores));
        listaProfesores.setOnItemClickListener(this);
    }

    public void setListener (ProfesorListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onProfesorListener((Profesor) listaProfesores.getAdapter().getItem(position));
        }
    }
}
