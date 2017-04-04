package com.example.cristian.appestudiante.vista;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.dialog.DialogExisteUsuario;
import com.example.cristian.appestudiante.modelo.Administrativo;
import com.example.cristian.appestudiante.modelo.Profesor;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editDni;
    private EditText editPasswd;
    private Button btnLogearse;
    private RadioGroup radioButton;
    private RadioButton radioAlumno;
    private RadioButton radioProfesor;
    private RadioButton radioAdministrativo;

    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editDni = (EditText) findViewById(R.id.editDni);
        editPasswd = (EditText) findViewById(R.id.editPasswd);
        btnLogearse = (Button) findViewById(R.id.btnLogearse);
        radioButton = (RadioGroup) findViewById(R.id.radioButton);
        radioAlumno = (RadioButton) findViewById(R.id.radioAlumno);
        radioProfesor = (RadioButton) findViewById(R.id.radioProfesor);
        radioAdministrativo = (RadioButton) findViewById(R.id.radioAdministrativo);

        ip = DireccionesWeb.URL_LoginAlumno;

        btnLogearse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogearse){
            login();
        }
    }

    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radioAlumno:
                if(checked){
                    ip = DireccionesWeb.URL_LoginAlumno;
                }
                break;
            case R.id.radioProfesor:
                if(checked){
                    ip = DireccionesWeb.URL_loginProfesor;
                }
                break;
            case R.id.radioAdministrativo:
                if(checked){
                    ip = DireccionesWeb.URL_LoginAdministrativo;
                }
                break;
        }
    }

    public void login(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("dni", editDni.getText());
            jsonObject.put("password", editPasswd.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("state").equals("1")){

                    }else if(response.getString("state").equals("2")){
                        Administrativo administrativo = new Administrativo();
                        administrativo.setDni(response.getJSONObject("administrativo").getString("dni"));
                        administrativo.setNombre(response.getJSONObject("administrativo").getString("nombre"));
                        administrativo.setApe1(response.getJSONObject("administrativo").getString("ape1"));
                        administrativo.setApe2(response.getJSONObject("administrativo").getString("ape2"));
                        administrativo.setIdCentro(response.getJSONObject("administrativo").getString("idCentro"));
                        accesoInicioAdministrativo(administrativo);
                    }else if(response.getString("state").equals("3")) {
                        Profesor profesor = new Profesor();
                        profesor.setDni(response.getJSONObject("profesor").getString("dni"));
                        profesor.setNombre(response.getJSONObject("profesor").getString("nombre"));
                        profesor.setApe1(response.getJSONObject("profesor").getString("ape1"));
                        profesor.setApe2(response.getJSONObject("profesor").getString("ape2"));
                        profesor.setEmail(response.getJSONObject("profesor").getString("email"));
                        profesor.setPassword(response.getJSONObject("profesor").getString("password"));
                        profesor.setIdCentro(response.getJSONObject("profesor").getString("idCentro"));
                        accesoInicioProfesor(profesor);
                    } else{
                        DialogExisteUsuario deu = new DialogExisteUsuario();
                        deu.show(getSupportFragmentManager(), "notuser");
                    }

                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void accesoInicioAdministrativo(Administrativo ad){
        Intent intent = new Intent(LoginActivity.this, InicioAdministrativo.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("administrativo", ad);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void accesoInicioProfesor(Profesor profesor){
        Intent intent = new Intent(LoginActivity.this, InicioProfesor.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("profesor", profesor);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
