package com.example.cristian.appestudiante.vista;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.appestudiante.R;
import com.example.cristian.appestudiante.fragment.TabAsignaturaOpciones;

public class AsignaturaOpciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_asignatura_opciones);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabAsignaturaOpciones tao = new TabAsignaturaOpciones(getSupportFragmentManager());

        viewPager.setAdapter(tao);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setLabelFor(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
}
