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

    private EditText nombreEditText, precioEditText, categoriaEditText, descripcionEditText, stockEditText, imagenEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirproducto);
        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.Nombre);
        precioEditText = findViewById(R.id.Precio);
        categoriaEditText = findViewById(R.id.Categoria);
        descripcionEditText = findViewById(R.id.Descripcion);
        stockEditText = findViewById(R.id.Stock);
        imagenEditText = findViewById(R.id.Imagen);

        Button enviarButton = findViewById(R.id.enviar);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String precio = precioEditText.getText().toString();
                String categoria = categoriaEditText.getText().toString();
                String descripcion = descripcionEditText.getText().toString();
                String stockStr = stockEditText.getText().toString();
                String imagen = imagenEditText.getText().toString();

                if (nombre.isEmpty() || precio.isEmpty() || categoria.isEmpty() || descripcion.isEmpty() || stockStr.isEmpty() || imagen.isEmpty()) {
                    showToast("Por favor, completa todos los campos");
                } else {
                    int stock = Integer.parseInt(stockStr);
                    long newRowId = insertProducto(nombre, precio, categoria, descripcion, stock, imagen);
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

    public long insertProducto(String nombre, String precio, String categoria, String descripcion, int stock, String imagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("categoria", categoria);
        values.put("descripcion", descripcion);
        values.put("stock", stock);
        values.put("imagen", imagen);

        long newRowId = -1;

        try {
            newRowId = db.insertOrThrow("Productos", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        if (newRowId != -1) {
            // Llama al método insertarProducto con la ruta de la imagen
            dbHelper.insertarProducto(db, nombre, categoria, descripcion, Double.parseDouble(precio), stock, imagen);
        }
        return newRowId;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        nombreEditText.setText("");
        precioEditText.setText("");
        categoriaEditText.setText("");
        descripcionEditText.setText("");
        stockEditText.setText("");
        imagenEditText.setText("");
    }
}
