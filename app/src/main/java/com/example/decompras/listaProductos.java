package com.example.decompras;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class listaProductos extends AppCompatActivity {

    ProductosSQLiteHelper baseD =
            new ProductosSQLiteHelper(this, "BDProductos", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);

        //Abrimos la base de datos ‘BDProductos’ en modo escritura

        SQLiteDatabase db = baseD.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM Productos", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                //Generamos los datos
                for (int i = 1; i <= 5; i++) {

                    String nombre = "Producto" + i;
                    //Insertamos los datos en la tabla Productos
                    db.execSQL("INSERT INTO Productos (nombre, precio) " +
                            "VALUES ('" + nombre + "', " + 100.00 + ")");
                }
            }
            cur.close();
        }


    }

    @Override
    protected void onDestroy() {
        baseD.close();
        super.onDestroy();
    }

}
