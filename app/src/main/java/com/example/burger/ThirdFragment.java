package com.example.burger;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public void onResume() {
        super.onResume();
        actualizarTotalPrecio();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // Inicializa el LinearLayout dentro de onCreateView
        cartLayout = view.findViewById(R.id.carroLayout);

        // Inicializa el dbHelper
        dbHelper = new DbHelper(requireContext());
        AppCompatButton realizarPedidoButton = view.findViewById(R.id.realizar);
        realizarPedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el ID del usuario actual
                int idUsuario = obtenerIdUsuarioActual();

                // Verifica que haya un usuario autenticado
                if (idUsuario != -1) {
                    // Realiza el pedido
                    dbHelper.realizarPedido(idUsuario);
                    actualizarTotalPrecio();
                    cartLayout.removeAllViews();
                    // Muestra un mensaje o realiza alguna acción adicional si es necesario
                    Toast.makeText(requireContext(), "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    // El usuario no está autenticado, muestra un mensaje o realiza alguna acción adicional si es necesario
                    Toast.makeText(requireContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Carga los productos del carro desde la base de datos
        cargarProductosDelCarro();
        actualizarTotalPrecio();
        return view;
    }
    public static String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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
    public void actualizarTotalPrecio() {
        View fragmentView = getView();
        if (fragmentView != null) {
            double totalPrecio = calcularTotalPrecio();
            TextView totalPrecioTextView = fragmentView.findViewById(R.id.totalPrecioTextView);
            totalPrecioTextView.setText(String.format(Locale.getDefault(), "Total: €%.2f", totalPrecio));
        }
    }

    private double calcularTotalPrecio() {
        int idUsuario = obtenerIdUsuarioActual();

        // Obtén la lista de precios de los productos en el carrito
        List<Double> precios = dbHelper.obtenerPreciosProductosEnCarro(idUsuario);

        // Mueve la declaración de totalPrecio aquí
        double totalPrecio = 0.0;

        // Suma los precios
        for (double precio : precios) {
            totalPrecio += precio;
        }

        return totalPrecio;
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
    private void agregarProductoAlCarro(String nombre, double precio, int cantidad,String rutaImagen) {
        // Infla dinámicamente la tarjeta de producto y agrega al LinearLayout
        View cartProductCard = LayoutInflater.from(requireContext()).inflate(R.layout.carrocard, cartLayout, false);

        // Configura la información del producto en la tarjeta
        TextView nombreTextView = cartProductCard.findViewById(R.id.nombreProducto);
        TextView precioTextView = cartProductCard.findViewById(R.id.precioProducto);
        TextView cantidadEditText = cartProductCard.findViewById(R.id.cantidadProductoc);
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
        actualizarTotalPrecio();

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
                actualizarTotalPrecio();
                Toast.makeText(requireContext(), "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
            }
        });
        actualizarTotalPrecio();
    }

}