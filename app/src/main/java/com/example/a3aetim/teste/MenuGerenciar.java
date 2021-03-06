package com.example.a3aetim.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuGerenciar extends AppCompatActivity{

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
        Intent i = new Intent(this, CadastrarAgenda.class);
        startActivity(i);
    }

    public void GerData(View view){
        Intent i = new Intent(this, GerAgenda.class);
        startActivity(i);
    }

    public void Sair(View view){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("IdUser",0);
        editor.putString("Name",null);
        editor.putString("Senha",null);
        editor.commit();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
