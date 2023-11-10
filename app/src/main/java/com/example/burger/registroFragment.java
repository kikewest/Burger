package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registroFragment extends Fragment {
    private DbHelper dbHelper;
    private EditText editTextNombre, editTextCorreo, editTextDireccion, editTextTelefono, editTextContrasenia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        dbHelper = new DbHelper(requireContext()); // assuming DbHelper is needed in this fragment

        editTextNombre = view.findViewById(R.id.editTextTextnombre);
        editTextCorreo = view.findViewById(R.id.editTextTexcorreo);
        editTextDireccion = view.findViewById(R.id.editTextTexDireccion);
        editTextTelefono = view.findViewById(R.id.editTextTexTelefono);
        editTextContrasenia = view.findViewById(R.id.editTextTexcontrasenia);

        Button btnCrear = requireActivity().findViewById(R.id.crear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString();
                String correo = editTextCorreo.getText().toString();
                String direccion = editTextDireccion.getText().toString();
                String telefono = editTextTelefono.getText().toString();
                String contrasenia = editTextContrasenia.getText().toString();

                if (nombre.isEmpty() || correo.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || contrasenia.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    long newRowId = insertRegistro(nombre, correo, direccion, telefono, contrasenia);
                    if (newRowId != -1) {
                        showToast("Registro agregado correctamente");
                        clearFields();
                    } else {
                        showToast("Error al agregar el registro");
                    }
                }
            }
        });

        return view;
    }

    private long insertRegistro(String nombre, String correo, String direccion, String telefono, String contrasenia) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("domicilio", direccion);
        values.put("telefono", telefono);
        values.put("contrasenia", contrasenia);
        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("clientes", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return newRowId;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        editTextNombre.setText("");
        editTextCorreo.setText("");
        editTextDireccion.setText("");
        editTextTelefono.setText("");
        editTextContrasenia.setText("");
    }
}

