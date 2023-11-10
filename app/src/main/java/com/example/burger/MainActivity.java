package com.example.burger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    FragmentTransaction transaction;

    Fragment fragmentLogin, fragmentInicio, fragmentRegistro;
    private int currentFragment = 1; // Inicialmente, estás en el fragmento 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                ConstraintLayout mainLayout = findViewById(R.id.activity_main_layout);
                mainLayout.setBackgroundResource(R.drawable.fondotranssinicono);

                currentFragment = 2; // Actualiza el estado al fragmento 2
            }
        });
        ConstraintLayout mainLayout = findViewById(R.id.activity_main_layout);
        mainLayout.setBackgroundResource(R.drawable.fondosintrans);

    }
    @Override
    public void onLogin(String username, String password) {
        // Aquí puedes manejar los datos de usuario y contraseña que se recopilaron.
        // Puedes realizar la lógica de autenticación o cualquier acción necesaria aquí.

        // Ejemplo: muestra los datos de usuario y contraseña en un Toast.
        Toast.makeText(this, "Usuario: " + username + "\nContraseña: " + password, Toast.LENGTH_SHORT).show();

        // También puedes realizar la autenticación aquí y llevar a cabo la lógica correspondiente.
    }
}