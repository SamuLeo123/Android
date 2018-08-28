package com.example.a3aetim.teste;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastrar extends AppCompatActivity {

    DatabaseHelper helper;
    EditText tName, tPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        helper = new DatabaseHelper(this);
        tName = (EditText)findViewById(R.id.edtName);
        tPass = (EditText)findViewById(R.id.edtPass);
    }

    public void Voltar(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void Cadastrar(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nome",tName.getText().toString());
        values.put("Senha",tPass.getText().toString());

        long res = db.insert("User", null, values);

        if(res != -1){
            Toast.makeText(this,"Registro Salvo", Toast.LENGTH_SHORT).show();
            tName.setText("");
            tPass.setText("");
        }
        else{
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }

    }
}
