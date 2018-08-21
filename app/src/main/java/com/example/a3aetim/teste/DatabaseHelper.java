package com.example.a3aetim.teste;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Banco = "BdDados";
    private static int versao = 1;

    public DatabaseHelper(Context context){
        super(context, Banco, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User ( IdUser INTEGER PRIMARY KEY autoincrement, Nome String, Senha String )");
        db.execSQL("CREATE TABLE Nota ( IdNota INTEGER PRIMARY KEY autoincrement, Texto String, IdUserFK INTEGER, foreign key (IdUserFK)  references User (IdUser))");
        db.execSQL("CREATE TABLE Agenda ( IdAgenda INTEGER PRIMARY KEY autoincrement, Titulo String, Mensagem String, Data DateTime, IDUserFK INTEGER, foreign key (IdUserFK)  references User (IdUser))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
