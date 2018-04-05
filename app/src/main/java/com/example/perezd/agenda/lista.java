package com.example.perezd.agenda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class lista extends AppCompatActivity {
    ListView lst;
    private ArrayList<String> contacto = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lst=(ListView)findViewById(R.id.lst);




        Coneccion mod=new Coneccion(this,"agenda",null,1);
        SQLiteDatabase db=mod.getWritableDatabase();
        Cursor cursor=db.rawQuery("select nombre from contactos",null);
        if(cursor.moveToFirst()){
            do{
                contacto.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }





        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacto);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent con=new Intent(getApplicationContext(),Contacto.class);
            con.putExtra("id",position+1);
            startActivity(con);
            }
        });
    }
    public void salir(View v){
        finish();
    }
}
