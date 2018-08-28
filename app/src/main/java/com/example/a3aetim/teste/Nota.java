package com.example.a3aetim.teste;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Nota extends AppCompatActivity {
    EditText edNota, edTit;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        helper = new DatabaseHelper(this);
        edNota = (EditText)findViewById(R.id.edtNota);
        edTit = (EditText)findViewById(R.id.edtTitulo);
    }

    public void Salvar(View view){
        try{
            int IdUser = GetUser();
            if(IdUser != 0) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Titulo", edTit.getText().toString());
                values.put("Texto", edNota.getText().toString());
                values.put("IdUserFK", IdUser);

                long res = db.insert("Nota", null, values);

                if(res != -1){
                    Toast.makeText(this,"Registro Salvo", Toast.LENGTH_SHORT).show();
                    Voltar(view);
                }
                else{
                    Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch(Exception erro){
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }
    }

    public int GetUser(){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int i = settings.getInt("IdUser", 0);
        return i;
    }

    public void Voltar(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
