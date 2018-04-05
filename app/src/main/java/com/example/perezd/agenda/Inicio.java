package com.example.perezd.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void lista(View v){
        Intent lista=new Intent(this,lista.class);
        startActivity(lista);
    }

    public void cont(View v){
        Intent con=new Intent(this,Contacto.class);
        con.putExtra("id",-1);
        startActivity(con);
    }

}
