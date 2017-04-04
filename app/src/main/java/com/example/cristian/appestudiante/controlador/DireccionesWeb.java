package com.example.cristian.appestudiante.controlador;

/**
 * Created by Cristian on 25/02/2017.
 */

public class DireccionesWeb {

    //LOGINS
    public static final String URL_LoginAlumno = "http://appestudiante.esy.es/modelo/loginAlumno.php";
    public static final String URL_LoginAdministrativo = "http://appestudiante.esy.es/modelo/loginAdministrativo.php";

    //CENTRO
    public static final String URL_ObtenerCentro= "http://appestudiante.esy.es/modelo/getCentroById.php?idCentro=";
    public static final String URL_obtenerCursos = "http://appestudiante.esy.es/modelo/getCursosDisponibles.php?idCentro=";
    public static final String URL_obtenerAsignaturasByCurso = "http://appestudiante.esy.es/modelo/getAsignaturasDisponibles.php?idCurso=";

    //ALUMNO
    public static final String URL_anadirAlumno = "http://appestudiante.esy.es/modelo/anadirAlumno.php";
    public static final String URL_obtenerAlumnos = "http://appestudiante.esy.es/modelo/obtenerAlumnos.php";
    public static final String URL_matricularAlumno = "http://appestudiante.esy.es/modelo/matricularAlumno.php";
    public static final String URL_actualizarAlumno = "http://appestudiante.esy.es/modelo/actualizarAlumno.php";
    public static final String URL_eliminarAlumno = "http://appestudiante.esy.es/modelo/eliminarAlumno.php";
    public static final String URL_obtenerAlumnosPorId = "http://appestudiante.esy.es/modelo/obtenerAlumnosPorDni.php?dni=";
    public static final String URL_obtenerAlumnosPorNombre = "http://appestudiante.esy.es/modelo/obtenerAlumnosPorNombre.php?nombre=";
    public static final String URL_obtenerAlumnosPrimerApellido = "http://appestudiante.esy.es/modelo/obtenerAlumnosPorPrimerApellido.php?ape1=";
    public static final String URL_obtenerAlumnoSegundoApellido = "http://appestudiante.esy.es/modelo/obtenerAlumnosPorSegundoApellido.php?ape2=";

    //PROFESOR
    public static final String URL_loginProfesor = "http://appestudiante.esy.es/modelo/loginProfesor.php";
    public static final String URL_anadirProfesor = "http://appestudiante.esy.es/modelo/anadirProfesor.php";
    public static final String URL_obtenerProfesores = "http://appestudiante.esy.es/modelo/obtenerProfesores.php";
    public static final String URL_actualizarProfesor = "http://appestudiante.esy.es/modelo/actualizarProfesor.php";
    public static final String URL_eliminarProfesor = "http://appestudiante.esy.es/modelo/eliminarProfesor.php";
    public static final String URL_obtenerProfesoresPorDni = "http://appestudiante.esy.es/modelo/obtenerProfesoresPorDni.php?dni=";
    public static final String URL_obtenerProfesoresPorNombre = "http://appestudiante.esy.es/modelo/obtenerProfesoresPorNombre.php?nombre=";
    public static final String URL_obtenerProfesoresPrimerApellido = "http://appestudiante.esy.es/modelo/obtenerProfesoresPrimerApellido.php?ape1=";
    public static final String URL_obtenerProfesoresSegundoApellido = "http://appestudiante.esy.es/modelo/obtenerProfesoresSegundoApellido.php?ape2=";

    //ADMINISTRATIVO
    public static final String URL_asignarMateria = "http://appestudiante.esy.es/modelo/asignarMateria.php";
}
