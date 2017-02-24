package com.example.cristian.appestudiante.vista;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.fragment.FragmentListaProfesor;
import com.example.cristian.appestudiante.fragment.ProfesorListener;
import com.example.cristian.appestudiante.modelo.Alumno;
import com.example.cristian.appestudiante.modelo.Profesor;

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

public class ListaProfesor extends AppCompatActivity implements ProfesorListener {

    private FragmentListaProfesor frgListaProfesor;
    private String ipProfesores = "http://appestudiante.esy.es/modelo/obtenerProfesores.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_profesores);

        frgListaProfesor = (FragmentListaProfesor) getSupportFragmentManager().findFragmentById(R.id.frgListaProfesores);

        AsyncProfesores asyncProfesores = new AsyncProfesores();
        asyncProfesores.execute(ipProfesores);

        frgListaProfesor.setListener(this);
    }

    @Override
    public void onProfesorListener(Profesor profesor) {

    }

    class AsyncProfesores extends AsyncTask<String, Void, ArrayList<Profesor>> {

        @Override
        protected ArrayList<Profesor> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";
            HttpURLConnection urlConn;
            ArrayList<Profesor> listaProfesores= null;
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
            if(profesores != null){
                frgListaProfesor.mostrarProfesores(profesores);
            }
        }
    }
}
