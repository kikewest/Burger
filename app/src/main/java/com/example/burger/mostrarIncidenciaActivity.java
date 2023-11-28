package com.example.burger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class mostrarIncidenciaActivity extends AppCompatActivity {

    TextView resultadosTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_incidencia);
        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.resultadosTextView);
        mostrarIncidencias();
    }

    private void mostrarIncidencias() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String[] projection = {"idIncidencia", "numPedido", "fecha", "hora"};

            Cursor cursor = db.query("Incidencias", projection, null, null, null, null, null);

            StringBuilder result = new StringBuilder();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIncidencia = cursor.getInt(cursor.getColumnIndexOrThrow("idIncidencia"));
                    int numPedido = cursor.getInt(cursor.getColumnIndexOrThrow("numPedido"));
                    String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                    String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));

                    result.append("ID de Incidencia: ").append(idIncidencia).append("\n");
                    result.append("Número de Pedido: ").append(numPedido).append("\n");
                    result.append("Fecha: ").append(fecha).append("\n");
                    result.append("Hora: ").append(hora).append("\n\n");
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                result.append("No se encontraron registros de incidencias.");
            }

            resultadosTextView.setText(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mostrarIncidencias", "Error: " + e.getMessage());
        }
    }
}
