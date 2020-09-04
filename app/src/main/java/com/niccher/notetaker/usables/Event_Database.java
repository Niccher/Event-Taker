package com.niccher.notetaker.usables;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.niccher.notetaker.interfaces.Event_Dao;

@Database(entities = {Note.class}, version = 2)
public abstract class Event_Database extends RoomDatabase {

    private static Event_Database instance;

    public abstract Event_Dao note_dao();

    public static synchronized Event_Database getInstance(Context cnt){

        if (instance ==null){

            instance = Room.databaseBuilder(cnt.getApplicationContext(),
                    Event_Database.class,"note_database").fallbackToDestructiveMigration()
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
        private Event_Dao noteDao;

        private PopulateDBAsyncTask(Event_Database db){
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
