package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String customer_name;

    public String phone;

    public String address;

    public String email;

    public long profit;

    public long deal_date;

    public State state;


    public enum State {
        ANALIZE, IN_PROGRESS, CLOSED
    }
}
