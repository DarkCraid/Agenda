package com.example.perezd.agenda;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Contacto extends AppCompatActivity {
    EditText nom,num,dir;
    Button btn_add,btn_mod;
    ImageView img;
    public int id;
    public String pictureName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        nom=(EditText)findViewById(R.id.txt_nombre);
        num=(EditText)findViewById(R.id.txt_num);
        dir=(EditText)findViewById(R.id.txt_dir);

        btn_add=(Button)findViewById(R.id.btn_add);
        btn_mod=(Button)findViewById(R.id.btn_mod);
        btn_mod.setVisibility(View.GONE);
        img=(ImageView)findViewById(R.id.img);

        Bundle b=getIntent().getExtras();
        id = b.getInt("id");
        pictureName="";
        if(id!=-1)
            ver(id);
    }

    public SQLiteDatabase  conect(){
        Coneccion mod=new Coneccion(this,"agenda",null,1);
        SQLiteDatabase db=mod.getWritableDatabase();
        return db;
    }
    public void takePicture(View v){
        if(id!=-1){
            pictureName="picture_"+String.valueOf(id - 1)+".jpg";
        }
        else{
            SQLiteDatabase db=conect();
            Cursor file=db.rawQuery("select count(*) from contactos",null);
            file.moveToLast();
            pictureName="picture_"+String.valueOf(file.getInt(0))+".jpg";
        }
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto=new File(getExternalFilesDir(null),pictureName);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivityForResult(camera,0);
        //startActivity(camera);
        showPicture(pictureName);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                showPicture(pictureName);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    public void showPicture(String nombrePic){
        Bitmap image= BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+nombrePic);
        img.setImageBitmap(image);
    }

    public void guardar(View v){
        if(! nom.getText().toString().isEmpty() && ! num.getText().toString().isEmpty() && ! dir.getText().toString().isEmpty() && !pictureName.isEmpty()){
            SQLiteDatabase db=conect();
            ContentValues registro=new ContentValues();
            registro.put("nombre",nom.getText().toString());
            registro.put("telefono",num.getText().toString());
            registro.put("direccion",dir.getText().toString());
            db.insert("contactos",null,registro);
            showPicture("");
            db.close();
            nom.setText(null);num.setText(null);dir.setText(null);
            Toast.makeText(this,"Registro guardado", Toast.LENGTH_LONG).show();
        }
        else if(pictureName.isEmpty())
            Toast.makeText(this,"Tome una fotografía",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Capture todos los campos",Toast.LENGTH_LONG).show();
    }

    public void ver(int id){
        SQLiteDatabase db = conect();
        btn_add.setVisibility(View.GONE);
        btn_mod.setVisibility(View.VISIBLE);
        Cursor file=db.rawQuery("select nombre, telefono, direccion from contactos where id="+id,null);
        if(file.moveToFirst()){
            nom.setText(file.getString(0));
            num.setText(file.getString(1));
            dir.setText(file.getString(2));
            showPicture("picture_"+String.valueOf(id - 1)+".jpg");

        }else
            Toast.makeText(this,"No se encontró nada",Toast.LENGTH_LONG).show();
        db.close();
    }
    public void salir(View v){
        finish();
    }

    public void modificar(View v){
        SQLiteDatabase db=conect();
        ContentValues reg=new ContentValues();
        reg.put("nombre",nom.getText().toString());
        reg.put("telefono",num.getText().toString());
        reg.put("direccion",dir.getText().toString());

        if(db.update("contactos",reg,"id="+id,null)!=0)
            Toast.makeText(this,"Se ha modificado",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"No se modifico",Toast.LENGTH_LONG).show();
        db.close();
    }
}
