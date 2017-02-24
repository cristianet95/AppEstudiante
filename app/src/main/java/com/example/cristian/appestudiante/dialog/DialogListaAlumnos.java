package com.example.cristian.appestudiante.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorAlumno;
import com.example.cristian.appestudiante.fragment.AlumnoListener;
import com.example.cristian.appestudiante.modelo.Alumno;

import java.util.ArrayList;

import static com.example.cristian.appestudiante.R.id.listaObjetos;

/**
 * Created by Cristian on 22/02/2017.
 */

public class DialogListaAlumnos extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<Alumno> listaAlumnos;
    private AlumnoListener listener;
    private ListView lista;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_lista, null);

        listaAlumnos = (ArrayList<Alumno>) getArguments().get("alumnos");

        lista = (ListView) view.findViewById(listaObjetos);

        lista.setAdapter(new AdaptadorAlumno(getActivity(), listaAlumnos));
        lista.setOnItemClickListener(this);

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onAlumnoSeleccionado(null);
                        dialog.cancel();
                    }
                });

        builder.setView(view);

        return builder.create();
    }

    public void setListener (AlumnoListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onAlumnoSeleccionado((Alumno) lista.getAdapter().getItem(position));
        }
    }
}
