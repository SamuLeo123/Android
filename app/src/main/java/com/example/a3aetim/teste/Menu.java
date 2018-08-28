package com.example.a3aetim.teste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void CadNota(View view){
        Intent i = new Intent(this, Nota.class);
        startActivity(i);
    }

    public void GerNota(View view){
        Intent i = new Intent(this, GerNotas.class);
        startActivity(i);
    }

    public void CadData(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void GerData(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
