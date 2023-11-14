package com.example.burger;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mostrarUsuarioActivity extends AppCompatActivity {

    TextView resultadosTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarusuario);
        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.resultadosTextView);
        mostrarClientes();
    }

    private void mostrarClientes() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String[] projection = {"idUsuario", "nombre", "telefono", "correo", "domicilio", "contrasenia", "administrador"};

            Cursor cursor = db.query("Clientes", projection, null, null, null, null, null);

            StringBuilder result = new StringBuilder();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    int telefono = cursor.getInt(cursor.getColumnIndexOrThrow("telefono"));
                    String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));
                    String domicilio = cursor.getString(cursor.getColumnIndexOrThrow("domicilio"));
                    String contrasenia = cursor.getString(cursor.getColumnIndexOrThrow("contrasenia"));
                    int administrador = cursor.getInt(cursor.getColumnIndexOrThrow("administrador"));

                    result.append("ID: ").append(id).append("\n");
                    result.append("Nombre: ").append(nombre).append("\n");
                    result.append("Teléfono: ").append(telefono).append("\n");
                    result.append("Correo: ").append(correo).append("\n");
                    result.append("Domicilio: ").append(domicilio).append("\n");
                    result.append("Contraseña: ").append(contrasenia).append("\n");
                    result.append("Administrador: ").append(administrador == 1).append("\n\n");
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                result.append("No se encontraron registros.");
            }

            resultadosTextView.setText(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mostrarClientes", "Error: " + e.getMessage());
        }
    }
}

