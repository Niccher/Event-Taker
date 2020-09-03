package com.niccher.notetaker.usables;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.niccher.notetaker.interfaces.Note_Dao;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static  NoteDatabase instance;

    public abstract Note_Dao note_dao();

    public static synchronized NoteDatabase getInstance(Context cnt){

        if (instance ==null){

            instance = Room.databaseBuilder(cnt.getApplicationContext(),
                    NoteDatabase.class,"note_database").fallbackToDestructiveMigration()
                    .addCallback(rooCallback)
                    .build();
        }

        return instance;
    }

    private static  RoomDatabase.Callback rooCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void,Void>{
        private Note_Dao noteDao;

        private PopulateDBAsyncTask(NoteDatabase db){
            noteDao = db.note_dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));
            noteDao.insert(new Note("Title 4","Description 4",4));*/

            return null;
        }
    }
}
