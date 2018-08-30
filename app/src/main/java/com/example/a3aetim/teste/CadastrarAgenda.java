package com.example.a3aetim.teste;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastrarAgenda extends AppCompatActivity {

    //https://www.youtube.com/watch?v=czKLAx750N0 -> Video do DatePicker
    //Outro DatePicker https://pt.stackoverflow.com/questions/206749/android-datepicker-em-um-edittext
    EditText edTit,edDesc;
    TextView tvData;
    DatabaseHelper helper;
    Button BSel;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    //1970-01-01 00:00:00 UTC
    Calendar data = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_agenda);

        helper = new DatabaseHelper(this);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        SelDate();
        edTit = (EditText)findViewById(R.id.edtTitulo);
        edDesc = (EditText)findViewById(R.id.edtDesc);
        tvData = (TextView)findViewById(R.id.tvData);
        CheckUpdate();
    }

    public void Voltar(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void Salvar(View view){
        try{
            if(!Updating){
                int IdUser = GetUser();
                if(IdUser != 0){
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("Titulo", edTit.getText().toString());
                    values.put("Mensagem", edDesc.getText().toString());
                    values.put("Data", df.format(data.getTime()));
                    values.put("IdUserFK", IdUser);

                    long res = db.insert("Agenda", null, values);

                    if(res != -1){
                        Toast.makeText(this,"Event Saved", Toast.LENGTH_SHORT).show();
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
                values.put("Mensagem", edDesc.getText().toString());
                values.put("Data", df.format(data.getTime()));

                long res = db.update("Agenda", values, "IdAgenda ="+IdAgendaUP+" AND IdUserFK ="+ IdUserUP, null);

                if(res != -1){
                    Toast.makeText(this,"Event Changed", Toast.LENGTH_SHORT).show();
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
            month_x = month; //Data começa no 0
            day_x = day;
            tvData.setText(day_x +"/"+ (month_x+1) +"/"+ year_x);
            data.set(Calendar.DAY_OF_MONTH, day_x);
            data.set(Calendar.MONTH, month_x);
            data.set(Calendar.YEAR, year_x);
            /*data.setDate(day_x);
            data.setMonth(month_x);
            data.setYear(year_x);*/
            //Toast.makeText(CadastrarAgenda.this, year_x +"/"+ month_x +"/"+ day_x, Toast.LENGTH_LONG).show();
        }
    };

    public int GetUser(){
        final String PREFS_NAME = "User";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int i = settings.getInt("IdUser", 0);
        return i;
    }

    int IdAgendaUP,IdUserUP;
    String TitleUP, MsgUP, DataUP;
    boolean Updating = false;

    public void CheckUpdate(){
        try{
            IdAgendaUP = getIntent().getIntExtra("EXTRA_IDAGENDA", 0);
            if(IdAgendaUP >0){
                Updating = true;
                TitleUP= getIntent().getStringExtra("EXTRA_TITLE");
                MsgUP = getIntent().getStringExtra("EXTRA_MSG");
                DataUP = getIntent().getStringExtra("EXTRA_DATA");
                IdUserUP = getIntent().getIntExtra("EXTRA_IDUSER",0);
                edTit.setText(TitleUP);
                edDesc.setText(MsgUP);

                Calendar c = Calendar.getInstance();
                Date d = df.parse(DataUP);
                c.setTime(d);
                //dpickerListener(this, dpickerListener, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                year_x = c.get(Calendar.YEAR);
                month_x = c.get(Calendar.MONTH);
                day_x = c.get(Calendar.DAY_OF_MONTH);
                tvData.setText(day_x +"/"+ (month_x+1) +"/"+ year_x);
            }
        }catch(Exception erro){
            Toast.makeText(this,"Erro: " + erro, Toast.LENGTH_SHORT).show();
        }
    }

}
