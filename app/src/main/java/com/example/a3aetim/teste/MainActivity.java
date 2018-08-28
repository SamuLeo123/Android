package com.example.a3aetim.teste;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper helper;
    EditText tName,tPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(this);
        tName = (EditText)findViewById(R.id.edtName);
        tPass = (EditText)findViewById(R.id.edtPass);

    }

    public void Cadastrar(View view){
        Intent i = new Intent(this, Cadastrar.class);
        startActivity(i);
    }

    public void Login(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        String name = tName.getText().toString();
        String pass = tPass.getText().toString();
        Cursor c = db.rawQuery("SELECT * FROM User WHERE TRIM(Nome) = '"+name.trim()+"' AND TRIM(Senha) = '"+pass.trim()+"'", null);
        c.moveToFirst();
        if(c.getColumnIndex("IdUser") > 0){
            Intent i = new Intent(this, Menu.class);
            startActivity(i);
        }
        else if(Integer.parseInt(c.getString(0)) > 0){
            Intent i = new Intent(this, Menu.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }

    }
}
