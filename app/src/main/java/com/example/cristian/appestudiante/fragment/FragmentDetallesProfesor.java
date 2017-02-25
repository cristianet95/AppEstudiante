package com.example.cristian.appestudiante.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.modelo.Profesor;

/**
 * Created by Cristian on 25/02/2017.
 */

public class FragmentDetallesProfesor extends Fragment {

    private EditText editDni;
    private EditText editNombre;
    private EditText editApe1;
    private EditText editApe2;
    private EditText editEmail;

    private Menu menu;

    private Toolbar toolbar;

    private Profesor profesor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detalles_profesor, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        editDni = (EditText) view.findViewById(R.id.editDniInfoProfesor);
        editNombre = (EditText) view.findViewById(R.id.editNombreInfoProfesor);
        editApe1 = (EditText) view.findViewById(R.id.editApe1InfoProfesor);
        editApe2 = (EditText) view.findViewById(R.id.editApe2InfoProfesor);
        editEmail = (EditText) view.findViewById(R.id.editEmailInfoProfesor);

        toolbar = (Toolbar) getView().findViewById(R.id.toolbarinfoprofesor);
        toolbar.setTitle("Detalle Profesor");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.menu_info_usuario, menu);

        menu.findItem(R.id.opcionAceptarModificarUsuario).setVisible(false);
        menu.findItem(R.id.opcionCancelarModificarUsuario).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.opcionModificarUsuario:
                cargarVista(1);
                return true;
            case R.id.opcionEliminarInfoUsuario:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("¿Estas seguro de eliminar al profesor?");
                dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //eliminarAlumno();
                        dialogInterface.dismiss();
                    }
                });
                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.create().show();
                return true;
            case R.id.opcionAceptarModificarUsuario:
                //modificarAlumno();
                return true;
            case R.id.opcionCancelarModificarUsuario:
                cargarVista(2);
                return true;
            default:
                return true;
        }
    }

    public void cargarVista(int opcion){
        MenuItem item;

        if(opcion == 1){
            editDni.setEnabled(true);
            editNombre.setEnabled(true);
            editApe1.setEnabled(true);
            editApe2.setEnabled(true);
            editEmail.setEnabled(true);

            item = menu.findItem(R.id.opcionModificarUsuario);
            item.setVisible(false);

            item = menu.findItem(R.id.opcionEliminarInfoUsuario);
            item.setVisible(false);

            item = menu.findItem(R.id.opcionAceptarModificarUsuario);
            item.setVisible(true);

            item = menu.findItem(R.id.opcionCancelarModificarUsuario);
            item.setVisible(true);
        }else if(opcion == 2){
            editDni.setEnabled(false);
            editNombre.setEnabled(false);
            editApe1.setEnabled(false);
            editApe2.setEnabled(false);
            editEmail.setEnabled(false);

            item = menu.findItem(R.id.opcionModificarUsuario);
            item.setVisible(true);

            item = menu.findItem(R.id.opcionEliminarInfoUsuario);
            item.setVisible(true);

            item = menu.findItem(R.id.opcionAceptarModificarUsuario);
            item.setVisible(false);

            item = menu.findItem(R.id.opcionCancelarModificarUsuario);
            item.setVisible(false);

            cargarDatosProfesor(profesor);
        }
    }

    public void cargarDatosProfesor(Profesor profesor){
        this.profesor = profesor;
        editDni.setText(profesor.getDni());
        editNombre.setText(profesor.getNombre());
        editApe1.setText(profesor.getApe1());
        editApe2.setText(profesor.getApe2());
        editEmail.setText(profesor.getEmail());
    }
}
