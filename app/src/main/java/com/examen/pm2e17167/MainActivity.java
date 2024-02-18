package com.examen.pm2e17167;

import static Configuracion.Transacciones.nombres;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;


public class MainActivity extends AppCompatActivity {
    EditText nombre, telefono, nota;
    Button salvar, salvados, actualiza;
    Button cargafotos;
    Spinner comboPais;

    String id;
    ImageView imagenView3;

    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 100;

    static final int peticion_camara = 100;
    static final int peticion_foto = 102;
    private byte[] photoByteArray;
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
        cargafotos = (Button) findViewById(R.id.btnFoto);
        actualiza = (Button) findViewById(R.id.btnActualiza);

        String[] paises = {"Honduras","Nicaragua","Costa Rica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,paises);
        comboPais.setAdapter(adapter);

        // Opciones de carga de fotografia
        salvar.setVisibility(View.VISIBLE);
        actualiza.setVisibility(View.INVISIBLE);

        String idp = getIntent().getStringExtra("id");
        String nombrep = getIntent().getStringExtra("nombres");
        String telefonop = getIntent().getStringExtra("telefono");
        String notap = getIntent().getStringExtra("nota");

        if (nombrep != null) {
            nombre.setText(nombrep);
            telefono.setText(telefonop);
            nota.setText(notap);
            salvar.setVisibility(View.INVISIBLE);
            actualiza.setVisibility(View.VISIBLE);
        }

        actualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    actualizaUpdate();
            }
        });
        cargafotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisoFoto();
            }
        });

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
    }

    private void actualizaUpdate() {
    }


    private void DialogoFotos() {
        final CharSequence[] opciones = {"Tomar foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")){
                    permisoFoto();
                }
                else {
                    if (opciones[i].equals("Elegir de galeria")){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        int COD_SELECCION = 10;
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCION);
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
        datos.put(Transacciones.pais, comboPais.getSelectedItem().toString());
        datos.put(Transacciones.nombres, nombre.getText().toString());
        datos.put(Transacciones.telefono, telefono.getText().toString());
        datos.put(Transacciones.nota, nota.getText().toString());
        datos.put(Transacciones.imagen,photoByteArray);

        Long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, datos);

        Toast.makeText(getApplicationContext(), "Persona ingresada correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
    }

    public void TomarFoto(){
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ( takeImageIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE);
       }
    }

    //para uso de camaras
    private void permisoFoto() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    peticion_camara);
        }
        else {
            tomarFoto();
        }
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,peticion_foto);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == peticion_camara){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tomarFoto();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == peticion_foto && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
            imagenView3.setImageBitmap(imagen);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
            photoByteArray = stream.toByteArray();

        }
    }

}