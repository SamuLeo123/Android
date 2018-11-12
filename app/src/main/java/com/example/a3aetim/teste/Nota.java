package com.example.a3aetim.teste;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class Nota extends AppCompatActivity {
    EditText edNota, edTit;
    DatabaseHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        edNota = (EditText)findViewById(R.id.edtNota);
        edTit = (EditText)findViewById(R.id.edtTitulo);
        CheckUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gerenciar, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.action_remove: removerNota(); break;
        }

        return true;
    }

    private void removerNota() {
        int value = getIntent().getIntExtra("EXTRA_IDNOTE",0);
        db.execSQL("DELETE FROM Nota WHERE IdNota ='"+value+"'");
        db.close();
        voltar();
        Toast.makeText(this,"Your note has been removed", Toast.LENGTH_SHORT).show();
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
        voltar();
    }

    public void voltar(){
        try{
            Intent i = new Intent(this, MenuGerenciar.class);
            startActivity(i);
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }
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
