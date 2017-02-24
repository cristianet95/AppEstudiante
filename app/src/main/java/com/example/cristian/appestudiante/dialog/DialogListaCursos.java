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
import com.example.cristian.appestudiante.adapter.AdaptadorCurso;
import com.example.cristian.appestudiante.fragment.CursoListener;
import com.example.cristian.appestudiante.modelo.Curso;

import java.util.ArrayList;

/**
 * Created by Cristian on 22/02/2017.
 */

public class DialogListaCursos extends DialogFragment implements AdapterView.OnItemClickListener {


    private ArrayList<Curso> cursos;
    private ListView listaCursos;
    private CursoListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_lista, null);

        cursos = (ArrayList<Curso>) getArguments().get("cursos");

        listaCursos = (ListView) view.findViewById(R.id.listaObjetos);
        listaCursos.setAdapter(new AdaptadorCurso(getContext(), cursos));

        listaCursos.setOnItemClickListener(this);
        builder.setView(view);

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCursoSeleccionado(null);
                        dialog.cancel();
                    }
                });

        return builder.create();
    }


    public void setListener(CursoListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null){
            listener.onCursoSeleccionado((Curso) listaCursos.getAdapter().getItem(position));
        }
    }
}
