package com.example.decompras;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class listaProductos extends ListActivity {

    ProductosSQLiteHelper baseD =
            new ProductosSQLiteHelper(this, "BDProductos", null, 5);

    public static final String EXTRA_PRODUCTO =
            "com.example.mensajeactividades.extra.PRODUCTO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);

        //Abrimos la base de datos ‘BDProductos’ en modo escritura
        SQLiteDatabase db = baseD.getWritableDatabase();

        String[] productos = {"Remera estampada", "Remera lisa", "Pantalón Jean", "Gorro lana", "Medias rayadas",
        "Polera lisa", "Zapatillas de lona", "Gorra trucker", "Bufanda cuadrillé", "Pantalón chupín"};

        float[] precios = {1050.50f, 1000f, 3400.50f, 600f, 300f, 2300.50f, 3500.75f, 750f, 450.75f, 4650f };

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM Productos", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                //Generamos los datos
                for (int i = 0; i < 10; i++) {

                    String nombre = productos[i];
                    float precio = precios[i];
                    //Insertamos los datos en la tabla Productos
                    db.execSQL("INSERT INTO Productos (nombre, precio) " +
                            "VALUES ('" + nombre + "', '" + precio + "')");
                }
            }
            cur.close();
        }

       consultar();

    }


    @SuppressWarnings("deprecation")
    private void consultar() {

        //Realizo la conexión a la base de datos
        SQLiteDatabase db = baseD.getReadableDatabase();

        //Realizo la consulta y la asigno a un objeto Cursor
        String[] campos = new String[] {"_id","nombre","precio"};
        Cursor cursor = db.query("Productos", campos, null , null, null, null, null);

        String[] columnas = new String[] { "nombre", "precio" };
        //int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
        int[] to = new int[] { R.id.producto_nombre, R.id.producto_precio };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.fila_producto,
                cursor,
                columnas,
                to);

        ListView lista = findViewById(android.R.id.list);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Cursor cursor = (Cursor) lista.getItemAtPosition(position);

                Intent replyIntent = new Intent();
                String[] producto = new String[2];
                producto[0] = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                producto[1] = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
                
                replyIntent.putExtra(EXTRA_PRODUCTO, producto);
                setResult(RESULT_OK,replyIntent);
                finish ();

            }
        });
    }

}
