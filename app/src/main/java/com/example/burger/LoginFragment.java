package com.example.burger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    FragmentTransaction transaction;
    private EditText editTextUsuario;
    private EditText editTextContraseña;
    private LoginListener loginListener;

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
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Realiza la autenticación aquí según tus necesidades
                    if (authenticateUser(username, password)) {
                        Toast.makeText(requireContext(), "Has iniciado sesion.", Toast.LENGTH_SHORT).show();
                        // Si la autenticación es exitosa, navega al fragmento de inicio
                        navigateToInicioFragment();
                    } else {
                        Toast.makeText(requireContext(), "Autenticación fallida. Verifique sus credenciales.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
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

    // Método para navegar al fragmento de inicio
    private void navigateToInicioFragment() {
        // Aquí asumimos que tienes un contenedor llamado fragmentContainerView en tu actividad
        transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new inicioFragment()); // Reemplaza con el nombre correcto del fragmento de inicio
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface LoginListener {
        void onLogin(String username, String password);
    }
}