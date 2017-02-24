package com.example.cristian.appestudiante.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Alumno;

import java.util.ArrayList;
/**
 * Created by Cristian on 16/02/2017.
 */

public class AdaptadorFragmentUsuarios extends ArrayAdapter<Alumno> {

    private ArrayList<Alumno> lista;
    private Activity context;

    public AdaptadorFragmentUsuarios(Fragment context, ArrayList<Alumno>lista) {
        super(context.getActivity(), R.layout.activity_lista_alumnos, lista);
        this.context = context.getActivity();
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View item = inflater.inflate(R.layout.list_item_usuario, null);

        TextView txtDni = (TextView) item.findViewById(R.id.txtDniListaAl);
        TextView txtNombre = (TextView) item.findViewById(R.id.txtNombreListaAl);
        TextView txtApe1 = (TextView) item.findViewById(R.id.txt1ApellidoListaAl);
        TextView txtApe2 = (TextView) item.findViewById(R.id.txt2ApellidoListaAl);

        txtDni.setText(lista.get(position).getDni());
        txtNombre.setText(lista.get(position).getNombre());
        txtApe1.setText(lista.get(position).getApe1());
        txtApe2.setText(lista.get(position).getApe2());


        return (item);
    }
}
