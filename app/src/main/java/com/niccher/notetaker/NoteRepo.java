package com.niccher.notetaker;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepo {

    private Note_Dao note_dao;
    private LiveData<List<Note>> allNotes;

    public NoteRepo(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        note_dao = database.note_dao();
        allNotes = note_dao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(note_dao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(note_dao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(note_dao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(note_dao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Note_Dao noteDao;

        private InsertNoteAsyncTask(Note_Dao  noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Note_Dao noteDao;

        private UpdateNoteAsyncTask(Note_Dao  noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private Note_Dao noteDao;

        private DeleteNoteAsyncTask(Note_Dao  noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{

        private Note_Dao noteDao;

        private DeleteAllNoteAsyncTask(Note_Dao  noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
