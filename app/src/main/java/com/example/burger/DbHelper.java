package com.example.burger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="burger.db";
    private static final String TABLE_CLIENTES="clientes";

    public static final String TABLE_PEDIDOS="pedidos";

    private static final String TABLE_PRODUCTOS="productos";

    private static final String TABLE_INCIDENCIAS="incidencias";

    private static final String TABLE_REPARTIDOR="repartidor";
    private static final String TABLE_TIENE="tiene";
    private static final String TABLE_CARRO = "carro";
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CLIENTES + "(" +
                "idUsuario INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "telefono INTEGER," +
                "correo TEXT," +
                "domicilio TEXT," +
                "contrasenia TEXT,"+
                "administrador BOOLEAN)");

        db.execSQL("CREATE TABLE " + TABLE_PEDIDOS + "(" +
                "idPedido INTEGER PRIMARY KEY," +
                "fecha DATE," +
                "nombreProducto TEXT," +
                "cantidad INTEGER," +
                "precio DOUBLE," +
                "imagen TEXT," +
                "idUsuario INTEGER," +
                "idProducto INTEGER," +
                "idRepartidor INTEGER," +
                "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_CLIENTES + "(idUsuario)," +
                "FOREIGN KEY (idRepartidor) REFERENCES " + TABLE_REPARTIDOR + "(idRepartidor))");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTOS + "(" +
                "idProducto INTEGER PRIMARY KEY," +
                "precio DOUBLE," +
                "nombre TEXT," +
                "imagen TEXT," +  // Add this line for the 'imagen' column
                "categoria TEXT," +
                "descripcion TEXT," +
                "stock INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_CARRO + "(" +
                "idCarro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER," +
                "idProducto INTEGER," +
                "nombreProducto TEXT," +
                "cantidad INTEGER," +
                "precio DOUBLE," +
                "imagen TEXT," +
                "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_CLIENTES + "(idUsuario)," +
                "FOREIGN KEY (idProducto) REFERENCES " + TABLE_PRODUCTOS + "(idProducto))");


        db.execSQL("CREATE TABLE " + TABLE_INCIDENCIAS + "(" +
                "idIncidencia INTEGER PRIMARY KEY," +
                "numPedido INTEGER," +
                "fecha DATE," +
                "hora TIME," +
                "FOREIGN KEY (numPedido) REFERENCES " + TABLE_PEDIDOS + "(idPedido) ON DELETE CASCADE)");


        db.execSQL("CREATE TABLE " + TABLE_REPARTIDOR + "(" +
                "idRepartidor INTEGER PRIMARY KEY," +
                "telefono INTEGER," +
                "dni TEXT," +
                "nombre TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TIENE + "(" +
                "idPedido INTEGER," +
                "idProducto INTEGER," +
                "PRIMARY KEY (idPedido, idProducto)," + // Clave compuesta
                "FOREIGN KEY (idPedido) REFERENCES " + TABLE_PEDIDOS + "(idPedido)," +
                "FOREIGN KEY (idProducto) REFERENCES " + TABLE_PRODUCTOS + "(idProducto))");

                // Insertar un cliente por defecto como administrador al crear la base de datos
                ContentValues values = new ContentValues();
                values.put("nombre", "Admin");
                values.put("telefono", 123456789);
                values.put("correo", "admin@example.com");
                values.put("domicilio", "Dirección Admin");
                values.put("contrasenia", "admin123");
                values.put("administrador", 1); // 1 si es administrador

                ContentValues values2 = new ContentValues();
                values2.put("nombre", "Pedro");
                values2.put("telefono", 123456789);
                values2.put("correo", "pedro@gmail.com");
                values2.put("domicilio", "Calle Aceituna");
                values2.put("contrasenia", "123");
                values2.put("administrador", 0); // 0 si no es administrador


        db.insert(TABLE_CLIENTES, null, values);
        db.insert(TABLE_CLIENTES, null, values2);


        insertarPedido(db, 2, "2024-01-07 20:00", "Hamburguesa de Ternera", 1, 8.99, "ternera");
        insertarPedido(db, 2, "2024-01-07 20:00", "Helado", 1, 5.49, "fresa");
        insertarPedido(db, 2, "2024-01-07 20:00", "Nachos con queso", 1, 3.99, "nachosconqueso");

        insertarPedido(db, 2, "2024-01-14 23:20", "Hamburguesa Doble Queso", 1, 10.99, "hamburguesadoblequeso");

        insertarPedido(db, 2, "2024-01-24 14:40", "Hamburguesa Doble Bacon", 1, 10.99, "hamburguesadoblequeso");
        insertarPedido(db, 2, "2024-01-24 14:40", "Patatas Fritas", 1, 4.99, "patatasfritasperfectas");
        insertarPedido(db, 2, "2024-01-24 14:40", "Tarta de queso", 1, 6.99, "tartadequeso");






        insertarProducto(db, "Hamburguesa de Ternera", "hamburguesas", "Deliciosa hamburguesa de ternera", 8.99, 10,"ternera");
        insertarProducto(db, "Hamburguesa Doble Queso", "hamburguesas", "Hamburguesa doble con queso", 10.99, 38,"hamburguesadoblequeso");
        insertarProducto(db, "Hamburguesa Doble Bacon", "hamburguesas", "Hamburguesa doble con bacon", 11.99, 18,"hamburguesadoblebacon");

        insertarProducto(db, "Agua", "liquidos", "Botella de agua", 1.99, 20,"agua");
        insertarProducto(db, "Coca-Cola", "liquidos", "Lata de Coca-Cola", 2.49, 15,"cocacola");
        insertarProducto(db, "Fanta", "liquidos", "Lata de Fanta de naranja", 2.49, 15,"fantanaranja");

        insertarProducto(db, "Patatas Fritas", "entrantes", "Descripción del entrante 1", 4.99, 12,"patatasfritasperfectas");
        insertarProducto(db, "Nachos con queso", "entrantes", "Descripción del entrante 2", 3.99, 15,"nachosconqueso");

        insertarProducto(db, "Tarta de queso", "postres", "Descripción del postre 1", 6.99, 10,"tartadequeso");
        insertarProducto(db, "Helado", "postres", "Descripción del postre 2", 5.49, 8,"fresa");
    }
    public void insertarProducto(SQLiteDatabase db, String nombre, String categoria, String descripcion, double precio, int stock, String imagen) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("categoria", categoria);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);
        values.put("imagen", imagen);
        db.insert(TABLE_PRODUCTOS, null, values);
    }
    public void realizarPedido(int idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Agrega la fecha actual al pedido
        values.put("fecha", ThirdFragment.obtenerFechaActual());

        // Agrega el idUsuario al pedido
        values.put("idUsuario", idUsuario);

        // Obtiene los datos del carrito del usuario
        String query = "SELECT * FROM " + TABLE_CARRO + " WHERE idUsuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

        while (cursor.moveToNext()) {
            // Obtiene los datos de cada producto en el carrito
            String nombreProducto = cursor.getString(cursor.getColumnIndex("nombreProducto"));
            int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            String imagen = cursor.getString(cursor.getColumnIndex("imagen"));

            // Agrega el producto al pedido
            values.put("nombreProducto", nombreProducto);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("imagen", imagen);

            // Inserta el pedido en la tabla 'pedidos'
            db.insert(TABLE_PEDIDOS, null, values);
        }

        // Elimina los productos del carrito después de realizar el pedido
        db.delete(TABLE_CARRO, "idUsuario=?", new String[]{String.valueOf(idUsuario)});

        cursor.close();
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE clientes");
        db.execSQL("DROP TABLE pedidos");
        db.execSQL("DROP TABLE productos");
        db.execSQL("DROP TABLE incidencias");
        db.execSQL("DROP TABLE repartidor");
        db.execSQL("DROP TABLE tiene");
        onCreate(db);
    }
    public List<Double> obtenerPreciosProductosEnCarro(int idUsuario) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT precio FROM carro WHERE idUsuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

        List<Double> precios = new ArrayList<>();

        while (cursor.moveToNext()) {
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            precios.add(precio);
        }

        cursor.close();
        db.close();

        return precios;
    }
    public void agregarProductoAlCarro(int idUsuario, int idProducto, String nombreProducto, int cantidad, double precio,String imagen) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insertar el producto en el carrito
        ContentValues values = new ContentValues();
        values.put("idUsuario", idUsuario);
        values.put("idProducto", idProducto);
        values.put("nombreProducto", nombreProducto);
        values.put("cantidad", cantidad);
        values.put("precio", precio);
        values.put("imagen", imagen);
        db.insert(TABLE_CARRO, null, values);

        db.close();
    }

    public boolean authenticateUser(String username, String password) {
        // Obtiene una referencia de solo lectura a la base de datos
        SQLiteDatabase db = this.getReadableDatabase();
        // Define las columnas que deseas recuperar en el resultado de la consulta
        String[] columns = {"idUsuario"};
        // Define la cláusula WHERE de la consulta/ Define la cláusula WHERE de la consulta
        String selection = "nombre=? AND contrasenia=?";
        // Especifica los valores a ser utilizados en la cláusula WHERE
        String[] selectionArgs = {username, password};
        // Realiza la consulta a la base de datos
        Cursor cursor = db.query(TABLE_CLIENTES, columns, selection, selectionArgs, null, null, null);
        // Obtiene el número de filas devueltas por la consulta
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public void eliminarProductoDelCarro(int idUsuario, int idProducto) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "idUsuario=? AND idProducto=?";
        String[] whereArgs = {String.valueOf(idUsuario), String.valueOf(idProducto)};

        db.delete(TABLE_CARRO, whereClause, whereArgs);

        db.close();
    }
    @SuppressLint("Range")
    public int obtenerIdUsuario(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"idUsuario"};
        String selection = "nombre=? AND contrasenia=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_CLIENTES, columns, selection, selectionArgs, null, null, null);

        int userId = -1; // Valor predeterminado si la autenticación falla

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("idUsuario"));
        }

        cursor.close();
        db.close();

        return userId;
    }

    @SuppressLint("Range")
    public boolean isUserAdmin(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"administrador"};
        String selection = "nombre=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_CLIENTES, columns, selection, selectionArgs, null, null, null);

        boolean isAdmin = false;

        if (cursor.moveToFirst()) {
            isAdmin = cursor.getInt(cursor.getColumnIndex("administrador")) ==1;
        }

        cursor.close();
        db.close();

        return isAdmin;
    }


    private void insertarPedido(SQLiteDatabase db, long idUsuario, String fecha, String nombreProducto, int cantidad, double precio, String imagen) {
        ContentValues values3 = new ContentValues();
        values3.put("idUsuario", idUsuario);
        values3.put("fecha", fecha);
        values3.put("nombreProducto", nombreProducto);
        values3.put("cantidad", cantidad);
        values3.put("precio", precio);
        values3.put("imagen", imagen);
        db.insert(TABLE_PEDIDOS, null, values3);
    }


}
