package com.example.burger;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

public class MostrarPedidos extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERID = "userId";

    private TextView textViewListaPedidos;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pedidos);

        dbHelper = new DbHelper(this);

        // Obtener el ID del usuario guardado en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(KEY_USERID, -1);

        // Mostrar los pedidos del usuario
        mostrarPedidos(userId);
    }

    private void mostrarPedidos(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT idPedido, fecha FROM " + DbHelper.TABLE_PEDIDOS + " WHERE idUsuario = ? ORDER BY fecha, idPedido", new String[]{String.valueOf(userId)});

        LinearLayout container = findViewById(R.id.container);

        int pedidoActual = 0;
        String fechaAnterior = "";

        if (cursor.moveToFirst()) {
            do {
                int pedidoId = cursor.getInt(cursor.getColumnIndex("idPedido"));
                String fechaPedido = cursor.getString(cursor.getColumnIndex("fecha"));

                if (!fechaPedido.equals(fechaAnterior)) {
                    pedidoActual++;
                    fechaAnterior = fechaPedido;
                    // Crear un TextView para mostrar el número de pedido y la fecha
                    TextView textViewPedido = new TextView(this);
                    textViewPedido.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textViewPedido.setText("Pedido " + pedidoActual + " - Fecha: " + fechaPedido);
                    textViewPedido.setTextSize(20);
                    textViewPedido.setTextColor(Color.parseColor("#00FFFF"));
                    container.addView(textViewPedido);
                }

                // Consultar los productos de este pedido
                Cursor productosCursor = db.rawQuery("SELECT nombreProducto, cantidad, precio, imagen FROM " + DbHelper.TABLE_PEDIDOS + " WHERE idPedido = ?", new String[]{String.valueOf(pedidoId)});
                if (productosCursor.moveToFirst()) {
                    do {
                        String nombreProducto = productosCursor.getString(productosCursor.getColumnIndex("nombreProducto"));
                        int cantidad = productosCursor.getInt(productosCursor.getColumnIndex("cantidad"));
                        double precio = productosCursor.getDouble(productosCursor.getColumnIndex("precio"));
                        String nombreArchivoImagen = productosCursor.getString(productosCursor.getColumnIndex("imagen"));

                        // Crear un CardView para cada producto
                        CardView cardView = new CardView(this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(0, 16, 0, 0);
                        cardView.setLayoutParams(layoutParams);

                        // Inflar el layout personalizado para el CardView
                        View cardViewLayout = LayoutInflater.from(this).inflate(R.layout.cardpedidos, null);
                        cardView.addView(cardViewLayout);

                        // Obtener referencias a las vistas dentro del CardView
                        ImageView imageViewProducto = cardView.findViewById(R.id.imageViewProducto);
                        TextView textViewDetallesPedido = cardView.findViewById(R.id.textViewDetallesPedido);

                        // Configurar los detalles del producto en el TextView
                        String detallesProducto = nombreProducto + ": " + cantidad + " unidades, $" + precio;
                        textViewDetallesPedido.setText(detallesProducto);

                        // Construir la ruta completa de la imagen
                        String rutaImagen = "drawable/" + nombreArchivoImagen.toLowerCase();

                        // Cargar la imagen del producto desde la ruta construida
                        int imagenResId = getResources().getIdentifier(rutaImagen, "drawable", getPackageName());
                        if (imagenResId != 0) {
                            imageViewProducto.setImageResource(imagenResId);
                        } else {
                            // Manejar caso en que la imagen no está disponible
                        }

                        // Agregar el CardView al contenedor
                        container.addView(cardView);

                    } while (productosCursor.moveToNext());
                }

                productosCursor.close();

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

}