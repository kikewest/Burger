package com.example.burger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction transaction;

    Fragment fragmentLogin, fragmentInicio, fragmentRegistro;
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
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fragmentLogin);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}