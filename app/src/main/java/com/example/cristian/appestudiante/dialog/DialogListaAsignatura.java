package com.example.cristian.appestudiante.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorAsignatura;
import com.example.cristian.appestudiante.fragment.AsignaturaListener;
import com.example.cristian.appestudiante.modelo.Asignatura;

import java.util.ArrayList;

/**
 * Created by Cristian on 02/04/2017.
 */

public class DialogListaAsignatura extends DialogFragment implements AdapterView.OnItemClickListener {


    private ArrayList<Asignatura> listaAsignaturas;
    private AsignaturaListener listener;
    private ListView lista;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_lista, null);

        listaAsignaturas = (ArrayList<Asignatura>) getArguments().get("asignaturas");

        lista = (ListView) view.findViewById(R.id.listaObjetos);
        lista.setAdapter(new AdaptadorAsignatura(getActivity(), listaAsignaturas));
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
                        listener.onAsignaturaSelected(null);
                        dialog.cancel();
                    }
                });

        builder.setView(view);

        return builder.create();
    }

    public void setListener(AsignaturaListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onAsignaturaSelected((Asignatura) lista.getAdapter().getItem(position));
        }
    }
}
