package com.example.cristian.appestudiante.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Pattern;

public class AnadirProfesor extends AppCompatActivity  {

    private Toolbar toolbar;
    private EditText editDni;
    private EditText editNombre;
    private EditText editApe1;
    private EditText editApe2;
    private EditText editEmail;
    private String idCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_profesor);

        toolbar = (Toolbar) findViewById(R.id.toolbarinsertprofesor);
        toolbar.setTitle("Añadir Profesor");

        if(savedInstanceState == null) {
            idCentro = (String) getIntent().getExtras().get("idCentro");
        }else{
            idCentro = savedInstanceState.getString("idCentro");
        }

        editDni = (EditText) findViewById(R.id.editDniProfesor);
        editNombre = (EditText) findViewById(R.id.editNombreProfesor);
        editApe1 = (EditText) findViewById(R.id.editApellido1Profesor);
        editApe2 = (EditText) findViewById(R.id.editApellido2Profesor);
        editEmail = (EditText) findViewById(R.id.editEmailProfesor);
    }

    public void insertarProfesor(View v) throws JSONException {
        if(comprobarDatosProfesor()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("dni", editDni.getText().toString());
            jsonObject.put("nombre", editNombre.getText().toString());
            jsonObject.put("ape1", editApe1.getText().toString());
            jsonObject.put("ape2", editApe2.getText().toString());
            jsonObject.put("email", editEmail.getText().toString());
            jsonObject.put("password", generarPassword());
            jsonObject.put("idCentro", idCentro);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_anadirProfesor, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("state").equals("1")) {
                            Toast.makeText(AnadirProfesor.this, "El profesor se ha insertado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AnadirProfesor.this, "Error al inserta el profesor", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AnadirProfesor.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

            AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }

    public boolean comprobarDatosProfesor(){
        boolean verificar = true;
        Pattern pdni = Pattern.compile("(\\d{8})([-]?)([A-Z]{1})");

        if(editDni.getText().toString().isEmpty()){
            editDni.setError("Debes introducir un dni");
            verificar = false;
        }else if(!pdni.matcher(editDni.getText().toString()).matches()){
            editDni.setError("El formato del dni no es correcto");
            verificar = false;
        }

        if(editNombre.getText().toString().isEmpty()){
            editNombre.setError("Debes introducir un nombre");
            verificar = false;
        }

        if(editApe1.getText().toString().isEmpty()){
            editApe1.setError("Debes introducir 1º apellido");
            verificar = false;
        }

        if (editApe2.getText().toString().isEmpty()){
            editApe2.setError("Debes introducir 2º apellido");
            verificar = false;
        }

        return verificar;
    }

    public String generarPassword(){
        String letras =  "abcdfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        while(builder.length() < 6){
            int index = (int) (random.nextFloat() * letras.length());
            builder.append(letras.charAt(index));
        }

        String password = builder.toString();
        return password;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idCentro", idCentro);
    }
}
