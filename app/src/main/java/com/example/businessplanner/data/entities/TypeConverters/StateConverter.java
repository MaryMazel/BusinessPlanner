package com.example.businessplanner.data.entities.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.businessplanner.data.entities.Order;

import static com.example.businessplanner.data.entities.Order.State.ANALISE;
import static com.example.businessplanner.data.entities.Order.State.CLOSED;
import static com.example.businessplanner.data.entities.Order.State.IN_PROGRESS;

public class StateConverter {
    @TypeConverter
    public static Order.State toState(int state) {
        if (state == ANALISE.getCode()) {
            return ANALISE;
        } else if (state == IN_PROGRESS.getCode()) {
            return IN_PROGRESS;
        } else if (state == CLOSED.getCode()) {
            return CLOSED;
        } else {
            throw new IllegalArgumentException("Could not recognize state");
        }
    }

    @TypeConverter
    public static Integer toInteger(Order.State state) {
        return state.getCode();
    }
}