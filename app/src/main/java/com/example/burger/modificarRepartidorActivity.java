package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class modificarRepartidorActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, telefonoEditText, dniEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarrepartidor);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarIDRepartidor);
        nombreEditText = findViewById(R.id.ModificarNombreRepartidor);
        telefonoEditText = findViewById(R.id.ModificarTelefonoRepartidor);
        dniEditText = findViewById(R.id.ModificarDniRepartidor);

        Button modificarButton = findViewById(R.id.modificarBotonRepartidor);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String dni = dniEditText.getText().toString();

                // Validar que los campos no estén vacíos
                if (modificarId.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || dni.isEmpty()) {
                    Toast.makeText(modificarRepartidorActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Crear un objeto ContentValues para actualizar datos
                    ContentValues values = new ContentValues();
                    values.put("nombre", nombre);
                    values.put("telefono", telefono);
                    values.put("dni", dni);

                    // Definir la cláusula WHERE para la actualización
                    String selection = "idRepartidor = ?";
                    String[] selectionArgs = { modificarId };

                    // Realizar la actualización de los datos
                    int rowsUpdated = db.update("Repartidor", values, selection, selectionArgs);

                    // Verificar si la actualización fue exitosa
                    if (rowsUpdated > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(modificarRepartidorActivity.this, "Repartidor modificado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia los campos después de modificar el repartidor
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        telefonoEditText.setText("");
                        dniEditText.setText("");
                    } else {
                        Toast.makeText(modificarRepartidorActivity.this, "Error al modificar el repartidor", Toast.LENGTH_SHORT).show();
                    }

                    // Cierra la base de datos
                    db.close();
                }
            }
        });
    }
}
