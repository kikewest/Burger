package com.example.burger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.core.content.ContextCompat;
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
    private void agregarSeparador(String categoria) {
        // Crea un TextView para mostrar el nombre de la categoría
        TextView separadorTextView = new TextView(requireContext());
        separadorTextView.setText(categoria.toUpperCase()); // Convierte a mayúsculas
        separadorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        separadorTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.aqua));
        separadorTextView.setPadding(16, 8, 16, 8);
        separadorTextView.setGravity(Gravity.CENTER); // Centra el texto

        // Establece el fondo transparente tirando a negro
        separadorTextView.setBackgroundColor(Color.parseColor("#80000000"));

        // Configura el margen superior
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 15, 0, 0); // Aquí puedes ajustar el valor superior

        // Aplica los parámetros de diseño al TextView
        separadorTextView.setLayoutParams(layoutParams);

        // Agrega el TextView como separador
        productLayout.addView(separadorTextView);
    }
    private void cargarProductos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define las columnas que deseas recuperar en el resultado de la consulta
        String[] columns = {"nombre", "precio", "imagen", "categoria"};

        // Realiza la consulta a la base de datos, ordenando por categoría y un criterio específico
        Cursor cursor = db.query("productos", columns, null, null, null, null, "categoria, CASE WHEN categoria = 'Entrantes' THEN 1 WHEN categoria = 'Hamburguesas' THEN 2 WHEN categoria = 'Bebidas' THEN 3 WHEN categoria = 'Postres' THEN 4 ELSE 5 END, nombre");

        // Variables para controlar la categoría actual
        String categoriaActual = "";

        // Itera sobre el cursor y agrega tarjetas para cada producto
        while (cursor.moveToNext()) {
            String categoria = cursor.getString(cursor.getColumnIndex("categoria"));
            if (!categoria.equals(categoriaActual)) {
                // Si la categoría cambia, agrega un separador
                agregarSeparador(categoria);
                categoriaActual = categoria;
            }

            // Agrega la tarjeta de producto al LinearLayout
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            String rutaImagen = cursor.getString(cursor.getColumnIndex("imagen"));
            agregarProducto(nombre, precio, rutaImagen);
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
        precioTextView.setText(String.format(Locale.getDefault(), "€%.2f", precio));
        int imagenResId = getResources().getIdentifier(rutaImagen, "drawable", requireContext().getPackageName());
        // Cargar y mostrar la imagen usando Glide
        Glide.with(requireContext())
                .load(imagenResId)  // La ruta almacenada en la base de datos
                .into(imagenImageView);

        // Agrega la tarjeta de producto al LinearLayout
        productLayout.addView(productCard);
    }
}