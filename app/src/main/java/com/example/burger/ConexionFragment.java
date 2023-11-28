package com.example.burger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConexionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConexionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppCompatButton btn;
    private AppCompatButton crearBaseDatosBtn;


    public ConexionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConexionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConexionFragment newInstance(String param1, String param2) {
        ConexionFragment fragment = new ConexionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                            if (requireContext().deleteDatabase("burger.db")) {
                                Toast.makeText(requireContext(), "BASE DE DATOS BORRADA", Toast.LENGTH_LONG).show();
                                // Deshabilita el botón después de borrar la base de datos
                                btn.setEnabled(false);
                                btn.setText("Base de datos borrada");
                                // Muestra el botón para crear la base de datos
                                crearBaseDatosBtn.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(requireContext(), "ERROR AL BORRAR BASE DE DATOS", Toast.LENGTH_LONG).show();
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