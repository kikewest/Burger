package com.example.burger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class FirstFragment extends Fragment {

    ViewFlipper slider;
    LinearLayout productLayout;
    DbHelper dbHelper;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Inicializa el ViewFlipper y el LinearLayout dentro de onCreateView
        slider = view.findViewById(R.id.slider);
        productLayout = view.findViewById(R.id.productLayout);

        // Agrega las imágenes al carrusel
        int ofertas[] = {R.drawable.oferta1, R.drawable.oferta2, R.drawable.oferta3};
        for (int oferta : ofertas) {
            sliderimagenes(oferta);
        }

        // Configuración del ViewFlipper
        slider.setFlipInterval(3000);
        slider.setAutoStart(true);
        slider.setInAnimation(requireActivity(), android.R.anim.fade_out);
        slider.setOutAnimation(requireActivity(), android.R.anim.fade_in);

        // Inicializa el dbHelper
        dbHelper = new DbHelper(requireContext());

        // Carga los productos desde la base de datos
        cargarProductos();

        return view;
    }

    public void sliderimagenes(int imagenes) {
        ImageView imageView = new ImageView(requireActivity());
        imageView.setBackgroundResource(imagenes);
        slider.addView(imageView);
    }

    private void cargarProductos() {
        // Obtiene una referencia de solo lectura a la base de datos
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define las columnas que deseas recuperar en el resultado de la consulta
        String[] columns = {"nombre", "precio", "imagen"};

        // Realiza la consulta a la base de datos
        Cursor cursor = db.query("productos", columns, null, null, null, null, null);

        // Itera sobre el cursor y agrega tarjetas para cada producto
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            String rutaImagen = cursor.getString(cursor.getColumnIndex("imagen"));
            agregarProducto(nombre, precio, rutaImagen);  // Llama al método con la ruta de la imagen
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
    }

    private void agregarProducto(String nombre, double precio, String rutaImagen) {
        // Infla dinámicamente la tarjeta de producto y agrega al LinearLayout
        View productCard = LayoutInflater.from(requireContext()).inflate(R.layout.product_card, productLayout, false);

        // Configura la información del producto en la tarjeta
        TextView nombreTextView = productCard.findViewById(R.id.productName);
        TextView precioTextView = productCard.findViewById(R.id.productPrice);
        ImageView imagenImageView = productCard.findViewById(R.id.productImage);

        nombreTextView.setText(nombre);
        precioTextView.setText(String.format(Locale.getDefault(), "$%.2f", precio));
        int imagenResId = getResources().getIdentifier(rutaImagen, "drawable", requireContext().getPackageName());
        // Cargar y mostrar la imagen usando Glide
        Glide.with(requireContext())
                .load(imagenResId)  // La ruta almacenada en la base de datos
                .into(imagenImageView);

        // Agrega la tarjeta de producto al LinearLayout
        productLayout.addView(productCard);
    }
}