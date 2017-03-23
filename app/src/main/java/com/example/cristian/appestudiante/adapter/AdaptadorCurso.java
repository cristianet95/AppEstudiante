package com.example.cristian.appestudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Curso;

import java.util.ArrayList;

/**
 * Created by Cristian on 22/02/2017.
 */

public class AdaptadorCurso extends ArrayAdapter<Curso> {

    private ArrayList<Curso> cursos;
    private Context context;

    public AdaptadorCurso(Context context, ArrayList<Curso> cursos) {
        super(context, R.layout.dialogo_lista, cursos);
        this.cursos =  cursos;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.list_item_curso, null);

        TextView txtPeriodo = (TextView) item.findViewById(R.id.txtPeriodo);
        TextView txtNombre = (TextView) item.findViewById(R.id.txtNombreCurso);

        txtPeriodo.setText(cursos.get(position).getPeriodo());
        txtNombre.setText(cursos.get(position).getNombre());

        return (item);
    }
}
