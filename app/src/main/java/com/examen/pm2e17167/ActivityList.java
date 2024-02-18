package com.examen.pm2e17167;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Modelos.Personas;

public class ActivityList extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listpersonas;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglo;


    Button eliminar, actualizar, agregar, compartir, verimagen;
    EditText nombre;
    String id, idPersona, idTelefono;


    static final String ACTION_CALL="100";

    ArrayList<String> list;
    ArrayAdapter<String> ad;

    SearchView busqueda;
    Integer idposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        listpersonas = (ListView) findViewById(R.id.listpersonas);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        actualizar = (Button) findViewById(R.id.btnActualizar);
        agregar = (Button) findViewById(R.id.btnAtras);
        busqueda = (SearchView) findViewById(R.id.busqueda);
        compartir = (Button) findViewById(R.id.btnCompartir);
        verimagen = (Button) findViewById(R.id.btnImagen);


        obtenerDatos();

       /// ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        //listpersonas.setAdapter(adp);
        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elementoSeleccionado = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Seleccionates " + elementoSeleccionado, Toast.LENGTH_LONG).show();
            }
        });

        //Para realizar busquedas

        busqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                busquedaLista(newText);
                return true;
            }
        });

        verimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraverimagen(idposition);
            }
        });
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paracompartir(idposition);
            }
        });
        //
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoEliminar(idPersona);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

        // manejo del coble click en Listview

        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private long lastClickTime = 0;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastClickTime < 500) { // Considerar como doble clic si ocurre dentro de 500 ms

                    onItemDoubleClick(position);

                } else {

                    onItemSingleClick(position);
                }
                int idposition=position;
                lastClickTime = currentTime;
            }
        });

   }

    private void paraverimagen(Integer idposition) {
        Personas personaSeleccionada = lista.get(idposition);
        String imagenp= Arrays.toString(personaSeleccionada.getImagen());
        //para compartir

    }

    private void paracompartir(Integer idposition) {
        Personas personaSeleccionada = lista.get(idposition);
        String nombrep=personaSeleccionada.getNombres().toString();
        String telefonop=personaSeleccionada.getTelefono().toString();

        //para compartir
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, nombrep + "\n" + telefonop +"\n");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void actualizarDatos() {
        // Obtener la persona seleccionada
        Personas personaSeleccionada = lista.get(idposition);

        // Aquí puedes definir la información que deseas pasar
        String informacionExtra = "Información adicional: " + personaSeleccionada.getNombres();

        // Crear un Intent para abrir una nueva actividad
        Intent intent = new Intent(ActivityList.this, MainActivity.class);

        // Pasar la información a través del Intent
        intent.putExtra("id", personaSeleccionada.getId().toString());
        intent.putExtra("pais", personaSeleccionada.getPais());
        intent.putExtra("nombres", personaSeleccionada.getNombres());
        intent.putExtra("telefono", personaSeleccionada.getTelefono());
        intent.putExtra("nota", personaSeleccionada.getNota());
        //intent.putExtra("imagen", personaSeleccionada.setImagen());

        // Iniciar la nueva actividad
        startActivity(intent);
        finish();

        //          actualizarPersona(id, nombres.getText().toString(), apellidos.getText().toString()
        //                 , edad.getText().toString(), correo.getText().toString(), direccion.getText().toString());
    }


    private void busquedaLista(String t) {
        list.clear();
        if(t.isEmpty()){
            list.addAll(Arreglo);
        }else {
            t = t.toLowerCase();
            for (String i : Arreglo){
                if(i.toLowerCase().contains(t)){
                    list.add(i);
                }
            }
        }
        ad.notifyDataSetChanged();
    }

    private void onItemSingleClick(int position) {
        Personas personaSeleccionada = lista.get(position);
        idPersona=personaSeleccionada.getId().toString();
        idposition = position;
    }

    private void onItemDoubleClick(int position) {
        Personas personaSeleccionada = lista.get(position);
        idTelefono=personaSeleccionada.getTelefono();
        mostrarDialogollamadas(idTelefono);
    }
    private void hacerllamada(String idTelefono) {
        String phoneNumber = "tel:" + idTelefono;
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));

        // Asegúrate de tener el permiso CALL_PHONE antes de realizar la llamada
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(dialIntent);
        } else {
            // Si no tienes el permiso, solicítalo al usuario
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
    }


    private void mostrarDialogollamadas(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Llamadas");
        builder.setMessage("¿Desea llamar ?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hacerllamada(id);
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

    }

    private void obtenerDatos() {
        SQLiteDatabase db =  conexion.getReadableDatabase();
        Personas person = null;
        lista = new ArrayList<>();
        Arreglo = new ArrayList<>();
        Cursor cursor = db.rawQuery(Transacciones.SelectAllPersonas,null);


        while (cursor.moveToNext()){
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setPais(cursor.getString(1));
            person.setNombres(cursor.getString(2));
            person.setTelefono(cursor.getString(3));
            person.setNota(cursor.getString(4));
            person.setImagen(cursor.getBlob(5));
            lista.add(person);
            LlenarData();
        }
        cursor.close();

        list = new ArrayList<>(Arreglo);

        ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listpersonas.setAdapter(ad);
    }

    private void LlenarData() {
        Arreglo = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Arreglo.add(lista.get(i).getId() + "\n" +
                    lista.get(i).getPais() + "\n"+
                    lista.get(i).getNombres() + "\n");        }
    }
}