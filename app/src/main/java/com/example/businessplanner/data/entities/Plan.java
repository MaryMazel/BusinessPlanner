package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Plan {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;

    public String note;

    public long date;

    public Plan(String title, String note, long date) {
        this.title = title;
        this.note = note;
        this.date = date;
    }
}
