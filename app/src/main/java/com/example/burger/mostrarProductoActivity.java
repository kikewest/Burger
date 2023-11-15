package com.example.burger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mostrarProductoActivity extends AppCompatActivity {

    TextView resultadosTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarproducto);
        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.resultadosTextView);
        mostrarProductos();


    }

    private void mostrarProductos() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String[] projection = {"idProducto", "nombre", "precio"};

            Cursor cursor = db.query("Productos", projection, null, null, null, null, null);

            StringBuilder result = new StringBuilder();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("idProducto"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String precio = cursor.getString(cursor.getColumnIndexOrThrow("precio"));

                    result.append("ID: ").append(id).append("\n");
                    result.append("Nombre: ").append(nombre).append("\n");
                    result.append("Precio: ").append(precio).append("\n\n");
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                result.append("No se encontraron registros.");
            }

            resultadosTextView.setText(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mostrarProductos", "Error: " + e.getMessage());
        }
    }

}
