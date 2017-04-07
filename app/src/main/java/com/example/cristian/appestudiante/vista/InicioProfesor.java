package com.example.cristian.appestudiante.vista;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorAsignatura;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.dialog.DialogListaAsignatura;
import com.example.cristian.appestudiante.fragment.AsignaturaListener;
import com.example.cristian.appestudiante.modelo.Asignatura;
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
import java.util.ArrayList;

public class InicioProfesor extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private Profesor profesor;
    private ListView listaAsignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_profesor);

        toolbar = (Toolbar) findViewById(R.id.iniprofetoolbar);
        toolbar.setTitle("Inicio profesor");

        listaAsignatura = (ListView) findViewById(R.id.listaAsignaturas);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();

            if(bundle == null){
                profesor = null;
            }else{
                profesor = (Profesor) bundle.getSerializable("profesor");
            }
        }else{
            profesor = (Profesor) savedInstanceState.getSerializable("profesor");
        }

        AsyncAsignatura asyncAsignatura = new AsyncAsignatura();
        asyncAsignatura.execute(DireccionesWeb.URL_obtenerAsignaturasByProfesor+profesor.getDni());

        listaAsignatura.setOnItemClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("profesor", profesor);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(InicioProfesor.this, AsignaturaOpciones.class);
        startActivity(intent);
    }

    class AsyncAsignatura extends AsyncTask<String, Void, ArrayList<Asignatura>> {

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
                listaAsignatura.setAdapter(new AdaptadorAsignatura(getBaseContext(), asignaturas));
        }
    }
}
