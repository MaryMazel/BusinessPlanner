package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

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

    @TypeConverters(StateConverter.class)
    public State state;

    public Customer(String customer_name, String phone, String address, String email, long profit, long deal_date, State state) {
        this.customer_name = customer_name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.profit = profit;
        this.deal_date = deal_date;
        this.state = state;
    }

    public enum State {
        ANALISE(0),
        IN_PROGRESS(1),
        CLOSED(2);

        private int code;

        State(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
