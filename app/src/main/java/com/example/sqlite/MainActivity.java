package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        et_precio = (EditText)findViewById(R.id.txt_precio);
    }

    public void Registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            db.insert("articulos", null, registro);

            db.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        }

    };

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()) {
            Cursor fila = db.rawQuery("select descripcion, precio from articulos where codigo = " + codigo, null);

            if(fila.moveToFirst()) {
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(0));
                db.close();
            } else {
                Toast.makeText(this,"No existe el árticulo", Toast.LENGTH_SHORT).show();
                db.close();
            }
        } else {
            Toast.makeText(this,"Debes introducir el código del articulo", Toast.LENGTH_SHORT).show();
        }
    };

    public void Eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {

            int cantidad = db.delete("articulos", "codigo=" + codigo, null);
            db.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if(cantidad == 1) {
                Toast.makeText(this,"Artículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"El artículo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this,"Debes introducir el código del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracio", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = db.update("articulos", registro, "codigo=" + codigo, null);
            db.close();

            if(cantidad == 1) {
                Toast.makeText(this,"Artículo modificado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"El artículo no existe", Toast.LENGTH_SHORT).show();
            }

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    };



}
