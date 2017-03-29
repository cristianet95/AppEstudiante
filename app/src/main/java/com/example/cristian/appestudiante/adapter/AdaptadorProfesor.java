package com.example.cristian.appestudiante.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Profesor;

import java.util.ArrayList;

/**
 * Created by Cristian on 23/02/2017.
 */

public class AdaptadorProfesor extends ArrayAdapter<Profesor> {

    private ArrayList<Profesor> profesores;
    private Context context;

    public AdaptadorProfesor(Context context, ArrayList<Profesor> profesores) {
        super(context, R.layout.dialogo_lista, profesores);
        this.profesores = profesores;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.list_item_usuario, null);

        TextView txtDni = (TextView) item.findViewById(R.id.txtDniListaAl);
        TextView txtNombre = (TextView) item.findViewById(R.id.txtNombreListaAl);
        TextView txtApe1 = (TextView) item.findViewById(R.id.txt1ApellidoListaAl);
        TextView txtApe2 = (TextView) item.findViewById(R.id.txt2ApellidoListaAl);

        txtDni.setText(profesores.get(position).getDni());
        txtNombre.setText(profesores.get(position).getNombre());
        txtApe1.setText(profesores.get(position).getApe1());
        txtApe2.setText(profesores.get(position).getApe2());

        return (item);
    }
}
