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
import com.example.cristian.appestudiante.adapter.AdaptadorProfesor;
import com.example.cristian.appestudiante.fragment.ProfesorListener;
import com.example.cristian.appestudiante.modelo.Profesor;

import java.util.ArrayList;

/**
 * Created by Cristian on 27/03/2017.
 */

public class DialogListaProfesor extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<Profesor> listaProfesor;
    private ProfesorListener listener;
    private ListView lista;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_lista, null);

        listaProfesor = (ArrayList<Profesor>) getArguments().get("profesores");

        lista = (ListView) view.findViewById(R.id.listaObjetos);

        lista.setAdapter(new AdaptadorProfesor(getActivity(), listaProfesor));
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
                        listener.onProfesorListener(null);
                        dialog.cancel();
                    }
                });

        builder.setView(view);

        return builder.create();
    }

    public void setListener (ProfesorListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onProfesorListener((Profesor) lista.getAdapter().getItem(position));
        }
    }
}
