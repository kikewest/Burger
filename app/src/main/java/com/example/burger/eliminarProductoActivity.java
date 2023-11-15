package com.example.burger;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class eliminarProductoActivity extends AppCompatActivity {

    EditText idEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarproducto);

        dbHelper = new DbHelper(this);
        idEditText = findViewById(R.id.ID);

        Button eliminarButton = findViewById(R.id.enviar);


        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();

                if (id.isEmpty()) {
                    showToast("Por favor, ingresa un ID válido");
                } else {
                    if (deleteProduct(id) > 0) {
                        showToast("Producto eliminado correctamente");
                        idEditText.setText("");
                    } else {
                        showToast("Error al eliminar el producto");
                    }
                }
            }
        });


    }

    private int deleteProduct(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "idProducto = ?";  // Change column name to "idProducto"
        String[] selectionArgs = {id};
        int rowsDeleted = db.delete("Productos", selection, selectionArgs);
        db.close();
        return rowsDeleted;
    }


    private void showToast(String message) {
        Toast.makeText(eliminarProductoActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
