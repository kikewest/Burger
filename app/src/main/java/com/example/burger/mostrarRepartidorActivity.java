package com.example.burger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mostrarRepartidorActivity extends AppCompatActivity {

    TextView resultadosTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarrepartidor);
        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.resultadosTextView);
        mostrarRepartidores();
    }

    private void mostrarRepartidores() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String[] projection = {"idRepartidor", "nombre", "telefono", "dni"};

            Cursor cursor = db.query("Repartidor", projection, null, null, null, null, null);

            StringBuilder result = new StringBuilder();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("idRepartidor"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
                    String dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"));

                    result.append("ID: ").append(id).append("\n");
                    result.append("Nombre: ").append(nombre).append("\n");
                    result.append("Teléfono: ").append(telefono).append("\n");
                    result.append("DNI: ").append(dni).append("\n\n");
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                result.append("No se encontraron registros.");
            }

            resultadosTextView.setText(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mostrarRepartidores", "Error: " + e.getMessage());
        }
    }
}

