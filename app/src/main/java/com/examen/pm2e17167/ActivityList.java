package com.examen.pm2e17167;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.Contactos;
import Configuracion.SQLiteConexion;
import Modelos.Contacto;

public class ActivityList extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listcontactos;
    ArrayList<Contacto> lista;
    ArrayList<String> Arreglo;
    EditText nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SQLiteConexion conexion = new SQLiteConexion(this, Contactos.DBName, null, 1);

        listcontactos = (ListView) findViewById(R.id.listcontactos);

        obtenerDatos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        listcontactos.setAdapter(adp);

        listcontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elementoSeleccionado = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Seleccionastes " + elementoSeleccionado, Toast.LENGTH_LONG).show();
            }
        });


        // Manejo del clic en el ListView
        listcontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la persona seleccionada
                Contacto contactoSeleccionado = lista.get(position);

                // Crear un Intent para abrir una nueva actividad
                ///Intent intent = new Intent(ActivityList.this, MainActivity.class);

                // Pasar la información a través del Intent
                //intent.putExtra("id", personaSeleccionada.getId().toString());
                //intent.putExtra("nombres", personaSeleccionada.getNombres());
                //intent.putExtra("apellidos", personaSeleccionada.getApellidos());
                //intent.putExtra("edad", personaSeleccionada.getEdad().toString());
                //intent.putExtra("correo", personaSeleccionada.getCorreo());
                //intent.putExtra("direccion", personaSeleccionada.getDireccion());

                // Iniciar la nueva actividad
                //startActivity(intent);
                finish();
            }
        });
    }


    private void obtenerDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contacto contact = null;
        lista = new ArrayList<Contacto>();

        //Cursor de base de datos para recorrer los datos
        Cursor cursor = db.rawQuery(Contactos.SelectAllContactos, null);

        while (cursor.moveToNext()) {
            contact = new Contacto();
            contact.setId(cursor.getInt(0));
            contact.setPais(cursor.getString(1));
            contact.setNombres(cursor.getString(2));
            //contact.setTelefono(cursor.getString(3));
            contact.setNota(cursor.getString(4));

            lista.add(contact);
        }
        cursor.close();
        LlenarData();
    }

    private void LlenarData() {
        Toast.makeText(getApplicationContext(),"Seleccionates aqui", Toast.LENGTH_LONG).show();
        Arreglo = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Arreglo.add(lista.get(i).getId() + "\n" +
                    lista.get(i).getNombres() + "\n");
        }
    }
}