package com.example.burger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import java.io.File;


public class ConexionFragment extends Fragment {




    private AppCompatButton btn;
    private AppCompatButton crearBaseDatosBtn;


    public ConexionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conexion, container, false);


        btn = view.findViewById(R.id.BorrarBaseDatos);
        crearBaseDatosBtn = view.findViewById(R.id.CrearBaseDatos);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(requireContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if (db != null) {
                    // Cierra la base de datos antes de eliminarla
                    db.close();
                    dbHelper.close();

                    // Muestra un cuadro de diálogo de confirmación
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setMessage("¿Estás seguro de que deseas borrar la base de datos?");
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Borra la base de datos burger.db
                            String nombreBaseDeDatos = "burger.db";
                            if (requireActivity().deleteDatabase(nombreBaseDeDatos)) {
                                Toast.makeText(requireContext(), "Base de datos '" + nombreBaseDeDatos + "' borrada con éxito", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(requireContext(), "No se pudo borrar la base de datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.show();
                } else {
                    Toast.makeText(requireContext(), "NO HAY BASE DE DATOS PARA BORRAR", Toast.LENGTH_LONG).show();
                }
            }
        });

        crearBaseDatosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(requireContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if (db != null) {
                    // Puedes agregar lógica adicional aquí si es necesario
                    Toast.makeText(requireContext(), "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();

                    // Luego de crear la base de datos, realiza la comprobación de existencia del archivo
                    File archivo = requireContext().getDatabasePath("burger.db");
                    if (archivo.exists()) {
                        // Tu lógica después de crear la base de datos
                        btn.setEnabled(true);
                        btn.setText("Borrar base de datos");
                        // Oculta el botón para crear la base de datos
                        crearBaseDatosBtn.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(requireContext(), "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

}