package com.example.businessplanner.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.businessplanner.data.dao.CalendarDao;
import com.example.businessplanner.data.dao.CustomerDao;
import com.example.businessplanner.data.dao.PlanDao;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Plan;

@Database(entities = {Plan.class, CalendarEvent.class, Customer.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlanDao planDao();
    public abstract CalendarDao calendarDao();
    public abstract CustomerDao customerDao();
}
