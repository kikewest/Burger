package com.example.burger;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class eliminarRepartidorActivity extends AppCompatActivity {

    EditText idEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarrepartidor);

        dbHelper = new DbHelper(this);
        idEditText = findViewById(R.id.IDRepartidor);

        Button eliminarButton = findViewById(R.id.enviarRepartidor);

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();

                if (id.isEmpty()) {
                    showToast("Por favor, ingresa un ID válido");
                } else {
                    if (deleteRepartidor(id) > 0) {
                        showToast("Repartidor eliminado correctamente");
                        idEditText.setText("");
                    } else {
                        showToast("Error al eliminar el repartidor");
                    }
                }
            }
        });
    }

    private int deleteRepartidor(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "idRepartidor = ?";
        String[] selectionArgs = {id};
        int rowsDeleted = db.delete("Repartidor", selection, selectionArgs);
        db.close();
        return rowsDeleted;
    }

    private void showToast(String message) {
        Toast.makeText(eliminarRepartidorActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

