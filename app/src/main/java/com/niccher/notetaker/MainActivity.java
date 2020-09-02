package com.niccher.notetaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Observable;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int Req_Response = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.txt_addnote);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent,Req_Response);
            }
        });

        RecyclerView rec = findViewById(R.id.recycl);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        rec.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Toast.makeText(MainActivity.this, "Manny is here", Toast.LENGTH_SHORT).show();
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
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note droped", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rec);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==Req_Response && resultCode == RESULT_OK){
            String got_title = data.getStringExtra(AddNote.Ex_Title);
            String got_desc = data.getStringExtra(AddNote.Ex_Desc);
            int got_priori = data.getIntExtra(AddNote.Ex_Priori,1);

            Note note = new Note(got_title,got_desc,got_priori);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Succesfully Created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not Created", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            case R.id.menu_del_all:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Destroyed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
