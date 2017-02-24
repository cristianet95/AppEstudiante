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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class AnadirProfesor extends AppCompatActivity  {

    private Toolbar toolbar;
    private EditText editDni;
    private EditText editNombre;
    private EditText editApe1;
    private EditText editApe2;
    private EditText editEmail;
    private String ip = "http://appestudiante.esy.es/modelo/anadirProfesor.php";
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
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("dni", editDni.getText().toString());
        jsonObject.put("nombre", editNombre.getText().toString());
        jsonObject.put("ape1", editApe1.getText().toString());
        jsonObject.put("ape2", editApe2.getText().toString());
        jsonObject.put("email", editEmail.getText().toString());
        jsonObject.put("password", generarPassword());
        jsonObject.put("idCentro", idCentro);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("state").equals("1")){
                        Toast.makeText(AnadirProfesor.this, "El profesor se ha insertado correctamente", Toast.LENGTH_SHORT ).show();
                    }else{
                        Toast.makeText(AnadirProfesor.this, "Error al inserta el alumno", Toast.LENGTH_SHORT ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AnadirProfesor.this, error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                });

        AppEstudianteSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
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

    /* class AsyncCentros extends AsyncTask<String, Void, ArrayList<Centro>> {
        @Override
        protected ArrayList<Centro> doInBackground(String... params) {
            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";
            HttpURLConnection urlConn;
            ArrayList<Centro> listCentro = null;
            Centro centro = null;

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

                    JSONArray centrosJSON = respuestaJSON.getJSONArray("centros");
                    listCentro = new ArrayList<>();
                    for (int i = 0; i < centrosJSON.length(); i++) {
                        centro = new Centro();
                        centro.setId(centrosJSON.getJSONObject(i).getString("id"));
                        centro.setNombre(centrosJSON.getJSONObject(i).getString("nombre"));
                        centro.setCa(centrosJSON.getJSONObject(i).getString("comunidadAutonoma"));
                        centro.setLocalidad(centrosJSON.getJSONObject(i).getString("localidad"));
                        centro.setCalle(centrosJSON.getJSONObject(i).getString("calle"));
                        centro.setTelefono(centrosJSON.getJSONObject(i).getString("telefono"));
                        centro.setFax(centrosJSON.getJSONObject(i).getString("fax"));
                        centro.setWeb(centrosJSON.getJSONObject(i).getString("web"));
                        centro.setTitularidad(centrosJSON.getJSONObject(i).getString("titularidad"));

                        listCentro.add(centro);
                    }
                    return listCentro;
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
        protected void onPostExecute(ArrayList<Centro> centros) {
            if(centros != null){
                mostrarCentros(centros);
            }
        }
    }*/
}
