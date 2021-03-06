package com.niccher.notetaker.usables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    private String title;
    private String description;
    private String dat;

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int priority;

    public Note(String title, String description, int priority, String dat) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dat = dat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getDat() {
        return dat;
    }

    public int getId() {
        return id;
    }
}
