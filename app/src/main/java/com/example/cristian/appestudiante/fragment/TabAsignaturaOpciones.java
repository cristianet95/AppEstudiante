package com.example.cristian.appestudiante.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Cristian on 07/04/2017.
 */

public class TabAsignaturaOpciones  extends FragmentStatePagerAdapter {

    final int paginas = 5;
    private String tabTitulos[] = new String[]{"Temas", "Ejercicios", "Examen", "Notas", "Alumno"};

    public TabAsignaturaOpciones(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentAsignaturaOpciones fro;
        switch (position){
            case 0:
                fro = new FragmentAsignaturaOpciones();
                return fro;
            case 1:
                fro = new FragmentAsignaturaOpciones();
                return fro;
            case 2:
                fro = new FragmentAsignaturaOpciones();
                return fro;
            case 3:
                fro = new FragmentAsignaturaOpciones();
                return fro;
            case 4:
                fro = new FragmentAsignaturaOpciones();
                return fro;
            case 5:
                fro = new FragmentAsignaturaOpciones();
                return fro;
        }
        return null;
    }

    @Override
    public int getCount() {
        return paginas;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitulos[position];
    }


}
