package com.example.burger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    ViewFlipper slider;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Inicializa el ViewFlipper dentro de onCreateView
        slider = view.findViewById(R.id.slider);

        int ofertas[] = {R.drawable.oferta1, R.drawable.oferta2, R.drawable.oferta3};

        for (int oferta : ofertas) {
            sliderimagenes(oferta);
        }

        // Configuración del ViewFlipper
        slider.setFlipInterval(3000);
        slider.setAutoStart(true);
        slider.setInAnimation(requireActivity(), android.R.anim.slide_out_right);
        //slider.setOutAnimation(requireActivity(), android.R.anim.slide_in_left);

        return view;
    }

    public void sliderimagenes(int imagenes) {
        ImageView imageView = new ImageView(requireActivity());
        imageView.setBackgroundResource(imagenes);
        slider.addView(imageView);
    }
}