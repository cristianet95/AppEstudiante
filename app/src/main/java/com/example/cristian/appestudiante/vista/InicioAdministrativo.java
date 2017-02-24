package com.example.cristian.appestudiante.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.adapter.ExpandableListAdapter;
import com.example.cristian.appestudiante.adapter.ListaObjetos;
import com.example.cristian.appestudiante.controlador.AppEstudianteSingleton;
import com.example.cristian.appestudiante.modelo.Administrativo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InicioAdministrativo extends AppCompatActivity implements ExpandableListView.OnChildClickListener {


    private Toolbar toolbar;
    private Administrativo ad;
    private ExpandableListView lista;
    private ArrayList<ListaObjetos> titulos;
    private HashMap<String, List<ListaObjetos>> items;
    private String ip = "http://appestudiante.esy.es/modelo/getCentroById.php?idCentro=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Añadiendo Toolbar
        toolbar = (Toolbar) findViewById(R.id.apptoolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        //Inicializando componentes
        lista = (ExpandableListView) findViewById(R.id.listaExpandible);
        cargarDatos();
        lista.setAdapter(new ExpandableListAdapter(this, titulos, items));
        lista.setOnChildClickListener(this);

        //Recogiendo valores del Administrador
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();

            if (bundle == null) {
                ad = null;
            } else {
                ad = (Administrativo) bundle.get("administrativo");
            }
        } else {
            ad = (Administrativo) savedInstanceState.getSerializable("administrativo");
        }
    }

    public void cargarDatos() {
        titulos = new ArrayList<>();
        titulos.add(new ListaObjetos("Centro", R.drawable.colegio));
        titulos.add(new ListaObjetos("Alumno", R.drawable.student));
        titulos.add(new ListaObjetos("Profesor", R.drawable.teacher));

        List<ListaObjetos> itemsCentro = new ArrayList<>();
        itemsCentro.add(new ListaObjetos("Datos del Centro", R.drawable.information));
        itemsCentro.add(new ListaObjetos("Localizacion del centro", R.drawable.location));

        List<ListaObjetos> itemsAlumno = new ArrayList<>();
        itemsAlumno.add(new ListaObjetos("Añadir Alumno", R.drawable.add_button));
        itemsAlumno.add(new ListaObjetos("Matricular Alumno", R.drawable.matricula));
        itemsAlumno.add(new ListaObjetos("Lista de alumnos", R.drawable.lista));

        List<ListaObjetos> itemsProfesor = new ArrayList<>();
        itemsProfesor.add(new ListaObjetos("Añadir Profesor", R.drawable.add_button));
        itemsProfesor.add(new ListaObjetos("Lista de profesores", R.drawable.lista));

        items = new HashMap<>();
        items.put(titulos.get(0).getTitulo(), itemsCentro);
        items.put(titulos.get(1).getTitulo(), itemsAlumno);
        items.put(titulos.get(2).getTitulo(), itemsProfesor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("administrativo", ad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.opcionDatosCentro:
                return true;
            case R.id.opcionLocalizacion:
                return true;
            case R.id.opcionAnadirAlumno:
                intent = new Intent(InicioAdministrativo.this, AnadirAlumno.class);
                startActivity(intent);
                return true;
            case R.id.opcionListarAlumnos:
                intent = new Intent(InicioAdministrativo.this, ListaAlumnos.class);
                startActivity(intent);
                return true;
            case R.id.opcionMatricularAlumnos:
                intent = new Intent(InicioAdministrativo.this, MatricularAlumno.class);
                intent.putExtra("idCentro", ad.getIdCentro());
                startActivity(intent);
                return true;
            case R.id.opcionAnadirProfesor:
                intent = new Intent(InicioAdministrativo.this, AnadirProfesor.class);
                intent.putExtra("idCentro", ad.getIdCentro());
                startActivity(intent);
                return true;
            case R.id.opcionEliminarProfesor:
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent;
        if (groupPosition == 0) {
            if (childPosition == 0) {

            } else if (childPosition == 1) {
                executeActivityMaps();
            }
        } else if (groupPosition == 1) {
            if (childPosition == 0) {
                intent = new Intent(InicioAdministrativo.this, AnadirAlumno.class);
                startActivity(intent);
                return true;
            } else if (childPosition == 1) {
                intent = new Intent(InicioAdministrativo.this, MatricularAlumno.class);
                intent.putExtra("idCentro", ad.getIdCentro());
                startActivity(intent);
                return true;
            }else if(childPosition == 2){
                intent = new Intent(InicioAdministrativo.this, ListaAlumnos.class);
                startActivity(intent);
                return true;
            }
        } else if (groupPosition == 2) {
            if (childPosition == 0) {
                intent = new Intent(InicioAdministrativo.this, AnadirProfesor.class);
                intent.putExtra("idCentro", ad.getIdCentro());
                startActivity(intent);
                return true;
            } else if (childPosition == 1) {
                intent = new Intent(InicioAdministrativo.this, ListaProfesor.class);
                startActivity(intent);
                return true;
            }
        }

        return true;
    }

    public void executeActivityMaps() {
        StringRequest jor = new StringRequest(Request.Method.GET, ip+ad.getIdCentro(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("state").equals("1")){
                        Intent intent = new Intent(InicioAdministrativo.this, MapsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putFloat("latitud",Float.parseFloat(jsonObject.getJSONObject("centro").getString("latitud")));
                        bundle.putFloat("longitud", Float.parseFloat(jsonObject.getJSONObject("centro").getString("longitud")));
                        bundle.putString("nombre", jsonObject.getJSONObject("centro").getString("nombre"));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else if(jsonObject.getString("state").equals("2")){
                        Toast.makeText(InicioAdministrativo.this, "No estas relacionado con ningun centro", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InicioAdministrativo.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppEstudianteSingleton.getInstance(this).addToRequestQueue(jor);
    }
}
