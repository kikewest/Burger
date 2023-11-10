package com.example.burger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

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
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsuario.getText().toString();
                String password = editTextContraseña.getText().toString();
                if (username != null && password != null) {
                    String message = "Datos recogidos: Usuario - " + username + ", Contraseña - " + password;
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
                if (loginListener != null) {
                    loginListener.onLogin(username, password);
                }
            }
        });

        return view;
    }

    public interface LoginListener {
        void onLogin(String username, String password);
    }
}