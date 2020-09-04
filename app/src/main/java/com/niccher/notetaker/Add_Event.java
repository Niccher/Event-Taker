package com.niccher.notetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Add_Event extends AppCompatActivity {

    public static final String Ex_Title = "com.niccher.notetaker.Ex_Title";
    public static final String Ex_Desc = "com.niccher.notetaker.Ex_Desc";
    public static final String Ex_Priori = "com.niccher.notetaker.Ex_Priori";
    public static final String Ex_Date = "com.niccher.notetaker.Ex_Date";
    
    private EditText new_title,new_desc;
    private NumberPicker new_priori;
    private Button new_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        
        new_desc = findViewById(R.id.nw_body);
        new_title = findViewById(R.id.nw_title);
        new_priori = findViewById(R.id.nw_priori);
        new_save = findViewById(R.id.nw_save);

        new_priori.setMaxValue(10);
        new_priori.setMinValue(2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Log a new Event");

        new_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewNote();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_newnote, menu);
        return true;//super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                saveNewNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNewNote() {
        String t_title = new_title.getText().toString();
        String t_body = new_desc.getText().toString();
        int t_priori = new_priori.getValue();

        Date currentTime = Calendar.getInstance().getTime();
        //Log.e("Timed AS ", "saveNewNote: "+Reg(String.valueOf(currentTime)));

        if (t_title.trim().isEmpty() || t_body.trim().isEmpty() ){
            Toast.makeText(this, "Please fill both Event Title and Event Body before saving ", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Ex_Title,t_title);
        data.putExtra(Ex_Desc,t_body);
        data.putExtra(Ex_Priori,t_priori);
        data.putExtra(Ex_Date,Reg(String.valueOf(currentTime)));

        setResult(RESULT_OK,data);
        finish();
    }

    private String Reg(String dt){
        String good  = null;
        String[] separated = dt.split(" ");
        good = separated[1] +" "+ separated[2] +" "+ separated[5];
        return good;
    }
}
