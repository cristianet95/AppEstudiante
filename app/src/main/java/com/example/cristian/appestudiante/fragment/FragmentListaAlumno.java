package com.example.cristian.appestudiante.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorFragmentUsuarios;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.modelo.Alumno;
import com.example.cristian.appestudiante.vista.DetallesAlumno;
import com.example.cristian.appestudiante.vista.ListaAlumnos;

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

/**
 * Created by mati on 17/02/2017.
 */

public class FragmentListaAlumno extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    static final int ALUMNO_SELECCIONADO = 1;

    private ListView listaAlumnos;
    private Toolbar toolbar;
    private EditText editSearch;
    private TextView txtSearch;
    private ImageButton menuSearch;
    private int modoBusqueda = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_alumnos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        listaAlumnos = (ListView) getView().findViewById(R.id.listaAlumnos);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbarlistalumnos);
        editSearch = (EditText) getView().findViewById(R.id.editSearch);
        txtSearch = (TextView) getView().findViewById(R.id.txtTituloSearch);
        menuSearch = (ImageButton) getView().findViewById(R.id.btnRadioSearch);
        txtSearch.setText("Lista Alumnos");
        editSearch.setHint("Buscar por dni");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        menuSearch.setOnClickListener(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AsyncAlumnos search = new AsyncAlumnos();
                switch (modoBusqueda) {
                        case 0:
                            search.execute(DireccionesWeb.URL_obtenerAlumnosPorId+editSearch.getText().toString());
                            break;
                        case 1:
                            search.execute(DireccionesWeb.URL_obtenerAlumnosPorNombre+editSearch.getText().toString());
                            break;
                        case 2:
                            search.execute(DireccionesWeb.URL_obtenerAlumnosPrimerApellido+editSearch.getText().toString());
                            break;
                        case 3:
                            search.execute(DireccionesWeb.URL_obtenerAlumnoSegundoApellido+editSearch.getText().toString());
                            break;
                    }
            }
        });

        AsyncAlumnos asyncAlumnos = new AsyncAlumnos();
        asyncAlumnos.execute(DireccionesWeb.URL_obtenerAlumnos);
    }

    public void mostrarAlumnos(ArrayList<Alumno> alumnos){
        listaAlumnos.setAdapter(new AdaptadorFragmentUsuarios(this, alumnos));
        listaAlumnos.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean existeFragment = (getActivity().getSupportFragmentManager().findFragmentById(R.id.frgDetalleAlumno)) != null;

        if(existeFragment){

        }else{
            Intent intent = new Intent(getActivity(), DetallesAlumno.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alumno", (Serializable) listaAlumnos.getAdapter().getItem(position));

            intent.putExtras(bundle);
            startActivityForResult(intent, ALUMNO_SELECCIONADO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                AsyncAlumnos search = new AsyncAlumnos();
                switch (modoBusqueda) {
                    case 0:
                        search.execute(DireccionesWeb.URL_obtenerAlumnosPorId+editSearch.getText().toString());
                        break;
                    case 1:
                        search.execute(DireccionesWeb.URL_obtenerAlumnosPorNombre+editSearch.getText().toString());
                        break;
                    case 2:
                        search.execute(DireccionesWeb.URL_obtenerAlumnosPrimerApellido+editSearch.getText().toString());
                        break;
                    case 3:
                        search.execute(DireccionesWeb.URL_obtenerAlumnoSegundoApellido+editSearch.getText().toString());
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRadioSearch:
                String[] items = {"DNI", "Nombre", "1º Apellido", "2º Apellido"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Buscar alumno por:");
                dialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                modoBusqueda = 0;
                                editSearch.setHint("Buscar por dni");
                                break;
                            case 1:
                                modoBusqueda = 1;
                                editSearch.setHint("Buscar por nombre");
                                break;
                            case 2:
                                modoBusqueda = 2;
                                editSearch.setHint("Buscar 1º apellido");
                                break;
                            case 3:
                                modoBusqueda = 3;
                                editSearch.setHint("Buscar 2º apellido");
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.create().show();
                break;
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
                mostrarAlumnos(alumnos);
            }
        }
    }
}
