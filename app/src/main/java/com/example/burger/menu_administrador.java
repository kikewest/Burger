package com.example.burger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        Button btnsesion = findViewById(R.id.sesion);

        BottomNavigationView navigation = findViewById(R.id.menu_administrador);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(producto);


        btnsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un cuadro de diálogo de confirmación
                AlertDialog.Builder builder = new AlertDialog.Builder(menu_administrador.this);
                builder.setTitle("Cerrar Sesión");
                builder.setMessage("¿Estás seguro de que quieres cerrar sesión?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Implementa aquí la lógica para cerrar sesión.
                        // Por ejemplo, si estás utilizando SharedPreferences para almacenar la información de sesión:
                        // Limpiar los datos de sesión (ejemplo con SharedPreferences)
                        getSharedPreferences("preferencias", MODE_PRIVATE).edit().clear().apply();

                        // Navegar a la pantalla de inicio de sesión
                        Intent intent = new Intent(menu_administrador.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario ha cancelado, no hacemos nada
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
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
