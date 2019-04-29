package com.example.businessplanner.data.entities;

import android.arch.persistence.room.TypeConverter;

import static com.example.businessplanner.data.entities.Customer.State.ANALISE;
import static com.example.businessplanner.data.entities.Customer.State.CLOSED;
import static com.example.businessplanner.data.entities.Customer.State.IN_PROGRESS;

public class StateConverter {

    @TypeConverter
    public static Customer.State toState(int state) {
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
    public static Integer toInteger(Customer.State state) {
        return state.getCode();
    }
}