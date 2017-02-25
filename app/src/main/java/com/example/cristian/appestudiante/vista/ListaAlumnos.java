package com.example.cristian.appestudiante.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.fragment.AlumnoListener;
import com.example.cristian.appestudiante.fragment.FragmentListaAlumno;
import com.example.cristian.appestudiante.modelo.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Cristian on 16/02/2017.
 */

public class ListaAlumnos extends AppCompatActivity implements AlumnoListener {

    private FragmentListaAlumno frgListaAlumno;
    static final int ALUMNO_SELECCIONADO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_alumnos);

        frgListaAlumno = (FragmentListaAlumno) getSupportFragmentManager().findFragmentById(R.id.frgListaAlumnos);

        AsyncAlumnos asyncAlumnos = new AsyncAlumnos();
        asyncAlumnos.execute(DireccionesWeb.URL_obtenerAlumnos);

        frgListaAlumno.setListener(this);
    }

    @Override
    public void onAlumnoSeleccionado(Alumno alumno) {
        boolean existeFragment = (getSupportFragmentManager().findFragmentById(R.id.frgDetalleAlumno)) != null;

        if(existeFragment){

        }else{
            Intent intent = new Intent(ListaAlumnos.this, DetallesAlumno.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alumno", alumno);

            intent.putExtras(bundle);
            startActivityForResult(intent, ALUMNO_SELECCIONADO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                AsyncAlumnos asyncAlumnos = new AsyncAlumnos();
                asyncAlumnos.execute(DireccionesWeb.URL_obtenerAlumnos);
            }
        }
    }

    class AsyncAlumnos extends AsyncTask<String, Void, ArrayList<Alumno>> {

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
                frgListaAlumno.mostrarAlumnos(alumnos);
            }
        }
    }
}
