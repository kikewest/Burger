package com.example.burger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {
    FragmentTransaction transaction;
    private EditText editTextUsuario;
    private EditText editTextContraseña;

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERID = "userId";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsuario = view.findViewById(R.id.editTextText);
        editTextContraseña = view.findViewById(R.id.editTextTextPassword);

        Button btnIniciarSesion = requireActivity().findViewById(R.id.iniciar);
        Button btnCrear = requireActivity().findViewById(R.id.crear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new registroFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(requireContext());
                String username = editTextUsuario.getText().toString();
                String password = editTextContraseña.getText().toString();

                // Verifica si los campos de usuario y contraseña no están vacíos
                // Verifica si los campos de usuario y contraseña no están vacíos
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Realiza la autenticación aquí según tus necesidades
                    boolean isAuthenticated = dbHelper.authenticateUser(username, password);
                    int userId = dbHelper.obtenerIdUsuario(username, password);
                    guardarIdUsuarioEnShared(userId);
                    if (isAuthenticated) {
                        // Si la autenticación es exitosa, muestra el mensaje según si es administrador o no
                        if (isUserAdmin(username)) {
                            showToast("Bienvenido "+ username);

                            Intent intent = new Intent(requireActivity(), menu_administrador.class);
                            startActivity(intent);
                        } else {
                            showToast("Bienvenido "+username);
                            Intent intent = new Intent(requireActivity(), menuActivity.class);
                            startActivity(intent);
                        }

                        // Navega al fragmento de inicio
                        navigateToInicioFragment();
                    } else {
                        showToast("Autenticación fallida. Verifique sus credenciales.");
                    }
                } else {
                    showToast("Por favor, ingrese usuario y contraseña");
                }
            }
        });

        return view;
    }
    private void navigateToInicioFragment() {
        // Aquí asumimos que tienes un contenedor llamado fragmentContainerView en tu actividad
        transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new inicioFragment()); // Reemplaza con el nombre correcto del fragmento de inicio
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void guardarIdUsuarioEnShared(int userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(KEY_USERID, userId).apply();
    }


    private boolean isUserAdmin(String username) {
        DbHelper dbHelper = new DbHelper(requireContext());
        try {
            return dbHelper.isUserAdmin(username);
        } finally {
            dbHelper.close();
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
