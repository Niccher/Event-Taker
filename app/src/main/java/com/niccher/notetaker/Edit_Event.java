package com.niccher.notetaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class Edit_Event extends AppCompatActivity {

    public static final String Ex_Title = "com.niccher.notetaker.Ex_Title";
    public static final String Ex_Desc = "com.niccher.notetaker.Ex_Desc";
    public static final String Ex_Priori = "com.niccher.notetaker.Ex_Priori";
    public static final String Ex_Date = "com.niccher.notetaker.Ex_Date";
    public static final String Ex_Edit = "com.niccher.notetaker.Ex_Edit";
    
    private EditText new_title,new_desc;
    private NumberPicker new_priori;
    private Button new_save;
    Intent ed;

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
        setTitle("Edit Event");

        ed = getIntent();

        if (ed.hasExtra(Ex_Edit)){
            new_title.setText(ed.getStringExtra(Ex_Title));
            //int pr = Integer.parseInt(ed.getStringExtra(Ex_Priori));
            new_priori.setValue(ed.getIntExtra(Ex_Priori,-1));
            new_desc.setText(ed.getStringExtra(Ex_Desc));
        }


        new_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewNote();
            }
        });
    }

    private void saveNewNote() {
        String t_title = new_title.getText().toString();
        String t_body = new_desc.getText().toString();
        int t_priori = new_priori.getValue();

        if (t_title.trim().isEmpty() || t_body.trim().isEmpty() ){
            Toast.makeText(this, "Please fill both Event Title and Event Body before saving ", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Ex_Title,t_title);
        data.putExtra(Ex_Desc,t_body);
        data.putExtra(Ex_Priori,t_priori);
        data.putExtra(Ex_Date,ed.getStringExtra(Ex_Date));

        int primary_key =  ed.getIntExtra(Ex_Edit,-1);
        if (primary_key != -1){
            data.putExtra(Ex_Edit, primary_key);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}
