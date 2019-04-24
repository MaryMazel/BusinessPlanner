package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.businessplanner.data.entities.CalendarEvent;

import java.util.List;

@Dao
public interface CalendarDao {
    @Insert
    void insert(CalendarEvent... calendarEvents);

    @Query("select * from CalendarEvent")
    List<CalendarEvent> getEvents();

    @Query("select * from calendarevent where date = :date")
    List<CalendarEvent> getCurrentDayEvents(String date);

    @Query("delete from calendarevent where id = :id")
    void deleteEvent(long id);

    @Query("delete from calendarevent where date = :date")
    void deleteCurrentDayEvents(String date);

    @Query("update calendarevent set text = :text, fullDate = :fullDate where id = :id")
    void updateCalendarEvent(String text, long id, long fullDate);
}
