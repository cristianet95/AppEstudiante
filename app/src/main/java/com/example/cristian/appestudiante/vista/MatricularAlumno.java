package com.example.cristian.appestudiante.vista;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.example.cristian.appestudiante.dialog.DialogListaAlumnos;
import com.example.cristian.appestudiante.dialog.DialogListaCursos;
import com.example.cristian.appestudiante.fragment.AlumnoListener;
import com.example.cristian.appestudiante.fragment.CursoListener;
import com.example.cristian.appestudiante.fragment.OnAsyncFinish;
import com.example.cristian.appestudiante.modelo.Alumno;
import com.example.cristian.appestudiante.modelo.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MatricularAlumno extends AppCompatActivity implements View.OnClickListener, OnAsyncFinish, AlumnoListener, CursoListener {

    private Toolbar toolbar;
    private TextView txtDni;
    private TextView txtNombreAlumno;
    private TextView txtPrimerApellido;
    private TextView txtSegundoApellido;
    private TextView txtNombreMatricula;

    private ImageButton btnElegirAlumno;
    private ImageButton btnElegirCurso;
    private Button btnMatricular;

    private Alumno alumno;
    private Curso curso;

    private String idCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricular_alumno);

        //Añadiendo Toolbar
        toolbar = (Toolbar) findViewById(R.id.apptoolbar);
        toolbar.setTitle("Matricular Alumno");
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            idCentro = (String) getIntent().getExtras().get("idCentro");
        }else{
            idCentro = savedInstanceState.getString("idCentro");
        }

        txtDni = (TextView) findViewById(R.id.txtDniMatricula);
        txtNombreAlumno = (TextView) findViewById(R.id.txtNombreAlumnoMatricula);
        txtPrimerApellido = (TextView) findViewById(R.id.txt1ApellidoMatricula);
        txtSegundoApellido = (TextView) findViewById(R.id.txt2ApellidoMatricula);
        txtNombreMatricula = (TextView) findViewById(R.id.txtNombreCursoMatricula);

        btnElegirAlumno = (ImageButton) findViewById(R.id.btnElegirAlumno);
        btnElegirCurso = (ImageButton) findViewById(R.id.btnElegirCurso);
        btnMatricular = (Button) findViewById(R.id.btnMatricularse);

        btnElegirAlumno.setOnClickListener(this);
        btnElegirCurso.setOnClickListener(this);
        btnMatricular.setOnClickListener(this);
    }

    public void setDataAlumno(){
        if(alumno != null){
            txtDni.setText(alumno.getDni());
            txtNombreAlumno.setText(alumno.getNombre());
            txtPrimerApellido.setText(alumno.getApe1());
            txtSegundoApellido.setText(alumno.getApe2());
        }else{
            txtDni.setText("");
            txtNombreAlumno.setText("");
            txtPrimerApellido.setText("");
            txtSegundoApellido.setText("");
        }
    }

    public void setDataCurso(){
        if(curso != null){
            txtNombreMatricula.setText(curso.getNombre());
        }else{
            txtNombreMatricula.setText("");
        }
    }

    public void matricularAlumno(){
        if(alumno == null || curso == null){
            Toast.makeText(this, "Debes elegir alumno y curso para matricular", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("idCentro", idCentro);
                jsonObject.put("idCurso", curso.getId());
                jsonObject.put("dniAlumno", alumno.getDni());
                jsonObject.put("fecha", Calendar.getInstance().getTime());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DireccionesWeb.URL_matricularAlumno, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getString("state").equals("1")){
                            Toast.makeText(getApplicationContext(), "Se ha matriculado correctamente el alumno", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se ha podido matricular correctamente el alumno", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnElegirAlumno:
                AsyncAlumnos async = new AsyncAlumnos(this);
                async.execute(DireccionesWeb.URL_obtenerAlumnos);
                break;
            case R.id.btnElegirCurso:
                AsyncCursos asyncCursos = new AsyncCursos(this);
                asyncCursos.execute(DireccionesWeb.URL_obtenerCursos+idCentro);
                break;
            case R.id.btnMatricularse:
                matricularAlumno();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idCentro", idCentro);
    }

    @Override
    public void onAsyncAlumnosCompletado(Object object) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("alumnos", (Serializable) object);
        DialogListaAlumnos dla = new DialogListaAlumnos();
        dla.setArguments(bundle);
        dla.setCancelable(false);
        dla.setListener(this);
        dla.show(getSupportFragmentManager(), "dialogAlumnos");
    }

    @Override
    public void onAsyncCursosCompletado(Object object) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cursos", (Serializable) object);
        DialogListaCursos dlc = new DialogListaCursos();
        dlc.setArguments(bundle);
        dlc.setCancelable(false);
        dlc.setListener(this);
        dlc.show(getSupportFragmentManager(), "dialogCursos");
    }

    @Override
    public void onAlumnoSeleccionado(Alumno alumno) {
        this.alumno = alumno;
        setDataAlumno();
    }

    @Override
    public void onCursoSeleccionado(Curso curso) {
        this.curso = curso;
        setDataCurso();
    }

    class AsyncAlumnos extends AsyncTask<String, Void, ArrayList<Alumno>> {

        private OnAsyncFinish listener;

        public AsyncAlumnos(OnAsyncFinish listener){
            this.listener = listener;
        }

        @Override
        protected ArrayList<Alumno> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";
            HttpURLConnection urlConn;
            ArrayList<Alumno> listaAlumnos = null;
            Alumno alumno = null;

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

                    JSONArray centrosJSON = respuestaJSON.getJSONArray("alumnos");
                    listaAlumnos = new ArrayList<>();
                    for (int i = 0; i < centrosJSON.length(); i++) {
                        alumno = new Alumno();
                        alumno.setDni(centrosJSON.getJSONObject(i).getString("dni"));
                        alumno.setNombre(centrosJSON.getJSONObject(i).getString("nombre"));
                        alumno.setApe1(centrosJSON.getJSONObject(i).getString("ape1"));
                        alumno.setApe2(centrosJSON.getJSONObject(i).getString("ape2"));
                        alumno.setDomicilio(centrosJSON.getJSONObject(i).getString("domicilio"));
                        alumno.setLocalidad(centrosJSON.getJSONObject(i).getString("localidad"));
                        alumno.setCodigoPostal(centrosJSON.getJSONObject(i).getString("codigopostal"));
                        alumno.setNacionalidad(centrosJSON.getJSONObject(i).getString("nacionalidad"));

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        alumno.setFecha(sdf.parse(centrosJSON.getJSONObject(i).getString("fecha")));
                        alumno.setEdad(centrosJSON.getJSONObject(i).getString("edad"));
                        alumno.setTelefono(centrosJSON.getJSONObject(i).getString("telefono"));
                        alumno.setMovil(centrosJSON.getJSONObject(i).getString("movil"));
                        alumno.setEmail(centrosJSON.getJSONObject(i).getString("email"));

                        listaAlumnos.add(alumno);
                    }
                    return listaAlumnos;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Alumno> alumnos) {
            if(alumnos != null){
                listener.onAsyncAlumnosCompletado(alumnos);
            }
        }
    }


    class AsyncCursos extends AsyncTask<String, Void, ArrayList<Curso>> {

        private OnAsyncFinish listener;

        public AsyncCursos(OnAsyncFinish listener){
            this.listener = listener;
        }

        @Override
        protected ArrayList<Curso> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";
            HttpURLConnection urlConn;
            ArrayList<Curso> listaCurso= null;
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
            if(cursos != null){
                listener.onAsyncCursosCompletado(cursos);
            }
        }
    }
}
