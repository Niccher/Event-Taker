package com.niccher.notetaker.usables;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.niccher.notetaker.interfaces.Event_Dao;

import java.util.List;

public class Event_Repo {

    private Event_Dao event_dao;
    private LiveData<List<Note>> allNotes;

    public Event_Repo(Application application){
        Event_Database database = Event_Database.getInstance(application);
        event_dao = database.note_dao();
        allNotes = event_dao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(event_dao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(event_dao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(event_dao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(event_dao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Event_Dao noteDao;

        private InsertNoteAsyncTask(Event_Dao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Event_Dao noteDao;

        private UpdateNoteAsyncTask(Event_Dao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Event_Dao noteDao;

        private DeleteNoteAsyncTask(Event_Dao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{

        private Event_Dao noteDao;

        private DeleteAllNoteAsyncTask(Event_Dao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
