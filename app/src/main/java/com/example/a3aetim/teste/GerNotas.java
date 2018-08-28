package com.example.a3aetim.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GerNotas extends AppCompatActivity {

    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_notas);

        helper = new DatabaseHelper(this);
        GetNotas();
    }

    public void Voltar(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    int in = 0;
    public void GetNotas(){
        //https://pt.stackoverflow.com/questions/255109/select-no-sqlite-android
        //https://stackoverflow.com/questions/1851633/how-to-add-a-button-dynamically-in-android
        int IdU = GetIdU();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notas WHERE IdUserrFK = '"+IdU+"'", null);
        try{

            while(c.moveToNext()){
                Button b = new Button(this);
                b.setText(c.getString(1));
                LinearLayout ll = (LinearLayout)findViewById(R.id.LLButtons);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(b,lp);

                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        BClick(in);
                    }
                });
                in++;
            }
        }
        catch(Exception erro){
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    public int GetIdU(){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int i = settings.getInt("IdUser", 0);
        return i;
    }

    public void BClick(int i){
        Toast.makeText(this, "O num: " + i, Toast.LENGTH_SHORT).show();
    }
}
