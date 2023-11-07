package com.example.burger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="burger.db";
    private static final String TABLE_CLIENTES="clientes";

    private static final String TABLE_PEDIDOS="pedidos";

    private static final String TABLE_PRODUCTOS="productos";

    private static final String TABLE_INCIDENCIAS="incidencias";

    private static final String TABLE_REPARTIDOR="repartidor";
    private static final String TABLE_TIENE="tiene";
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
                "idUsuario INTEGER," +
                "idProducto INTEGER," +
                "idRepartidor INTEGER," +
                "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_CLIENTES + "(idUsuario)," +
                "FOREIGN KEY (idRepartidor) REFERENCES " + TABLE_REPARTIDOR + "(idRepartidor))");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTOS + "(" +
                "idProducto INTEGER PRIMARY KEY," +
                "precio DOUBLE," +
                "nombre TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_INCIDENCIAS + "(" +
                "idIncidencia INTEGER PRIMARY KEY," +
                "numPedido INTEGER," +
                "fecha DATE," +
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
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE clientes");
        db.execSQL("DROP TABLE pedidos");
        db.execSQL("DROP TABLE productos");
        db.execSQL("DROP TABLE incidencias");
        db.execSQL("DROP TABLE repartidor");
        onCreate(db);
    }
}
