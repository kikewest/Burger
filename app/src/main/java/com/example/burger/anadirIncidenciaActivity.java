package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class anadirIncidenciaActivity extends AppCompatActivity {

    private EditText numPedidoEditText, fechaEditText, horaEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_incidencia);
        dbHelper = new DbHelper(this);

        numPedidoEditText = findViewById(R.id.NumPedido);
        fechaEditText = findViewById(R.id.Fecha);
        horaEditText = findViewById(R.id.Hora);

        Button enviarButton = findViewById(R.id.enviar);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numPedidoStr = numPedidoEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                String hora = horaEditText.getText().toString();

                if (numPedidoStr.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    int numPedido = Integer.parseInt(numPedidoStr);
                    long newRowId = insertIncidencia(numPedido, fecha, hora);
                    if (newRowId != -1) {
                        showToast("Incidencia agregada correctamente");
                        clearFields();
                    } else {
                        showToast("Error al agregar la incidencia");
                    }
                }
            }
        });
    }

    private long insertIncidencia(int numPedido, String fecha, String hora) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numPedido", numPedido);
        values.put("fecha", fecha);
        values.put("hora", hora);

        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("Incidencias", null, values);
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
        numPedidoEditText.setText("");
        fechaEditText.setText("");
        horaEditText.setText("");
    }
}
