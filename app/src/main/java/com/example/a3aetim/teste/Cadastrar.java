package com.example.a3aetim.teste;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastrar extends AppCompatActivity {

    DatabaseHelper helper;
    EditText tName, tPass, tPass2;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        tName = (EditText)findViewById(R.id.edtName);
        tPass = (EditText)findViewById(R.id.edtPass);
        tPass2 = (EditText)findViewById(R.id.edtPass2);
    }

    public void Voltar(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void Cadastrar(View view){
        if (tPass2.getText().toString().equals("") || tPass.getText().toString().equals("") || tName.getText().toString().equals("")){
            Toast.makeText(this,"Fields can't be null", Toast.LENGTH_SHORT).show();
            if (tPass2.getText().toString().equals("")){
                Toast.makeText(this,"Confirm your password", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (tPass.getText().toString().equals(tPass2.getText().toString())){
                if(!(VerifyUnique())){
                    ContentValues values = new ContentValues();
                    values.put("Nome",tName.getText().toString());
                    values.put("Senha",tPass.getText().toString());

                    long res = db.insert("User", null, values);

                    if(res != -1){
                        Toast.makeText(this,"User saved", Toast.LENGTH_SHORT).show();
                        tName.setText("");
                        tPass.setText("");
                        tPass2.setText("");
                    }
                    else{
                        Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"Username is already been used", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean VerifyUnique(){ //true == tem user false == n tem
        try {
            Cursor c = db.rawQuery("SELECT * FROM User WHERE Nome = '" + tName.getText() + "'", null);

            c.moveToFirst();
            if (Integer.parseInt(c.getString(0)) == 0) {
                return false;
            }
            else{
                return true;
            }
        }catch (Exception erro){
            return false;
        }
    }
}
