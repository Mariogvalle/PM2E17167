package com.examen.pm2e17167;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.Contactos;
import Configuracion.SQLiteConexion;
import Modelos.Contacto;

public class MainActivity extends AppCompatActivity {
    EditText nombre, telefono, nota;
    Button salvar, salvados;
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

        String[] paises = {"Honduras","Nicaragua"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,paises);
        comboPais.setAdapter(adapter);

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

    }

    private void salvarData() {
        SQLiteConexion conexion = new SQLiteConexion(this, Contactos.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues datos = new ContentValues();
        datos.put(Contactos.nombres, nombre.getText().toString());
        datos.put(Contactos.pais, comboPais.getSelectedItem().toString());
        datos.put(Contactos.telefono, telefono.getText().toString());
        datos.put(Contactos.nota, nota.getText().toString());

        Long resultado = db.insert(Contactos.TableContactos, Contactos.id, datos);

        Toast.makeText(getApplicationContext(), "Persona ingresada correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
    }
}