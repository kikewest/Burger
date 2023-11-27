package com.example.burger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncidenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncidenciasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncidenciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncidenciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncidenciasFragment newInstance(String param1, String param2) {
        IncidenciasFragment fragment = new IncidenciasFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incidencias, container, false);

        Button btnanadir = (Button) view.findViewById(R.id.anadirIncidencia);
        Button btnmodificar = (Button) view.findViewById(R.id.modificarIncidencia);
        Button btneliminar = (Button) view.findViewById(R.id.eliminarIncidencia);
        Button btnmostrar = (Button) view.findViewById(R.id.mostrarIncidencia);





        btnanadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), anadirIncidenciaActivity.class);
                startActivity(intent);
            }
        });

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), modificarIncidenciaActivity.class);
                startActivity(intent);
            }
        });
        btneliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), eliminarIncidenciaActivity.class);
                startActivity(intent);
            }
        });
        btnmostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), mostrarIncidenciaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}