package com.example.cristian.appestudiante.controlador;

/**
 * Created by Cristian on 25/02/2017.
 */

public class DireccionesWeb {

    //LOGINS
    public static final String URL_LoginAlumno = "http://appestudiante.esy.es/modelo/loginAlumno.php";
    public static final String URL_LoginAdministrativo = "http://appestudiante.esy.es/modelo/loginAdministrativo.php";

    //CENTRO
    public static String URL_ObtenerCentro= "http://appestudiante.esy.es/modelo/getCentroById.php?idCentro=";
    public static String URL_obtenerCursos = "http://appestudiante.esy.es/modelo/getCursosDisponibles.php?idCentro=";

    //ALUMNO
    public static final String URL_anadirAlumno = "http://appestudiante.esy.es/modelo/anadirAlumno.php";
    public static final String URL_obtenerAlumnos = "http://appestudiante.esy.es/modelo/obtenerAlumnos.php";
    public static final String URL_matricularAlumno = "http://appestudiante.esy.es/modelo/matricularAlumno.php";
    public static final String URL_actualizarAlumno = "http://appestudiante.esy.es/modelo/actualizarAlumno.php";
    public static final String URL_eliminarAlumno = "http://appestudiante.esy.es/modelo/eliminarAlumno.php";

    //PROFESOR
    public static final String URL_anadirProfesor = "http://appestudiante.esy.es/modelo/anadirProfesor.php";
    public static final String URL_obtenerProfesores = "http://appestudiante.esy.es/modelo/obtenerProfesores.php";
}
