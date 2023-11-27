package com.example.burger;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class eliminarIncidenciaActivity extends AppCompatActivity {

    EditText idIncidenciaEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_incidencia);

        dbHelper = new DbHelper(this);
        idIncidenciaEditText = findViewById(R.id.IDIncidencia);

        Button eliminarButton = findViewById(R.id.enviar);

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idIncidencia = idIncidenciaEditText.getText().toString();

                if (idIncidencia.isEmpty()) {
                    showToast("Por favor, ingresa un ID de incidencia válido");
                } else {
                    if (deleteIncidencia(idIncidencia) > 0) {
                        showToast("Incidencia eliminada correctamente");
                        idIncidenciaEditText.setText("");
                    } else {
                        showToast("Error al eliminar la incidencia");
                    }
                }
            }
        });
    }

    private int deleteIncidencia(String idIncidencia) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "idIncidencia = ?";  // Cambiar el nombre de la columna a "idIncidencia"
        String[] selectionArgs = {idIncidencia};
        int rowsDeleted = db.delete("Incidencias", selection, selectionArgs);
        db.close();
        return rowsDeleted;
    }

    private void showToast(String message) {
        Toast.makeText(eliminarIncidenciaActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

