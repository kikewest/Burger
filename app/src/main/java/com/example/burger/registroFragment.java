package com.example.burger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class registroFragment extends Fragment {

    FragmentTransaction transaction;
    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextDireccion;
    private EditText editTextDni;
    private EditText editTextContraseña;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_registro, container, false);
        editTextNombre = view.findViewById(R.id.editTextTextnombre);
        editTextCorreo = view.findViewById(R.id.editTextTexcorreo);
        editTextDireccion = view.findViewById(R.id.editTextTexDireccion);
        editTextDni = view.findViewById(R.id.editTextTexdni);
        editTextContraseña = view.findViewById(R.id.editTextTexcontrasenia);
        Button btnIniciar = requireActivity().findViewById(R.id.iniciar);
        Button btnCrear = requireActivity().findViewById(R.id.crear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre= editTextNombre.getText().toString();
                String Correo= editTextCorreo.getText().toString();
                String Direccion= editTextDireccion.getText().toString();
                String Dni= editTextDni.getText().toString();
                String Contraseña= editTextContraseña.getText().toString();
                if (Nombre != null && Correo != null && Direccion!=null && Dni!=null && Contraseña!=null) {
                    String message = "Datos recogidos: Usuario - " + Nombre + ", Correo - " + Correo;
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new LoginFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}