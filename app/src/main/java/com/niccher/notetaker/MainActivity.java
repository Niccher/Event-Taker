package com.niccher.notetaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niccher.notetaker.adapters.Event_Adapter;
import com.niccher.notetaker.usables.Note;
import com.niccher.notetaker.models.Event_ViewModel;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Event_ViewModel eventViewModel;

    public static final int Add_Response = 1;
    public static final int Edit_Response = 3;

    Dialog myDialog;
    TextView popclos, pop_title, pop_body, pop_priori, pop_date, pop_edit;

    String term_accpt,term_liab,term_disclaimer,term_declare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.txt_addnote);

        myDialog = new Dialog(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Event.class);
                startActivityForResult(intent,Add_Response);
            }
        });

        RecyclerView rec = findViewById(R.id.recycl);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);

        final Event_Adapter adapter = new Event_Adapter();
        rec.setAdapter(adapter);

        term_accpt = "By accessing this service we assume you accept these terms and conditions. Do not continue to use this app if you do not agree to take all of the terms and conditions stated here";
        term_liab = "We shall not be hold responsible for any content that appears on platform. No content should appear on this platform, if it may be interpreted as rebellious, obscene or criminal, or which infringes, otherwise violates, or advocates the infringement or other violation of, any third party rights.\n" +
                "    We do not ensure that the information on this platform is correct, we do not warrant its completeness or accuracy; nor do we promise to ensure that the platform remains available or that the material on the posted is kept up to date.";
        term_disclaimer = "To the maximum extent permitted by applicable law, we exclude all representations, warranties and conditions relating to our platform and the use of this website. Nothing in this disclaimer will:\n" +
                "\n" +
                "    limit or exclude our or your liability for personality demeanor;\n" +
                "    limit or exclude our or your liability for fraud or fraudulent misrepresentation;\n" +
                "    limit any of our or your liabilities in any way that is not permitted under applicable law; or\n" +
                "    exclude any of our or your liabilities that may not be excluded under applicable law.";
        term_declare = "As long as the information and services on the platform are provided free of charge, we will not be liable for any loss or damage of any nature";


        eventViewModel = ViewModelProviders.of(this).get(Event_ViewModel.class);
        eventViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.SetNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //viewHolder.getAdapterPosition();
                eventViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rec);


        adapter.setOnItemClickListener(new Event_Adapter.ClickedEvent() {
            @Override
            public void onItmClick(final Note note) {
                try {
                    myDialog.setContentView(R.layout.part_pop);
                    popclos=(TextView) myDialog.findViewById(R.id.popclose);

                    pop_title=(TextView) myDialog.findViewById(R.id.evt_title);
                    pop_body=(TextView) myDialog.findViewById(R.id.evt_body);
                    pop_priori=(TextView) myDialog.findViewById(R.id.evt_priori);
                    pop_date=(TextView) myDialog.findViewById(R.id.evt_date);
                    pop_edit=(TextView) myDialog.findViewById(R.id.evt_edit);

                    pop_title.setText(note.getTitle());
                    pop_body.setText(note.getDescription());
                    pop_priori.setText("Urgency > "+String.valueOf(note.getPriority()));
                    pop_date.setText(note.getDat());

                    popclos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });

                    pop_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                            Intent edit = new Intent(MainActivity.this, Edit_Event.class);
                            edit.putExtra(Edit_Event.Ex_Title , note.getTitle());
                            edit.putExtra(Edit_Event.Ex_Edit , note.getId());
                            edit.putExtra(Edit_Event.Ex_Desc , note.getDescription());
                            edit.putExtra(Edit_Event.Ex_Priori , note.getPriority());
                            edit.putExtra(Edit_Event.Ex_Date , note.getDat());
                            startActivityForResult(edit,Edit_Response );
                        }
                    });

                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();
                }catch ( Exception pop){
                    Toast.makeText(MainActivity.this, "An error was encountered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(this,
                "link1",
                "link2");

        dialog.addPoliceLine(term_accpt);
        dialog.addPoliceLine(term_liab);
        dialog.addPoliceLine(term_disclaimer);
        dialog.addPoliceLine(term_declare);

        dialog.setTitleTextColor(Color.parseColor("#222222"));
        dialog.setAcceptButtonColor(ContextCompat.getColor(this, R.color.colorAccent));

        dialog.setOnClickListener(new PrivacyPolicyDialog.OnClickListener() {
            @Override
            public void onAccept(boolean isFirstTime) {
            }

            @Override
            public void onCancel() {
                Log.e("MainActivity", "Policies not accepted");
                finish();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Add_Response && resultCode == RESULT_OK){
            String got_title = data.getStringExtra(Add_Event.Ex_Title);
            String got_desc = data.getStringExtra(Add_Event.Ex_Desc);
            String got_date = data.getStringExtra(Add_Event.Ex_Date);
            int got_priori = data.getIntExtra(Add_Event.Ex_Priori,1);

            Note note = new Note(got_title,got_desc,got_priori, got_date);
            eventViewModel.insert(note);

            Toast.makeText(this, "Event Succesfully Created", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Edit_Response && resultCode == RESULT_OK){
            int prim = data.getIntExtra(Edit_Event.Ex_Edit, -1);

            if (prim == -1){
                Toast.makeText(this, "Event cannot be Edited", Toast.LENGTH_SHORT).show();
            }
            else {
                String got_title = data.getStringExtra(Edit_Event.Ex_Title);
                String got_desc = data.getStringExtra(Edit_Event.Ex_Desc);
                String got_date = data.getStringExtra(Edit_Event.Ex_Date);
                int got_priori = data.getIntExtra(Edit_Event.Ex_Priori,1);

                Note note = new Note(got_title,got_desc,got_priori, got_date);
                note.setId(prim);
                eventViewModel.update(note);

                Toast.makeText(this, "Event updated succesfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Event not Created", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Boolean stat = false;
        switch (item.getItemId()){
            case R.id.menu_del_all:
                eventViewModel.deleteAllNotes();
                Toast.makeText(this, "All Event Destroyed", Toast.LENGTH_SHORT).show();
                return true;
            /*case R.id.menu_theme_light:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);*/
            case  R.id.menu_theme_dark:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
