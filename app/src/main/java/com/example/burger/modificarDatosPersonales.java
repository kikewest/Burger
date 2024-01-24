package com.example.burger;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class modificarDatosPersonales extends AppCompatActivity {
    EditText nombreEditText, telefonoEditText, correoEditText, domicilioEditText, contraseniaEditText;
    DbHelper dbHelper;

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificardatospersonales);

        Log.d("modificarDatosPersonales", "onCreate called");

        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.ModificarNombreUsuario);
        telefonoEditText = findViewById(R.id.ModificarTelefonoUsuario);
        correoEditText = findViewById(R.id.ModificarCorreoUsuario);
        domicilioEditText = findViewById(R.id.ModificarDomicilioUsuario);
        contraseniaEditText = findViewById(R.id.ModificarContraseniaUsuario);

        // Recuperar userId desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt(KEY_USERID, -1);

        if (idUsuario != -1) {
            Button modificarButton = findViewById(R.id.modificarBoton);
            modificarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Obtener los valores de los EditText
                    String nombre = nombreEditText.getText().toString();
                    String telefono = telefonoEditText.getText().toString();
                    String correo = correoEditText.getText().toString();
                    String domicilio = domicilioEditText.getText().toString();
                    String contrasenia = contraseniaEditText.getText().toString();

                    // Validar que los campos no estén vacíos
                    if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || domicilio.isEmpty() || contrasenia.isEmpty()) {
                        Toast.makeText(modificarDatosPersonales.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    } else {
                        // Actualizar los datos del usuario en la base de datos
                        actualizarDatosUsuario(idUsuario, nombre, telefono, correo, domicilio, contrasenia);
                    }
                }
            });
        } else {
            // Manejar el caso donde la ID del usuario es inválida
            Log.e("modificarDatosPersonales", "ID del usuario inválida en SharedPreferences");
            mostrarMensajeError("Error al cargar la actividad. Inténtelo nuevamente.");
            finish(); // Cerrar la actividad en caso de error.
        }
    }

    private void mostrarMensajeError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void actualizarDatosUsuario(int idUsuario, String nombre, String telefono, String correo, String domicilio, String contrasenia) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("telefono", telefono);
        valores.put("correo", correo);
        valores.put("domicilio", domicilio);
        valores.put("contrasenia", contrasenia);

        String seleccion = "idUsuario = ?";
        String[] argsSeleccion = {String.valueOf(idUsuario)};

        int filasActualizadas = db.update("clientes", valores, seleccion, argsSeleccion);

        if (filasActualizadas > 0) {
            Toast.makeText(modificarDatosPersonales.this, "Cliente modificado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(modificarDatosPersonales.this, "Error al modificar el cliente", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
