package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CalendarEvent {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long fullDate;

    public String date;

    public String text;

    public CalendarEvent(long fullDate, String date, String text) {
        this.fullDate = fullDate;
        this.date = date;
        this.text = text;
    }
}
