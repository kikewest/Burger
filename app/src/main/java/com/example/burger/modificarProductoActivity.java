package com.example.burger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class modificarProductoActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, precioEditText, categoriaEditText, descripcionEditText, stockEditText, imagenEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarproducto);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarID);
        nombreEditText = findViewById(R.id.Nombre);
        precioEditText = findViewById(R.id.Precio);
        categoriaEditText = findViewById(R.id.Categoria);
        descripcionEditText = findViewById(R.id.Descripcion);
        stockEditText = findViewById(R.id.Stock);
        imagenEditText = findViewById(R.id.Imagen);

        Button modificarButton = findViewById(R.id.Modificar);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String precio = precioEditText.getText().toString();
                String categoria = categoriaEditText.getText().toString();
                String descripcion = descripcionEditText.getText().toString();
                String stockStr = stockEditText.getText().toString();
                String imagen = imagenEditText.getText().toString();

                if (modificarId.isEmpty() || nombre.isEmpty() || precio.isEmpty() || categoria.isEmpty() || descripcion.isEmpty() || stockStr.isEmpty() || imagen.isEmpty()) {
                    Toast.makeText(modificarProductoActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    int stock = Integer.parseInt(stockStr);

                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("nombre", nombre);
                    values.put("precio", precio);
                    values.put("categoria", categoria);
                    values.put("descripcion", descripcion);
                    values.put("stock", stock);
                    values.put("imagen", imagen);

                    String selection = "idProducto = ?";
                    String[] selectionArgs = { modificarId };

                    int rowsUpdated = db.update("Productos", values, selection, selectionArgs);

                    if (rowsUpdated > 0) {
                        Toast.makeText(modificarProductoActivity.this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show();
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        precioEditText.setText("");
                        categoriaEditText.setText("");
                        descripcionEditText.setText("");
                        stockEditText.setText("");
                        imagenEditText.setText("");
                    } else {
                        Toast.makeText(modificarProductoActivity.this, "Error al modificar el producto", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                }
            }
        });
    }
}
