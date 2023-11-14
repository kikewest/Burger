package com.example.burger;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu_administrador extends AppCompatActivity {

    ProductoFragment producto = new ProductoFragment();
    UsuarioFragment usuario = new UsuarioFragment();
    RepartidorFragment repartidor = new RepartidorFragment();
    IncidenciasFragment incidencias = new IncidenciasFragment();
    ConexionFragment conexion = new ConexionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        BottomNavigationView navigation = findViewById(R.id.menu_administrador);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(producto);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.Producto) {
                loadFragment(producto);
                return true;
            } else if (item.getItemId() == R.id.Usuario) {
                loadFragment(usuario);
                return true;
            } else if (item.getItemId() == R.id.Repartidor) {
                loadFragment(repartidor);
                return true;
            } else if (item.getItemId() == R.id.Incidencias) {
                loadFragment(incidencias);
                return true;
            } else if (item.getItemId() == R.id.Conexion) {
                loadFragment(conexion);
                return true;
            }

            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
