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

        CheckUpdate();
    }

    public void Salvar(View view){
        try{
            if(!Updating){
                int IdUser = GetUser();
                if(IdUser != 0) {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("Titulo", edTit.getText().toString());
                    values.put("Texto", edNota.getText().toString());
                    values.put("IdUserFK", IdUser);

                    long res = db.insert("Nota", null, values);

                    if(res != -1){
                        Toast.makeText(this,"Note Saved", Toast.LENGTH_SHORT).show();
                        Voltar(view);
                    }
                    else{
                        Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Titulo", edTit.getText().toString());
                values.put("Texto", edNota.getText().toString());

                long res = db.update("Nota", values, "IdNota ="+IdNoteUP+" AND IdUserFK ="+ IdUserUP, null);

                if(res != -1){
                    Toast.makeText(this,"Note Changed", Toast.LENGTH_SHORT).show();
                    Voltar(view);
                }
                else{
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch(Exception erro){
            Toast.makeText(this,"Error: " + erro, Toast.LENGTH_SHORT).show();
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

    int IdNoteUP,IdUserUP;
    String TitleUP, MsgUP;
    boolean Updating = false;
    public void CheckUpdate(){
        try{
            IdNoteUP = getIntent().getIntExtra("EXTRA_IDNOTE",0);
            if(IdNoteUP > 0){
                Updating = true;
                TitleUP = getIntent().getStringExtra("EXTRA_TITLE");
                MsgUP = getIntent().getStringExtra("EXTRA_MSG");
                IdUserUP = getIntent().getIntExtra("EXTRA_IDUSER",0);
                edTit.setText(TitleUP);
                edNota.setText(MsgUP);
            }

        }
        catch(Exception erro){
            Toast.makeText(this,"Erro: " + erro, Toast.LENGTH_SHORT).show();
        }
    }
}
