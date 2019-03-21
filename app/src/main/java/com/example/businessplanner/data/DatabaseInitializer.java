package com.example.businessplanner.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

public class DatabaseInitializer {
    @NonNull
    public static AppDatabase createDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "planner_database")
                .allowMainThreadQueries()
                .build();
    }
}
