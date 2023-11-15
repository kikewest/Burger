package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class anadirUsuarioActivity extends AppCompatActivity {

    private EditText nombreEditText, telefonoEditText, correoEditText, domicilioEditText, contraseniaEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirusuario);
        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.NombreUsuario);
        telefonoEditText = findViewById(R.id.TelefonoUsuario);
        correoEditText = findViewById(R.id.CorreoUsuario);
        domicilioEditText = findViewById(R.id.DomicilioUsuario);
        contraseniaEditText = findViewById(R.id.ContraseniaUsuario);

        Button enviarButton = findViewById(R.id.enviar);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String domicilio = domicilioEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || domicilio.isEmpty() || contrasenia.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    long newRowId = insertUsuario(nombre, telefono, correo, domicilio, contrasenia);
                    if (newRowId != -1) {
                        showToast("Usuario agregado correctamente");
                        clearFields();
                    } else {
                        showToast("Error al agregar el usuario");
                    }
                }
            }
        });
    }

    private long insertUsuario(String nombre, String telefono, String correo, String domicilio, String contrasenia) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("correo", correo);
        values.put("domicilio", domicilio);
        values.put("contrasenia", contrasenia);
        values.put("administrador", false); // assuming regular users are not administrators
        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("Clientes", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return newRowId;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        nombreEditText.setText("");
        telefonoEditText.setText("");
        correoEditText.setText("");
        domicilioEditText.setText("");
        contraseniaEditText.setText("");
    }
}


