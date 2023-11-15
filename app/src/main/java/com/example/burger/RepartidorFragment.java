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
 * Use the {@link RepartidorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepartidorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RepartidorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepartidorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepartidorFragment newInstance(String param1, String param2) {
        RepartidorFragment fragment = new RepartidorFragment();
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
        View view = inflater.inflate(R.layout.fragment_repartidor, container, false);

        Button btnanadir = (Button) view.findViewById(R.id.anadirRepartidor);
        Button btnmodificar = (Button) view.findViewById(R.id.modificarRepartidor);
        Button btneliminar = (Button) view.findViewById(R.id.eliminarRepartidor);
        Button btnmostrar = (Button) view.findViewById(R.id.mostrarRepartidor);



        btnanadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), anadirRepartidorActivity.class);
                startActivity(intent);
            }
        });

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), modificarRepartidorActivity.class);
                startActivity(intent);
            }
        });
        btneliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), eliminarRepartidorActivity.class);
                startActivity(intent);
            }
        });
        btnmostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), mostrarRepartidorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
