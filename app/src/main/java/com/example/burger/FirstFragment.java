package com.example.burger;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
        int ofertas[] = {R.drawable.oferta1, R.drawable.oferta2, R.drawable.oferta3, R.drawable.oferta4};
        for (int oferta : ofertas) {
            sliderimagenes(oferta);
        }

        // Configuración del ViewFlipper
        slider.setFlipInterval(2000);
        slider.setAutoStart(true);
        // Usar animaciones personalizadas para evitar el parpadeo
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // opcional
        fadeIn.setDuration(1000); // ajusta la duración según sea necesario

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // opcional
        fadeOut.setStartOffset(1000); // opcional, añade un retraso entre la animación de salida y entrada
        fadeOut.setDuration(1000); // ajusta la duración según sea necesario

        slider.setInAnimation(fadeIn);
        slider.setOutAnimation(fadeOut);

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
        TextView nombreTextView = productCard.findViewById(R.id.nombreProducto);
        TextView precioTextView = productCard.findViewById(R.id.precioProducto);
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
        // Configura el clic en el carrito para agregar al carrito
        ImageView carritoImageView = productCard.findViewById(R.id.carritoProducto);
        carritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el id del producto y otros detalles necesarios
                int idProducto = obtenerIdProducto(nombre);
                int idUsuario = obtenerIdUsuarioActual();
                String nombreProducto = nombre;
                int cantidadProducto = obtenerCantidadProducto(productCard);
                double precioTotal = cantidadProducto * precio;

                // Agrega el producto al carrito utilizando el DBHelper
                dbHelper.agregarProductoAlCarro(idUsuario, idProducto, nombreProducto, cantidadProducto, precioTotal, rutaImagen);

                // Actualiza el total de precio en ThirdFragment
                ThirdFragment fragment = (ThirdFragment) getParentFragmentManager().findFragmentById(R.id.thirdFragment);
                if (fragment != null) {
                    fragment.actualizarTotalPrecio();
                }

                Toast.makeText(requireContext(), "Producto añadido al carrito", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private int obtenerCantidadProducto(View productCard) {
        EditText cantidadEditText = productCard.findViewById(R.id.cantidadProducto);

        try {
            // Intenta obtener la cantidad desde el EditText
            return Integer.parseInt(cantidadEditText.getText().toString());
        } catch (NumberFormatException e) {
            // En caso de error al parsear, devuelve 0 o maneja el error según tus necesidades
            return 0;
        }
    }
    private int obtenerIdUsuarioActual() {
        // Utiliza SharedPreferences para obtener el ID del usuario actual
        SharedPreferences preferences = requireContext().getSharedPreferences(LoginFragment.PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(LoginFragment.KEY_USERID, -1);  // Retorna -1 si no se encuentra el ID del usuario
    }


    private int obtenerIdProducto(String nombreProducto) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"idProducto"};
        String selection = "nombre=?";
        String[] selectionArgs = {nombreProducto};

        Cursor cursor = db.query("productos", columns, selection, selectionArgs, null, null, null);

        int idProducto = -1;  // Valor predeterminado si no se encuentra el producto

        if (cursor.moveToFirst()) {
            idProducto = cursor.getInt(cursor.getColumnIndex("idProducto"));
        }

        cursor.close();
        db.close();

        return idProducto;
    }
}