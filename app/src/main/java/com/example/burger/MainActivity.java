package com.example.burger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity  {
    DbHelper dbHelper;
    FragmentTransaction transaction;

    Fragment fragmentLogin, fragmentInicio, fragmentRegistro;
    private int currentFragment = 1; // Inicialmente, estás en el fragmento 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(MainActivity.this);
        fragmentInicio = new inicioFragment();
        fragmentLogin = new LoginFragment();
        fragmentRegistro = new registroFragment();


        Button btncrear = findViewById(R.id.crear);
        Button btninicio = findViewById(R.id.iniciar);

        btncrear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fragmentRegistro);
                transaction.addToBackStack(null);
                transaction.commit();
                // Cambia la imagen de fondo al fragmento Registro
                //ConstraintLayout mainLayout = findViewById(R.id.activity_main_layout);
               // mainLayout.setBackgroundResource(R.drawable.fondotranssinicono);

                // Actualiza el estado al fragmento Registro
                currentFragment = 2;
            }
        });

        btninicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getSupportFragmentManager().findFragmentByTag(LoginFragment.class.getName()) == null) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, fragmentLogin, LoginFragment.class.getName());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                // Cambia la imagen de fondo al fragmento 2
                // Cambia la imagen de fondo del ConstraintLayout al fragmento 2
                //ConstraintLayout mainLayout = findViewById(R.id.activity_main_layout);
               // mainLayout.setBackgroundResource(R.drawable.fondotranssinicono);

                currentFragment = 2; // Actualiza el estado al fragmento 2
            }
        });
        ConstraintLayout mainLayout = findViewById(R.id.activity_main_layout);
        mainLayout.setBackgroundResource(R.drawable.fondosintrans);
        //el siguiente if cambia la barra de navegacion de android de morado a transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.transparente)); // Reemplaza R.color.colorTransparent con tu color deseado
        }
    }
}