package com.example.a3aetim.teste;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CadastrarAgenda extends AppCompatActivity {

    //https://www.youtube.com/watch?v=czKLAx750N0 -> Video do DatePicker
    //Outro DatePicker https://pt.stackoverflow.com/questions/206749/android-datepicker-em-um-edittext
    Button BSel;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_agenda);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        SelDate();
    }

    public void Voltar(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void Salvar(View view){

    }

    public void SelDate(){
        BSel = (Button)findViewById(R.id.btnSelDate);

        BSel.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }


    @Override
    protected Dialog onCreateDialog(int id){
        if(id ==DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            year_x = year;
            month_x = month + 1; //Data começa no 0
            day_x = day;
            Toast.makeText(CadastrarAgenda.this, year_x +"/"+ month_x +"/"+ day_x, Toast.LENGTH_LONG).show();
        }
    };

}
