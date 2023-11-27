package com.example.burger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menuActivity extends AppCompatActivity {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();



    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("mostrarFragmento")) {
            String fragmento = intent.getStringExtra("mostrarFragmento");

            // Determina qué fragmento debe mostrarse según la información del Intent
            if (fragmento.equals("firstFragment")) {
                loadFragment(firstFragment);
            } else if (fragmento.equals("secondFragment")) {
                loadFragment(secondFragment);
            } else if (fragmento.equals("thirdFragment")) {
                loadFragment(thirdFragment);
            }
        } else {
            // Si no hay información en el Intent, carga el FirstFragment por defecto
            loadFragment(firstFragment);
        }

        // Muestra el BottomNavigationView si no se ha ocultado
        if (navigation.getVisibility() == View.GONE) {
            navigation.setVisibility(View.VISIBLE);
        }
    }




    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.firstFragment) {
                loadFragment(firstFragment);
                return true;
            } else if (item.getItemId() == R.id.secondFragment) {
                loadFragment(secondFragment);
                return true;
            } else if (item.getItemId() == R.id.thirdFragment) {
                loadFragment(thirdFragment);
                return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("mostrarFragmento")) {
            String fragmento = intent.getStringExtra("mostrarFragmento");

            if (fragmento.equals("firstFragment")) {
                // Si se debe mostrar solo el FirstFragment, oculta el BottomNavigationView
                BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
                navigationView.setVisibility(View.GONE);
            }
        }
    }
}
