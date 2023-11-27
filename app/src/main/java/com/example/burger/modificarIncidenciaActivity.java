package com.example.burger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class modificarIncidenciaActivity extends AppCompatActivity {

    EditText modificarIdEditText, numPedidoEditText, fechaEditText, horaEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_incidencia);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarIDIncidencia);
        numPedidoEditText = findViewById(R.id.NumPedido);
        fechaEditText = findViewById(R.id.Fecha);
        horaEditText = findViewById(R.id.Hora);

        Button modificarButton = findViewById(R.id.Modificar);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modificarId = modificarIdEditText.getText().toString();
                String numPedido = numPedidoEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                String hora = horaEditText.getText().toString();

                if (modificarId.isEmpty() || numPedido.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                    Toast.makeText(modificarIncidenciaActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("numPedido", Integer.parseInt(numPedido));
                    values.put("fecha", fecha);
                    values.put("hora", hora);

                    String selection = "idIncidencia = ?";
                    String[] selectionArgs = { modificarId };

                    int rowsUpdated = db.update("Incidencias", values, selection, selectionArgs);

                    if (rowsUpdated > 0) {
                        Toast.makeText(modificarIncidenciaActivity.this, "Incidencia modificada correctamente", Toast.LENGTH_SHORT).show();
                        modificarIdEditText.setText("");
                        numPedidoEditText.setText("");
                        fechaEditText.setText("");
                        horaEditText.setText("");
                    } else {
                        Toast.makeText(modificarIncidenciaActivity.this, "Error al modificar la incidencia", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                }
            }
        });
    }
}
