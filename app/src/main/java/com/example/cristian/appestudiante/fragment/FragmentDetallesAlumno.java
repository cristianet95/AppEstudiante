package com.example.cristian.appestudiante.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.modelo.Alumno;
import com.example.cristian.appestudiante.vista.DetallesAlumno;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cristian on 18/02/2017.
 */

public class FragmentDetallesAlumno extends Fragment {

    private EditText editDni;
    private EditText editNombre;
    private EditText editApe1;
    private EditText editApe2;
    private EditText editEdad;
    private EditText editLocalidad;
    private EditText editDomicilio;
    private EditText editCodigoPostal;
    private EditText editTelefono;
    private EditText editMovil;
    private EditText editEmail;

    private Toolbar toolbar;
    private Menu menu;

    private Alumno alumno;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_detalles_alumno, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        editDni = (EditText) view.findViewById(R.id.editDniInfoAlumno);
        editNombre = (EditText) view.findViewById(R.id.editNombreInfoAlumno);
        editApe1 = (EditText) view.findViewById(R.id.editApe1InfoAlumno);
        editApe2 = (EditText) view.findViewById(R.id.editApe2InfoAlumno);
        editEdad = (EditText) view.findViewById(R.id.editEdadInfoAlumno);
        editLocalidad = (EditText) view.findViewById(R.id.editLocalidadInfoAlumno);
        editDomicilio = (EditText) view.findViewById(R.id.editDomicilioInfoAlumno);
        editCodigoPostal = (EditText) view.findViewById(R.id.editCodigoPostalInfoAlumno);
        editTelefono = (EditText) view.findViewById(R.id.editTelefonoInfoAlumno);
        editMovil = (EditText) view.findViewById(R.id.editMovilInfoAlumno);
        editEmail = (EditText) view.findViewById(R.id.editEmailInfoAlumno);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbarinfoalumno);
        toolbar.setTitle("Detalle Alumnos");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }



    public void modificarAlumno() {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dni", editDni.getText().toString());
            jsonObject.put("dniActual", alumno.getDni());
            jsonObject.put("nombre", editNombre.getText().toString());
            jsonObject.put("ape1", editApe1.getText().toString());
            jsonObject.put("ape2", editApe2.getText().toString());
            jsonObject.put("edad", editEdad.getText().toString());
            jsonObject.put("localidad", editLocalidad.getText().toString());
            jsonObject.put("domicilio", editDomicilio.getText().toString());
            jsonObject.put("codigopostal", editCodigoPostal.getText().toString());
            jsonObject.put("telefono", editTelefono.getText().toString());
            jsonObject.put("movil", editMovil.getText().toString());
            jsonObject.put("email", editEmail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_actualizarAlumno, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("state").equals("1")){
                        Toast.makeText(getContext(), "El alumno se ha modificado", Toast.LENGTH_SHORT ).show();
                        alumno.setDni(editDni.getText().toString());
                        alumno.setNombre(editNombre.getText().toString());
                        alumno.setApe1(editApe1.getText().toString());
                        alumno.setApe2(editApe2.getText().toString());
                        alumno.setEdad(editEdad.getText().toString());
                        alumno.setLocalidad(editLocalidad.getText().toString());
                        alumno.setDomicilio(editDomicilio.getText().toString());
                        alumno.setCodigoPostal(editCodigoPostal.getText().toString());
                        alumno.setTelefono(editTelefono.getText().toString());
                        alumno.setMovil(editMovil.getText().toString());
                        alumno.setEmail(editEmail.getText().toString());
                        ((DetallesAlumno)getActivity()).setResult(Activity.RESULT_OK);
                        cargarDatosAlumno(alumno);
                        cargarVista(2);
                    }else{
                        Toast.makeText(getContext(), "Error al modificar el alumno", Toast.LENGTH_SHORT ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                });

        AppEstudianteSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void eliminarAlumno(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("dni", editDni.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_eliminarAlumno, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("state").equals("1")){
                        Toast.makeText(getContext(), "El alumno se ha eliminado", Toast.LENGTH_SHORT ).show();
                        ((DetallesAlumno)getActivity()).setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(), "Error al eliminar el alumno", Toast.LENGTH_SHORT ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                });

        AppEstudianteSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
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
                dialog.setTitle("¿Estas seguro de eliminar al alumno?");
                dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarAlumno();
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
                modificarAlumno();
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
            editEdad.setEnabled(true);
            editLocalidad.setEnabled(true);
            editCodigoPostal.setEnabled(true);
            editDomicilio.setEnabled(true);
            editTelefono.setEnabled(true);
            editMovil.setEnabled(true);
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
            editEdad.setEnabled(false);
            editLocalidad.setEnabled(false);
            editCodigoPostal.setEnabled(false);
            editDomicilio.setEnabled(false);
            editTelefono.setEnabled(false);
            editMovil.setEnabled(false);
            editEmail.setEnabled(false);

            item = menu.findItem(R.id.opcionModificarUsuario);
            item.setVisible(true);

            item = menu.findItem(R.id.opcionEliminarInfoUsuario);
            item.setVisible(true);

            item = menu.findItem(R.id.opcionAceptarModificarUsuario);
            item.setVisible(false);

            item = menu.findItem(R.id.opcionCancelarModificarUsuario);
            item.setVisible(false);

            cargarDatosAlumno(alumno);
        }
    }

    public void cargarDatosAlumno(Alumno alumno){
        this.alumno = alumno;
        editDni.setText(alumno.getDni());
        editNombre.setText(alumno.getNombre());
        editApe1.setText(alumno.getApe1());
        editApe2.setText(alumno.getApe2());
        editEdad.setText(alumno.getEdad());
        editLocalidad.setText(alumno.getLocalidad());
        editDomicilio.setText(alumno.getDomicilio());
        editCodigoPostal.setText(alumno.getCodigoPostal());
        editTelefono.setText(alumno.getTelefono());
        editMovil.setText(alumno.getMovil());
        editEmail.setText(alumno.getEmail());
    }

    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
    }
}
