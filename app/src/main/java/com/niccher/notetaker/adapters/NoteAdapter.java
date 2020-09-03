package com.niccher.notetaker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niccher.notetaker.usables.Note;
import com.niccher.notetaker.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.NoteHolder>{

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.tx_title.setText(currentNote.getTitle());
        holder.tx_priority.setText(String.valueOf(currentNote.getPriority()));
        holder.tx_body.setText(currentNote.getDescription());
        holder.tx_dat.setText(currentNote.getDat());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void SetNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView tx_title,tx_priority,tx_body,tx_dat;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            tx_title = itemView.findViewById(R.id.txt_title);
            tx_priority = itemView.findViewById(R.id.txt_priority);
            tx_body = itemView.findViewById(R.id.txt_body);
            tx_dat = itemView.findViewById(R.id.txt_dat);

        }
    }
}
