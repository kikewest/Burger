package com.example.burger;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Locale;


public class ThirdFragment extends Fragment {
    LinearLayout cartLayout;
    DbHelper dbHelper;

    public ThirdFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // Inicializa el LinearLayout dentro de onCreateView
        cartLayout = view.findViewById(R.id.productLayout);

        // Inicializa el dbHelper
        dbHelper = new DbHelper(requireContext());

        // Carga los productos del carro desde la base de datos
        cargarProductosDelCarro();
        return view;
    }
    private int obtenerIdUsuarioActual() {
        // Utiliza SharedPreferences para obtener el ID del usuario actual
        SharedPreferences preferences = requireContext().getSharedPreferences(LoginFragment.PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(LoginFragment.KEY_USERID, -1);  // Retorna -1 si no se encuentra el ID del usuario
    }
    private void cargarProductosDelCarro() {
        int idUsuario = obtenerIdUsuarioActual();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define las columnas que deseas recuperar en el resultado de la consulta
        String[] columns = {"nombreProducto", "precio","imagen", "cantidad"};

        // Realiza la consulta a la base de datos filtrando por idUsuario
        Cursor cursor = db.query("carro", columns, "idUsuario=?", new String[]{String.valueOf(idUsuario)}, null, null, null);

        // Itera sobre el cursor y agrega tarjetas para cada producto en el carro
        while (cursor.moveToNext()) {
            // Agrega la tarjeta de producto al LinearLayout
            String nombre = cursor.getString(cursor.getColumnIndex("nombreProducto"));
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            String rutaImagen = cursor.getString(cursor.getColumnIndex("imagen"));
            agregarProductoAlCarro(nombre, precio, cantidad, rutaImagen);
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
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
    private void agregarProductoAlCarro(String nombre, double precio, int cantidad,String rutaImagen) {
        // Infla dinámicamente la tarjeta de producto y agrega al LinearLayout
        View cartProductCard = LayoutInflater.from(requireContext()).inflate(R.layout.product_card, cartLayout, false);

        // Configura la información del producto en la tarjeta
        TextView nombreTextView = cartProductCard.findViewById(R.id.nombreProducto);
        TextView precioTextView = cartProductCard.findViewById(R.id.precioProducto);
        EditText cantidadEditText = cartProductCard.findViewById(R.id.cantidadProducto);
        cantidadEditText.setEnabled(false);
        ImageView imagenImageView = cartProductCard.findViewById(R.id.productImage);

        nombreTextView.setText(nombre);
        precioTextView.setText(String.format(Locale.getDefault(), "€%.2f", precio));
        cantidadEditText.setText(String.valueOf(cantidad)); // Muestra la cantidad actual en el EditText
        int imagenResId = getResources().getIdentifier(rutaImagen, "drawable", requireContext().getPackageName());
        // Puedes cargar y mostrar la imagen usando Glide si tienes una columna para la ruta de la imagen en la tabla carro
        Glide.with(requireContext())
                 .load(imagenResId)  // La ruta almacenada en la base de datos
                 .into(imagenImageView);

        // Agrega la tarjeta de producto al LinearLayout
        cartLayout.addView(cartProductCard);
        // Configura el clic en el carrito para actualizar la cantidad en el carro
        ImageView carritoImageView = cartProductCard.findViewById(R.id.carritoProducto);
        carritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el id del producto y otros detalles necesarios
                int idProducto = obtenerIdProducto(nombre);  // Debes implementar este método
                int idUsuario = obtenerIdUsuarioActual();   // Debes implementar este método
                int nuevaCantidad = obtenerCantidadProducto(cartProductCard);  // Debes implementar este método

                // Actualiza la cantidad del producto en el carro utilizando el DBHelper
                dbHelper.actualizarCantidadProductoEnCarro(idUsuario, idProducto, nuevaCantidad);
                Toast.makeText(requireContext(), "Cantidad actualizada en el carrito", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el clic en el icono de eliminar para quitar el producto del carro
        ImageView eliminarImageView = cartProductCard.findViewById(R.id.carritoProducto);
        eliminarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el id del producto
                int idProducto = obtenerIdProducto(nombre);  // Debes implementar este método

                // Elimina el producto del carro utilizando el DBHelper
                dbHelper.eliminarProductoDelCarro(obtenerIdUsuarioActual(), idProducto);
                // Remueve la vista de la tarjeta de producto del LinearLayout
                cartLayout.removeView(cartProductCard);
                Toast.makeText(requireContext(), "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
            }
        });
    }

}