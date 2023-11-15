package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class modificarProductoActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, precioEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarproducto);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarID);
        nombreEditText = findViewById(R.id.Nombre);
        precioEditText = findViewById(R.id.Precio);

        Button modificarButton = findViewById(R.id.Modificar);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String precio = precioEditText.getText().toString();

                // Validar que los campos no estén vacíos
                if (modificarId.isEmpty() || nombre.isEmpty() || precio.isEmpty()) {
                    Toast.makeText(modificarProductoActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Crear un objeto ContentValues para actualizar datos
                    ContentValues values = new ContentValues();
                    values.put("nombre", nombre);
                    values.put("precio", precio);



                    // Definir la cláusula WHERE para la actualización
                    String selection = "idProducto = ?";
                    String[] selectionArgs = { modificarId };

                    // Realizar la actualización de los datos
                    int rowsUpdated = db.update("Productos", values, selection, selectionArgs);

                    // Verificar si la actualización fue exitosa
                    if (rowsUpdated > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(modificarProductoActivity.this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia los campos después de modificar el producto
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        precioEditText.setText("");
                    } else {
                        Toast.makeText(modificarProductoActivity.this, "Error al modificar el producto", Toast.LENGTH_SHORT).show();
                    }

                    // Cierra la base de datos
                    db.close();
                }
            }
        });


    }
}


