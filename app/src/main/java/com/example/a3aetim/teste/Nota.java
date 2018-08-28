package com.example.a3aetim.teste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Nota extends AppCompatActivity {
    EditText edNota;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        helper = new DatabaseHelper(this);
        edNota = (EditText)findViewById(R.id.edtNota);
    }
}
