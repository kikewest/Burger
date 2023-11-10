package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
    private DbHelper dbHelper;
    FragmentTransaction transaction;
    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextDireccion;
    private EditText editTextTelefono;
    private EditText editTextContrasenia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_registro, container, false);
        editTextNombre = view.findViewById(R.id.editTextTextnombre);
        editTextCorreo = view.findViewById(R.id.editTextTexcorreo);
        editTextDireccion = view.findViewById(R.id.editTextTexDireccion);
        editTextTelefono = view.findViewById(R.id.editTextTexTelefono);
        editTextContrasenia = view.findViewById(R.id.editTextTexcontrasenia);
        Button btnIniciar = requireActivity().findViewById(R.id.iniciar);
        Button btnCrear = requireActivity().findViewById(R.id.crear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString();
                String correo = editTextCorreo.getText().toString();
                String domicilio = editTextDireccion.getText().toString();
                String telefono = editTextTelefono.getText().toString();
                String contrasenia = editTextContrasenia.getText().toString();
                // Crea una instancia de DbHelper
                dbHelper = new DbHelper(getActivity());

                // Obtén una referencia a la base de datos para escritura
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Crea un objeto ContentValues para insertar los datos
                ContentValues values = new ContentValues();
                values.put("nombre", nombre);
                values.put("correo", correo);
                values.put("domicilio", domicilio);
                values.put("telefono", telefono);
                values.put("contraseña", contrasenia);
                // Inserta los valores en la tabla "usuarios"
                long newRowId = db.insert("clientes", null, values);

                // Verifica si la inserción fue exitosa
                if (newRowId != -1) {
                    Toast.makeText(getContext(), "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al insertar datos", Toast.LENGTH_SHORT).show();
                }

                // Cierra la base de datos
                db.close();
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