package com.example.a3aetim.teste;

import android.content.Intent;
import android.content.SharedPreferences;
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
        try {
            c.moveToFirst();

            /*if (c.getColumnIndex("IdUser") > 0) {
                SalvarSP(name.trim(),pass.trim());
                Intent i = new Intent(this, Menu.class);
                startActivity(i);
            } else */
            int id = Integer.parseInt(c.getString(0));
            if (id > 0) {
                UserClass u = new UserClass(id,name,pass);
                SalvarSP(u);
                Intent i = new Intent(this, Menu.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception erro){
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    public void SalvarSP(UserClass u){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("IdUser",u.IdUser);
        editor.putString("Name",u.Nome);
        editor.putString("Senha",u.Senha);
        editor.commit();
    }
}
