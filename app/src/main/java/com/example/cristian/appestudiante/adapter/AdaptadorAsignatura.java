package com.example.cristian.appestudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Asignatura;

import java.util.ArrayList;

/**
 * Created by Cristian on 02/04/2017.
 */

public class AdaptadorAsignatura extends ArrayAdapter<Asignatura>{


    private ArrayList<Asignatura> asignaturas;
    private Context context;

    public AdaptadorAsignatura(Context context, ArrayList<Asignatura> asignaturas) {
        super(context, R.layout.dialogo_lista, asignaturas);
        this.asignaturas = asignaturas;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.list_item_asignatura, null);

        TextView txtAsignatura = (TextView) item.findViewById(R.id.txtAsignatura);

        txtAsignatura.setText(asignaturas.get(position).getNombre());

        return (item);
    }
}
