package com.examen.pm2e17167;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Modelos.Personas;

public class ActivityList extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listpersonas;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglo;

    Button eliminar, actualizar;
    EditText nombre;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        listpersonas = (ListView) findViewById(R.id.listpersonas);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        actualizar = (Button) findViewById(R.id.btnActualizar);

        obtenerDatos();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        listpersonas.setAdapter(adp);

        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elementoSeleccionado = (String) parent.getItemAtPosition(position);
                id =  elementoSeleccionado.toString();
                Toast.makeText(getApplicationContext(),"Seleccionates " + elementoSeleccionado, Toast.LENGTH_LONG).show();


            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoEliminar(id);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          actualizarPersona(id, nombres.getText().toString(), apellidos.getText().toString()
       //                 , edad.getText().toString(), correo.getText().toString(), direccion.getText().toString());
            }
        });

        // Manejo del clic en el ListView
        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la persona seleccionada
                Personas personaSeleccionada = lista.get(position);
                //eliminarPersona(personaSeleccionada.getId().toString());

                // Aquí puedes definir la información que deseas pasar
                //String informacionExtra = "Información adicional: " + personaSeleccionada.getNombres();

                // Crear un Intent para abrir una nueva actividad
                ///Intent intent = new Intent(ActivityList.this, MainActivity.class);

                // Pasar la información a través del Intent
                ///intent.putExtra("id", personaSeleccionada.getId().toString());
                ///intent.putExtra("nombres", personaSeleccionada.getNombres());
                ///intent.putExtra("apellidos", personaSeleccionada.getApellidos());
                ///intent.putExtra("edad", personaSeleccionada.getEdad().toString());
                ///intent.putExtra("correo", personaSeleccionada.getCorreo());
                ///intent.putExtra("direccion", personaSeleccionada.getDireccion());

                // Iniciar la nueva actividad
                //startActivity(intent);
                //finish();
            }
        });
    }

    private void mostrarDialogoEliminar(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminación");
        builder.setMessage("¿Está seguro de eliminar este registro?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPersona(id);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el diálogo.
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarPersona(String idPersona) {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        Long resultado = Long.valueOf(db.delete(Transacciones.TablePersonas, Transacciones.id + "=?", new String[]{String.valueOf(idPersona)}));
        db.close();
        Toast.makeText(getApplicationContext(), "Registro eliminado correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(ActivityList.class);
        //startActivity(intent);
        finish();
    }

    private void obtenerDatos() {
        SQLiteDatabase db =  conexion.getReadableDatabase();
        Personas person;
        lista = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery(Transacciones.SelectAllPersonas,null);


        while (cursor.moveToNext()){
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setPais(cursor.getString(2));
            person.setTelefono(cursor.getString(3));
            person.setNota(cursor.getString(4));
            person.setImagen(cursor.getString(5));
            lista.add(person);

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