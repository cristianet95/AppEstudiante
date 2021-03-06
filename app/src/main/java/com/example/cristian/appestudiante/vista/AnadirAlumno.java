package com.example.cristian.appestudiante.vista;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.dialog.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cristian on 13/02/2017.
 */

public class AnadirAlumno extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText editDniAlumno;
    private EditText editNombreAlumno;
    private EditText editApe1Alumno;
    private EditText editApe2Alumno;
    private EditText editDomicilio;
    private EditText editLocalidad;
    private EditText editCodigoPostal;
    private EditText editNacionalidad;
    private TextView textFechaNac;
    private EditText editEdad;
    private EditText editTelefono;
    private EditText editMovil;
    private EditText editEmail;

    private ImageButton dialogDatePicker;
    private Button btnInsertarAlumno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alumno);

        //REFERENCIANDO LOS COMPONENTES
        toolbar = (Toolbar) findViewById(R.id.apptoolbar);
        toolbar.setTitle("Añadir Alumno");

        editDniAlumno = (EditText) findViewById(R.id.editDniAlumno);
        editNombreAlumno = (EditText) findViewById(R.id.editNombreAlumno);
        editApe1Alumno = (EditText) findViewById(R.id.editApellido1Alumno);
        editApe2Alumno = (EditText) findViewById(R.id.editApellido2Alumno);
        editDomicilio = (EditText) findViewById(R.id.editDomicilio);
        editLocalidad = (EditText) findViewById(R.id.editLocalidad);
        editCodigoPostal = (EditText) findViewById(R.id.editCodigoPostal);
        editNacionalidad = (EditText) findViewById(R.id.editNacionalidad);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editMovil = (EditText) findViewById(R.id.editMovil);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editEdad = (EditText) findViewById(R.id.editEdad);

        textFechaNac = (TextView) findViewById(R.id.textFechaNac);

        btnInsertarAlumno = (Button) findViewById(R.id.btnInsertarAlumno);
        dialogDatePicker = (ImageButton) findViewById(R.id.btnDatePicker);
    }


    public void insertarAlumno(View v) throws JSONException, ParseException {
        if(comprobarDatosAlumno()){
            JSONObject jsonObject = putData();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_anadirAlumno, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getString("state").equals("1")){
                            Toast.makeText(AnadirAlumno.this, "El alumno se ha insertado correctamente", Toast.LENGTH_SHORT ).show();
                        }else{
                            Toast.makeText(AnadirAlumno.this, "Error al inserta el alumno", Toast.LENGTH_SHORT ).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AnadirAlumno.this, error.toString(), Toast.LENGTH_SHORT ).show();
                        }
                    });

            AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }

    public JSONObject putData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dni", editDniAlumno.getText().toString());

            jsonObject.put("nombre", editNombreAlumno.getText().toString());

            jsonObject.put("ape1", editApe1Alumno.getText().toString());
            jsonObject.put("ape2", editApe2Alumno.getText().toString());
            jsonObject.put("domicilio", editDomicilio.getText().toString());
            jsonObject.put("localidad", editLocalidad.getText().toString());
            jsonObject.put("codigopostal", editCodigoPostal.getText().toString());
            jsonObject.put("nacionalidad", editNacionalidad.getText().toString());

            String f = textFechaNac.getText().toString();
            String [] date = f.split("/");
            Date fechaNac =   sdf.parse(date[2]+ "-" + date[1] + "-" + date[0]);

            jsonObject.put("fecha", fechaNac);
            jsonObject.put("edad", Integer.parseInt(editEdad.getText().toString()));
            jsonObject.put("telefono", editTelefono.getText().toString());
            jsonObject.put("movil", editTelefono.getText().toString());
            jsonObject.put("email", editEmail.getText().toString());
            jsonObject.put("password", generarPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public boolean comprobarDatosAlumno(){
        boolean verificar = true;
        Pattern pdni = Pattern.compile("(\\d{8})([-]?)([A-Z]{1})");

        if(editDniAlumno.getText().toString().isEmpty()){
            editDniAlumno.setError("Debes introducir un dni");
            verificar = false;
        }else if(!pdni.matcher(editDniAlumno.getText().toString()).matches()){
            editDniAlumno.setError("El formato del dni no es correcto");
            verificar = false;
        }

        if(textFechaNac.getText().toString().isEmpty()){
            verificar = false;
            Toast.makeText(this, "Debes introducir una fecha", Toast.LENGTH_SHORT).show();
        }

        if(editNombreAlumno.getText().toString().isEmpty()){
            editNombreAlumno.setError("Debes introducir un nombre");
            verificar = false;
        }

        if(editApe1Alumno.getText().toString().isEmpty()){
            editApe1Alumno.setError("Debes introducir el 1º Apellido");
            verificar = false;
        }

        if(editApe2Alumno.getText().toString().isEmpty()){
            editApe2Alumno.setError("Debes introducir el 2º Apellido");
            verificar = false;
        }

        if(editDomicilio.getText().toString().isEmpty()){
            editDomicilio.setError("Debes introducir un domicilio");
        }

        if(editLocalidad.getText().toString().isEmpty()){
            editLocalidad.setError("Debes introducir una localidad");
        }

        if(editCodigoPostal.getText().toString().isEmpty()){
            editCodigoPostal.setError("Debes introducir un codigo postal");
            verificar = false;
        }

        if(editEdad.getText().toString().isEmpty()){
            editEdad.setError("Debes introducir una edad");
            verificar = false;
        }

        return verificar;
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
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
}
