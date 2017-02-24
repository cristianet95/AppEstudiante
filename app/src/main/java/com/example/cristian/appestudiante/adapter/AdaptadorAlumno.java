package com.example.cristian.appestudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Alumno;

import java.util.ArrayList;

/**
 * Created by Cristian on 22/02/2017.
 */

public class AdaptadorAlumno extends ArrayAdapter<Alumno> {

    private ArrayList<Alumno> alumnos;
    private Context context;

    public AdaptadorAlumno(Context context, ArrayList<Alumno> alumnos) {
        super(context, R.layout.dialogo_lista, alumnos);
        this.alumnos = alumnos;
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

        txtDni.setText(alumnos.get(position).getDni());
        txtNombre.setText(alumnos.get(position).getNombre());
        txtApe1.setText(alumnos.get(position).getApe1());
        txtApe2.setText(alumnos.get(position).getApe2());

        return (item);
    }
}
