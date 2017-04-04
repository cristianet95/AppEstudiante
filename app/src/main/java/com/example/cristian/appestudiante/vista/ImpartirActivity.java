package com.example.cristian.appestudiante.vista;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.dialog.DialogExisteUsuario;
import com.example.cristian.appestudiante.dialog.DialogListaAsignatura;
import com.example.cristian.appestudiante.dialog.DialogListaCursos;
import com.example.cristian.appestudiante.dialog.DialogListaProfesor;
import com.example.cristian.appestudiante.fragment.AsignaturaListener;
import com.example.cristian.appestudiante.fragment.CursoListener;
import com.example.cristian.appestudiante.fragment.ProfesorListener;
import com.example.cristian.appestudiante.modelo.Administrativo;
import com.example.cristian.appestudiante.modelo.Asignatura;
import com.example.cristian.appestudiante.modelo.Curso;
import com.example.cristian.appestudiante.modelo.Profesor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImpartirActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private TextView txtNombreProfesor;
    private TextView txtApellidoProfesor;
    private TextView txtApellido2Profesor;
    private TextView txtNombreCurso;
    private TextView txtSelectAsignatura;
    private TextView txtAsignatura;
    private TextView txtNombreAsignatura;

    private ImageButton btnSelectProfesor;
    private ImageButton btnSelectCurso;
    private ImageButton btnSelectAsignatura;
    private Button btnAsignarMateria;

    private LinearLayout linearAsignatura;

    private String idCentro;
    private String idCurso;
    private String idProfesor;
    private String idAsignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);

        toolbar = (Toolbar) findViewById(R.id.apptoolbar);
        toolbar.setTitle("Asignar Materia");

        if(savedInstanceState == null) {
            idCentro = (String) getIntent().getExtras().get("idCentro");
        }else{
            idCentro = savedInstanceState.getString("idCentro");
        }

        linearAsignatura = (LinearLayout) findViewById(R.id.linearAsignatura);

        txtNombreProfesor = (TextView) findViewById(R.id.txtNombreProfesor);
        txtApellidoProfesor = (TextView) findViewById(R.id.txtApellidoProfesor);
        txtApellido2Profesor = (TextView) findViewById(R.id.txtApellido2Profesor);
        txtNombreCurso = (TextView) findViewById(R.id.txtNombreCurso);
        txtSelectAsignatura = (TextView) findViewById(R.id.txtSelectAsignatura);
        txtAsignatura = (TextView) findViewById(R.id.txtAsignatura);
        txtNombreAsignatura = (TextView) findViewById(R.id.txtNombreAsignatura);

        btnSelectProfesor = (ImageButton) findViewById(R.id.btnSelectProfesor);
        btnSelectCurso = (ImageButton) findViewById(R.id.btnSelectCurso);
        btnSelectAsignatura = (ImageButton) findViewById(R.id.btnSelectAsignatura);
        btnAsignarMateria = (Button) findViewById(R.id.btnAsignarMateria);

        btnSelectProfesor.setOnClickListener(this);
        btnSelectCurso.setOnClickListener(this);
        btnSelectAsignatura.setOnClickListener(this);
        btnAsignarMateria.setOnClickListener(this);
    }

    public void asignarMateria(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idProfesor", idProfesor);
            jsonObject.put("idAsignatura", idAsignatura);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_asignarMateria, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("state").equals("1")) {
                        Toast.makeText(ImpartirActivity.this, "Se ha asignado correctamente la materia", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ImpartirActivity.this, "No se ha podido asignar la materia", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ImpartirActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                });

        AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("idCentro", idCentro);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSelectProfesor) {
            AsyncProfesores listaProfes = new AsyncProfesores();
            listaProfes.execute(DireccionesWeb.URL_obtenerProfesores);
        } else if (v.getId() == R.id.btnSelectCurso) {
            AsyncCursos listaProfes = new AsyncCursos();
            listaProfes.execute(DireccionesWeb.URL_obtenerCursos+idCentro);
        }else if(v.getId() == R.id.btnSelectAsignatura){
            AsyncAsignatura listaAsignatura = new AsyncAsignatura();
            listaAsignatura.execute(DireccionesWeb.URL_obtenerAsignaturasByCurso+idCurso);
        }else if(v.getId() == R.id.btnAsignarMateria){
            asignarMateria();
        }
    }

    class AsyncProfesores extends AsyncTask<String, Void, ArrayList<Profesor>> implements ProfesorListener {

        @Override
        protected ArrayList<Profesor> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";
            HttpURLConnection urlConn;
            ArrayList<Profesor> listaProfesores = null;
            Profesor profesor = null;

            try {
                url = new URL(cadena);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                int respuesta = connection.getResponseCode();


                StringBuilder result = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    // preparo la cadena de entrada
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // la introduzco en un BufferedReader
                    // StringBuilder.
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line); // Paso toda la entrada al StringBuilder
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos(campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(result.toString());

                    JSONArray profesoresJSON = respuestaJSON.getJSONArray("profesores");
                    listaProfesores = new ArrayList<>();
                    for (int i = 0; i < profesoresJSON.length(); i++) {
                        profesor = new Profesor();
                        profesor.setDni(profesoresJSON.getJSONObject(i).getString("dni"));
                        profesor.setNombre(profesoresJSON.getJSONObject(i).getString("nombre"));
                        profesor.setApe1(profesoresJSON.getJSONObject(i).getString("ape1"));
                        profesor.setApe2(profesoresJSON.getJSONObject(i).getString("ape2"));
                        profesor.setEmail(profesoresJSON.getJSONObject(i).getString("email"));

                        listaProfesores.add(profesor);
                    }
                    return listaProfesores;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Profesor> profesores) {
            if (profesores != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profesores", profesores);
                DialogListaProfesor dla = new DialogListaProfesor();
                dla.setArguments(bundle);
                dla.setCancelable(false);
                dla.setListener(this);
                dla.show(getFragmentManager(), "DialogProfesor");
            }
        }

        @Override
        public void onProfesorListener(Profesor profesor) {
            if (profesor != null) {
                txtNombreProfesor.setText(profesor.getNombre());
                txtApellidoProfesor.setText(profesor.getApe1());
                txtApellido2Profesor.setText(profesor.getApe2());
                idProfesor = profesor.getDni();

                if(idAsignatura != null){
                    btnAsignarMateria.setEnabled(true);
                }
            } else {
                txtNombreProfesor.setText("");
                txtApellidoProfesor.setText("");
                txtApellido2Profesor.setText("");
                idProfesor = null;
                btnAsignarMateria.setEnabled(false);
            }
        }
    }

    class AsyncCursos extends AsyncTask<String, Void, ArrayList<Curso>> implements CursoListener {

        @Override
        protected ArrayList<Curso> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";
            HttpURLConnection urlConn;
            ArrayList<Curso> listaCurso = null;
            Curso curso = null;

            try {
                url = new URL(cadena);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                int respuesta = connection.getResponseCode();


                StringBuilder result = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    // preparo la cadena de entrada
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // la introduzco en un BufferedReader
                    // StringBuilder.
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line); // Paso toda la entrada al StringBuilder
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos(campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(result.toString());

                    JSONArray cursosJSON = respuestaJSON.getJSONArray("cursos");
                    listaCurso = new ArrayList<>();
                    for (int i = 0; i < cursosJSON.length(); i++) {
                        curso = new Curso();
                        curso.setId(cursosJSON.getJSONObject(i).getString("id"));
                        curso.setNombre(cursosJSON.getJSONObject(i).getString("nombre"));
                        listaCurso.add(curso);
                    }
                    return listaCurso;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Curso> cursos) {
            if (cursos != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cursos", cursos);
                DialogListaCursos dlc = new DialogListaCursos();
                dlc.setArguments(bundle);
                dlc.setCancelable(false);
                dlc.setListener(this);
                dlc.show(getSupportFragmentManager(), "dialogCursos");
            }
        }

        @Override
        public void onCursoSeleccionado(Curso curso) {
            if(curso != null){
                txtNombreCurso.setText(curso.getNombre());
                txtSelectAsignatura.setVisibility(View.VISIBLE);
                btnSelectAsignatura.setVisibility(View.VISIBLE);
                linearAsignatura.setBackgroundColor(Color.parseColor("#4191e0"));
                txtAsignatura.setTextColor(Color.WHITE);
                idCurso = curso.getId();
            }else{
                txtNombreCurso.setText("");
                txtSelectAsignatura.setVisibility(View.INVISIBLE);
                btnSelectAsignatura.setVisibility(View.INVISIBLE);
                linearAsignatura.setBackgroundColor(Color.parseColor("#dededf"));
                txtAsignatura.setTextColor(Color.parseColor("#4d4d53"));
                idCurso = null;
                txtNombreAsignatura.setText("");
                idAsignatura = null;
                btnAsignarMateria.setEnabled(false);
            }
        }
    }

    class AsyncAsignatura extends AsyncTask<String, Void, ArrayList<Asignatura>> implements AsignaturaListener {

        @Override
        protected ArrayList<Asignatura> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";
            HttpURLConnection urlConn;
            ArrayList<Asignatura> listaAsignatura = null;
            Asignatura asignatura = null;

            try {
                url = new URL(cadena);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                int respuesta = connection.getResponseCode();


                StringBuilder result = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    // preparo la cadena de entrada
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // la introduzco en un BufferedReader
                    // StringBuilder.
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line); // Paso toda la entrada al StringBuilder
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos(campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(result.toString());
                    String state = respuestaJSON.getString("state");

                    if(state.equals("1")){
                        JSONArray cursosJSON = respuestaJSON.getJSONArray("asignaturas");
                        listaAsignatura = new ArrayList<>();
                        for (int i = 0; i < cursosJSON.length(); i++) {
                            asignatura = new Asignatura();
                            asignatura.setId(cursosJSON.getJSONObject(i).getString("codigo"));
                            asignatura.setNombre(cursosJSON.getJSONObject(i).getString("nombre"));
                            listaAsignatura.add(asignatura);
                        }
                    }

                    return listaAsignatura;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asignatura> asignaturas) {
            if(asignaturas != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable("asignaturas", asignaturas);
                DialogListaAsignatura dla = new DialogListaAsignatura();
                dla.setArguments(bundle);
                dla.setCancelable(false);
                dla.setListener(this);
                dla.show(getFragmentManager(), "DialogAsignatura");
            }
        }

        @Override
        public void onAsignaturaSelected(Asignatura asignatura) {
            if(asignatura != null){
                txtNombreAsignatura.setText(asignatura.getNombre());
                idAsignatura = asignatura.getId();

                if(idProfesor != null){
                    btnAsignarMateria.setEnabled(true);
                }
            }else{
                txtNombreAsignatura.setText("");
                idAsignatura = null;
                btnAsignarMateria.setEnabled(false);
            }
        }
    }
}
