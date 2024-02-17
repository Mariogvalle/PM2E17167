package com.examen.pm2e17167;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Modelos.Personas;

public class MainActivity extends AppCompatActivity {
    EditText nombre, telefono, nota;
    Button salvar, salvados;
    ImageButton cargafotos;
    Spinner comboPais;

    String id;
    ImageView imagenView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.txtNombre);
        comboPais = (Spinner) findViewById(R.id.spinner);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNota);
        imagenView3 = (ImageView) findViewById(R.id.imageView3);
        salvar = (Button) findViewById(R.id.btnSalvar);
        salvados = (Button) findViewById(R.id.btnSalvados);
        cargafotos = (ImageButton) findViewById(R.id.botonFotos);



        String[] paises = {"Honduras","Nicaragua","Costa Rica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,paises);
        comboPais.setAdapter(adapter);

        // Opciones de carga de fotografia
        salvar.setVisibility(View.VISIBLE);


//        String id = getIntent().getStringExtra("id");
//        String nombre = getIntent().getStringExtra("nombres");
//        String apellido = getIntent().getStringExtra("apellidos");
//        String edad2 = getIntent().getStringExtra("edad");
//        String correo2 = getIntent().getStringExtra("correo");
//        String direccion2 = getIntent().getStringExtra("direccion");


//        if (nombre != null) {
//            // Aquí puedes hacer lo que necesites con la información extra recibida
//            // Por ejemplo, puedes mostrarla en un TextView
//            nombres.setText(nombre);
//            apellidos.setText(apellido);
//            edad.setText(edad2);
//            correo.setText(correo2);
//            direccion.setText(direccion2);
//            salvar.setVisibility(View.INVISIBLE);
//            eliminar.setVisibility(View.VISIBLE);
//            actualizar.setVisibility(View.VISIBLE);
//        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre != null) {
                    salvarData();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Hacen falta datos requeridos para guardar " ,
                            Toast.LENGTH_LONG).show();
                }
                }

        });

        salvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityList.class);
                startActivity(intent);
            }
        });

        cargafotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoFotos();
            }
        });

    }

    private void DialogoFotos() {
        final CharSequence[] opciones = {"Tomar foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        final int COD_SELECCIONA =10;
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")){
                    //metodo para tomar foto
                }
                else {
                    if (opciones[i].equals("Elegir de galeria")){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        int COD_SELECCIONA = 0;
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }
                    else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void salvarData() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues datos = new ContentValues();
        datos.put(Transacciones.nombres, nombre.getText().toString());
        datos.put(Transacciones.pais, comboPais.getSelectedItem().toString());
        datos.put(Transacciones.telefono, telefono.getText().toString());
        datos.put(Transacciones.nota, nota.getText().toString());

        Long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, datos);

        Toast.makeText(getApplicationContext(), "Persona ingresada correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
    }
}