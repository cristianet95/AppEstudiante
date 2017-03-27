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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.AdaptadorProfesor;
import com.example.cristian.appestudiante.controlador.DireccionesWeb;
import com.example.cristian.appestudiante.modelo.Profesor;
import com.example.cristian.appestudiante.vista.DetallesProfesor;

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
import java.util.ArrayList;

/**
 * Created by Cristian on 23/02/2017.
 */

public class FragmentListaProfesor extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listaProfesores;
    private Toolbar toolbar;
    private EditText editSearch;
    private TextView txtTitulo;
    private ImageButton menuSearch;

    private int modoBusqueda = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_profesor, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaProfesores = (ListView) getView().findViewById(R.id.listaProfesor);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbarlistaprofesor);
        editSearch = (EditText) getView().findViewById(R.id.editSearch);
        menuSearch = (ImageButton) getView().findViewById(R.id.btnRadioSearch);
        txtTitulo = (TextView) getView().findViewById(R.id.txtTituloSearch);


        txtTitulo.setText("Lista Profesores");
        editSearch.setHint("Buscar por dni");
        menuSearch.setOnClickListener(this);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AsyncProfesores search = new AsyncProfesores();
                switch (modoBusqueda) {
                    case 0:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPorDni+editSearch.getText().toString());
                        break;
                    case 1:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPorNombre+editSearch.getText().toString());
                        break;
                    case 2:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPrimerApellido+editSearch.getText().toString());
                        break;
                    case 3:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresSegundoApellido+editSearch.getText().toString());
                        break;
                }
            }
        });


        AsyncProfesores asyncProfesores = new AsyncProfesores();
        asyncProfesores.execute(DireccionesWeb.URL_obtenerProfesores);
    }

    public void mostrarProfesores(ArrayList<Profesor> profesores){
        listaProfesores.setAdapter(new AdaptadorProfesor(this, profesores));
        listaProfesores.setOnItemClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                AsyncProfesores search = new AsyncProfesores();
                switch (modoBusqueda) {
                    case 0:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPorDni+editSearch.getText().toString());
                        break;
                    case 1:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPorNombre+editSearch.getText().toString());
                        break;
                    case 2:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresPrimerApellido+editSearch.getText().toString());
                        break;
                    case 3:
                        search.execute(DireccionesWeb.URL_obtenerProfesoresSegundoApellido+editSearch.getText().toString());
                        break;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean existeFragment = (getActivity().getSupportFragmentManager().findFragmentById(R.id.frgDetalleProfesor)) != null;

        if(existeFragment){

        }else{
            Intent intent = new Intent(getActivity(), DetallesProfesor.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("profesor", (Serializable) listaProfesores.getAdapter().getItem(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
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
            if(profesores != null){
                mostrarProfesores(profesores);
            }
        }
    }
}
