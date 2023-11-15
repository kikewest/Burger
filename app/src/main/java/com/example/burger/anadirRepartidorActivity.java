package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class anadirRepartidorActivity extends AppCompatActivity {

    private EditText nombreEditText, telefonoEditText, dniEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirrepartidor);
        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.NombreRepartidor);
        telefonoEditText = findViewById(R.id.TelefonoRepartidor);
        dniEditText = findViewById(R.id.DniRepartidor);

        Button enviarButton = findViewById(R.id.enviarRepartidor);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String dni = dniEditText.getText().toString();

                if (nombre.isEmpty() || telefono.isEmpty() || dni.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    long newRowId = insertRepartidor(nombre, telefono, dni);
                    if (newRowId != -1) {
                        showToast("Repartidor agregado correctamente");
                        clearFields();
                    } else {
                        showToast("Error al agregar el repartidor");
                    }
                }
            }
        });
    }

    private long insertRepartidor(String nombre, String telefono, String dni) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("dni", dni);
        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("Repartidor", null, values);
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
        dniEditText.setText("");
    }
}
