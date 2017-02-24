package com.example.cristian.appestudiante.controlador;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Cristian on 08/02/2017.
 */

//Declaramos la clase final para no sobreescribir metodos
public final class AppEstudianteSingleton {

    //La instancia de la clase
    private static AppEstudianteSingleton singleton;

    private RequestQueue requestQueue;

    private static Context context;

    public AppEstudianteSingleton(Context context) {
        AppEstudianteSingleton.context = context;
        requestQueue = getRequestQueue();
    }

    /*Se asigna memoria a la única instancia del singleton, donde se llama al constructor privado de la clase. Este método debe tener la propiedad synchronized,
     ya que la instancia será accedida desde varios hilos por lo que es necesario evitar bloqueos de acceso*/
    public static synchronized AppEstudianteSingleton getInstance(Context context){
        if(singleton == null){
            singleton = new AppEstudianteSingleton(context);
        }
        return singleton;
    }

    //Obtiene la instancia de la cola de peticiones que se usará a través de toda la aplicación.
    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue  = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    //agregar un nueva petición
    public void addToRequestQueue(Request req){
        getRequestQueue().add(req);
    }
}
