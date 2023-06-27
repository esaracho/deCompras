package com.example.decompras;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class carroCompras extends AppCompatActivity {

    //private TextView mProductoTextView;

    private Button boton_producto;
    private Button boton_borrar;

    private float precio_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carro_compras);

        TableLayout tablaProducto = findViewById(R.id.tabla_carro);
        TableRow totalRow = findViewById(R.id.total);
        boton_producto = findViewById(R.id.button_producto);
        boton_borrar = findViewById(R.id.button_borrar);

        Intent intent = new Intent(this, listaProductos.class);

        //El botón lanza la activity listaProducto
        boton_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respuesta.launch(intent);
            }
        });

        boton_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                precio_total = 0;
                tablaProducto.removeViews(0,tablaProducto.getChildCount()-1);
                totalRow.setVisibility(View.INVISIBLE);

            }
        });


    }

    // Se genera el objeto para manejar la respuesta del activity listaProducto
    ActivityResultLauncher<Intent> respuesta = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        assert result.getData() != null;
                        //Se guarda el resultado del intent
                        Bundle extras = result.getData().getExtras();
                        String[] producto = extras.getStringArray(listaProductos.EXTRA_PRODUCTO);
                        String nombre = producto[0];
                        String precio = producto[1];

                        precio_total += Float.parseFloat(precio);

                        //Se genera fila de la tabla
                        TableLayout tablaProducto = findViewById(R.id.tabla_carro);
                        TableRow totalRow = findViewById(R.id.total);
                        TableRow tablarow = new TableRow(carroCompras.this);


                        TextView celda1 = new TextView(carroCompras.this);
                        TextView celda2 = new TextView(carroCompras.this);

                        celda1.setText(nombre);
                        celda2.setText("$" + precio);
                        celda1.setTextSize(24);
                        celda2.setTextSize(24);
                        celda1.setPadding(0,15,30,15);
                        celda2.setPadding(30,15,0,15);
                        celda1.setGravity(Gravity.START);
                        celda2.setGravity(Gravity.END);


                        tablarow.addView(celda1);
                        tablarow.addView(celda2);
                        tablarow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                        totalRow.setVisibility(View.VISIBLE);
                        totalRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                        totalRow.setPadding(0,20,0,20);
                        totalRow.setBackgroundResource(R.color.azul_oscuro);

                        //Se inserta fila de suma total
                        sumTotal(totalRow);
                        //Se inserta fila de la tabla
                        tablaProducto.addView(tablarow, tablaProducto.getChildCount() - 1);


                    }
                }
            });

    private void sumTotal(TableRow totalRow) {

        TextView celda1 = new TextView(carroCompras.this);
        TextView celda2 = new TextView(carroCompras.this);

        celda1.setText("Total");
        celda2.setText("$" + precio_total);
        celda1.setTextSize(24);
        celda2.setTextSize(24);
        celda1.setPadding(0,15,30,15);
        celda2.setPadding(30,15,0,15);
        celda1.setGravity(Gravity.START);
        celda2.setGravity(Gravity.END);

        totalRow.removeAllViews();

        totalRow.addView(celda1);
        totalRow.addView(celda2);

    }


}
