package com.example.burger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    FragmentTransaction transaction;
    private EditText editTextUsuario;
    private EditText editTextContraseña;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Obtén las referencias a los EditText.
        editTextUsuario = view.findViewById(R.id.editTextText);
        editTextContraseña = view.findViewById(R.id.editTextTextPassword);

        // Configura el OnClickListener para el botón de inicio de sesión.
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
                String username = editTextUsuario.getText().toString();
                String password = editTextContraseña.getText().toString();

                // Verifica si los campos de usuario y contraseña no están vacíos
                // Verifica si los campos de usuario y contraseña no están vacíos
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Realiza la autenticación aquí según tus necesidades
                    boolean isAuthenticated = authenticateUser(username, password);

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

    // Método para autenticar al usuario (simulado)
    private boolean authenticateUser(String username, String password) {
        // Aquí utilizamos el DbHelper para realizar la autenticación
        DbHelper dbHelper = new DbHelper(requireContext());
        boolean isAuthenticated = dbHelper.authenticateUser(username, password);
        dbHelper.close();

        return isAuthenticated;
    }
    private boolean isUserAdmin(String username) {
        DbHelper dbHelper = new DbHelper(requireContext());

        try {
            boolean isAdmin = dbHelper.isUserAdmin(username);
            return isAdmin;
        } finally {
            dbHelper.close();
        }
    }

    // Método para navegar al fragmento de inicio
    private void navigateToInicioFragment() {
        // Aquí asumimos que tienes un contenedor llamado fragmentContainerView en tu actividad
        transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new inicioFragment()); // Reemplaza con el nombre correcto del fragmento de inicio
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}