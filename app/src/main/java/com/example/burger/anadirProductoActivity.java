package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class anadirProductoActivity extends AppCompatActivity {

    private EditText nombreEditText, precioEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirproducto);
        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.Nombre);
        precioEditText = findViewById(R.id.Precio);

        Button enviarButton = findViewById(R.id.enviar);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String precio = precioEditText.getText().toString();

                if (nombre.isEmpty() || precio.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    long newRowId = insertProducto(nombre, precio);
                    if (newRowId != -1) {
                        showToast("Producto agregado correctamente");
                        clearFields();
                    } else {
                        showToast("Error al agregar el producto");
                    }
                }
            }
        });
    }

    private long insertProducto(String nombre, String precio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nombre", nombre);
        values.put("Precio", precio);
        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("Productos", null, values);
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
        precioEditText.setText("");
    }
}

