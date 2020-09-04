package com.niccher.notetaker.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.niccher.notetaker.usables.Event_Repo;
import com.niccher.notetaker.usables.Note;

import java.util.List;

public class Event_ViewModel extends AndroidViewModel {

    private Event_Repo repository;
    private LiveData<List<Note>> allNotes;

    public Event_ViewModel(@NonNull Application application) {
        super(application);
        repository = new Event_Repo(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }
    public void update(Note note) {
        repository.update(note);
    }
    public void delete(Note note) {
        repository.delete(note);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
