package com.example.a3aetim.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class GerNotas extends AppCompatActivity {

    DatabaseHelper helper;
    ArrayList<NotaClass> notas;
    private RecyclerView mRecyclerView;
    private NotaAdapter mRVAdapter;
    private RecyclerView.LayoutManager mRVLManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_notas);

        helper = new DatabaseHelper(this);
        notas = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerViewGerNotas);
        mRVLManager = new LinearLayoutManager(this);
        setmRecyclerView();
    }

    private void setmRecyclerView(){
        GetNotas();
        mRVAdapter = new NotaAdapter(notas);
        mRecyclerView.setLayoutManager(mRVLManager);
        mRecyclerView.setAdapter(mRVAdapter);

        mRVAdapter.setOnitemClickListener(new NotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent intent = new Intent(getApplicationContext(), Nota.class);
                intent.putExtra("EXTRA_IDNOTE", notas.get(position).getIdNota());
                intent.putExtra("EXTRA_TITLE", notas.get(position).getTitulo());
                intent.putExtra("EXTRA_MSG",notas.get(position).getTexto());
                intent.putExtra("EXTRA_IDUSER", notas.get(position).getIdUser());
                startActivity(intent);
            }
        });
    }


    public void Voltar(View view){
        Intent i = new Intent(this, MenuGerenciar.class);
        startActivity(i);
    }

    public void GetNotas(){
        //https://pt.stackoverflow.com/questions/255109/select-no-sqlite-android
        //https://stackoverflow.com/questions/1851633/how-to-add-a-button-dynamically-in-android
        int IdU = GetIdU();
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            Cursor c = db.rawQuery("SELECT * FROM Nota WHERE IdUserFK = "+IdU, null);
            while(c.moveToNext()){
                int IdNota = c.getInt(0);
                 String Titulo = c.getString(1);
                 String Texto = c.getString(2);
                 int IdUser = c.getInt(3);
                notas.add(new NotaClass(IdNota,Titulo,Texto,IdUser));
            }
            c.close();
        }
        catch(Exception erro){
            Toast.makeText(this, "Error: "+erro, Toast.LENGTH_SHORT).show();
        }
    }

    public int GetIdU(){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int i = settings.getInt("IdUser", 0);
        return i;
    }

}
