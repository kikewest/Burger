package com.example.burger;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class modificarUsuarioActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, telefonoEditText, correoEditText, domicilioEditText, contraseniaEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarusuario);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarIDUsuario);
        nombreEditText = findViewById(R.id.ModificarNombreUsuario);
        telefonoEditText = findViewById(R.id.ModificarTelefonoUsuario);
        correoEditText = findViewById(R.id.ModificarCorreoUsuario);
        domicilioEditText = findViewById(R.id.ModificarDomicilioUsuario);
        contraseniaEditText = findViewById(R.id.ModificarContraseniaUsuario);

        Button modificarButton = findViewById(R.id.modificarBoton);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String domicilio = domicilioEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                // Validar que los campos no estén vacíos
                if (modificarId.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || domicilio.isEmpty() || contrasenia.isEmpty()) {
                    Toast.makeText(modificarUsuarioActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Crear un objeto ContentValues para actualizar datos
                    ContentValues values = new ContentValues();
                    values.put("nombre", nombre);
                    values.put("telefono", telefono);
                    values.put("correo", correo);
                    values.put("domicilio", domicilio);
                    values.put("contrasenia", contrasenia);

                    // Definir la cláusula WHERE para la actualización
                    String selection = "idUsuario = ?";
                    String[] selectionArgs = { modificarId };

                    // Realizar la actualización de los datos
                    int rowsUpdated = db.update("Clientes", values, selection, selectionArgs);

                    // Verificar si la actualización fue exitosa
                    if (rowsUpdated > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(modificarUsuarioActivity.this, "Cliente modificado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia los campos después de modificar el cliente
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        telefonoEditText.setText("");
                        correoEditText.setText("");
                        domicilioEditText.setText("");
                        contraseniaEditText.setText("");
                    } else {
                        Toast.makeText(modificarUsuarioActivity.this, "Error al modificar el cliente", Toast.LENGTH_SHORT).show();
                    }

                    // Cierra la base de datos
                    db.close();
                }
            }
        });
    }
}

