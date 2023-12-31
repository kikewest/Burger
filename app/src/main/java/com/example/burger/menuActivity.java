package com.example.burger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class menuActivity extends AppCompatActivity {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    FourthFragment fourthFragment = new FourthFragment();




    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NavigationView lateral = findViewById(R.id.lateral);
        lateral.setVisibility(View.GONE);
        Intent intent = getIntent();
        ImageView imagen = findViewById(R.id.imageView3);
        imagen.setVisibility(View.GONE);

        if (intent != null && intent.hasExtra("mostrarFragmento")) {
            String fragmento = intent.getStringExtra("mostrarFragmento");

            // Determina qué fragmento debe mostrarse según la información del Intent
            if (fragmento.equals("firstFragment")) {
                loadFragment(firstFragment);
                lateral.setVisibility(View.GONE);
            } else if (fragmento.equals("secondFragment")) {
                loadFragment(secondFragment);
                lateral.setVisibility(View.GONE);
            } else if (fragmento.equals("thirdFragment")) {
                loadFragment(thirdFragment);
                lateral.setVisibility(View.GONE);
            }else if (fragmento.equals("fourthFragment")) {
                loadFragment(fourthFragment);
                lateral.setVisibility(View.VISIBLE);
            }
        } else {
            // Si no hay información en el Intent, carga el FirstFragment por defecto
            loadFragment(firstFragment);
        }


        // Muestra el BottomNavigationView si no se ha ocultado
        if (navigation.getVisibility() == View.GONE) {
            navigation.setVisibility(View.VISIBLE);
        }


        // Encuentra el elemento de menú por su ID
        MenuItem sesionItem = lateral.getMenu().findItem(R.id.sesion);

        // Establece un OnClickListener para el elemento de menú
        sesionItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("MenuItemClick", "Cerrar Sesión seleccionado");
                cerrarSesion();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lateral_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Este método se usa para manejar clics en el menú de la ActionBar (si lo tienes).
        // Como ya estás manejando el clic en el elemento de menú directamente, este método no es necesario aquí.
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        // Implementa aquí la lógica para cerrar sesión.
        // Por ejemplo, si estás utilizando SharedPreferences para almacenar la información de sesión:
        // Limpiar los datos de sesión (ejemplo con SharedPreferences)
        getSharedPreferences("preferencias", MODE_PRIVATE).edit().clear().apply();


        // Navegar a la pantalla de inicio de sesión
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            NavigationView lateral = findViewById(R.id.lateral);
            ImageView imagen = findViewById(R.id.imageView3);
            imagen.setVisibility(View.GONE);
            if (item.getItemId() == R.id.firstFragment) {
                loadFragment(firstFragment);
                imagen.setVisibility(View.GONE);
                lateral.setVisibility(View.GONE);
                return true;
            } else if (item.getItemId() == R.id.secondFragment) {
                loadFragment(secondFragment);
                imagen.setVisibility(View.GONE);
                lateral.setVisibility(View.GONE);
                return true;
            } else if (item.getItemId() == R.id.thirdFragment) {
                loadFragment(thirdFragment);
                imagen.setVisibility(View.GONE);
                lateral.setVisibility(View.GONE);
                return true;
            } else if (item.getItemId() == R.id.fourthFragment) {
                loadFragment(fourthFragment);
                imagen.setVisibility(View.VISIBLE);
                lateral.setVisibility(View.VISIBLE);
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
