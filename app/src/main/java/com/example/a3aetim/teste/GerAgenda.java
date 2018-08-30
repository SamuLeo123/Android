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

public class GerAgenda extends AppCompatActivity {

    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_agenda);

        helper = new DatabaseHelper(this);
        GetEvents();
    }

    public void Voltar(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    int in = 0;
    public void GetEvents(){
        int IdU = GetIdU();
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM Agenda WHERE IdUserFK = "+IdU, null);
            while(c.moveToNext()){
                final Button b = new Button(this);
                b.setText(c.getString(1));
                int tag = in;
                b.setTag(tag);
                LinearLayout ll = (LinearLayout)findViewById(R.id.llGerA);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(b,lp);

                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        BClick(String.valueOf(b.getTag()));
                    }
                });
                in++;
            }

        }catch(Exception erro){
            Toast.makeText(this, "Error: " + erro, Toast.LENGTH_SHORT).show();
        }

    }

    public int GetIdU(){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int i = settings.getInt("IdUser", 0);
        return i;
    }

    public void BClick(String a){
        int IdU = GetIdU();
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            LinearLayout ll = (LinearLayout)findViewById(R.id.llGerA);
            Button b = (Button)ll.findViewWithTag(Integer.parseInt(a));
            String Tit = b.getText().toString();
            Cursor c = db.rawQuery("SELECT * FROM Agenda WHERE IdUserFK = "+IdU + " AND Titulo = '"+ Tit +"'", null);
            c.moveToFirst(); //i -> 0: id,1:Tit, 2: Msg, 3 : Data, 4: IdUser
            int id = Integer.parseInt(c.getString(0));
            String title = c.getString(1);
            String msg = c.getString(2);
            String data = c.getString(3);
            int iduser = Integer.parseInt(c.getString(4));

            Intent intent = new Intent(this, CadastrarAgenda.class);
            intent.putExtra("EXTRA_IDAGENDA", id);
            intent.putExtra("EXTRA_TITLE", title);
            intent.putExtra("EXTRA_MSG", msg);
            intent.putExtra("EXTRA_DATA", data);
            intent.putExtra("EXTRA_IDUSER", iduser);
            startActivity(intent);
        }
        catch(Exception erro){
            Toast.makeText(this, "Error: "+erro, Toast.LENGTH_SHORT).show();
        }
    }
}
