package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String imageName;

    public String customer_name;

    public String phone;

    public String email;

    public Customer(String imageName, String customer_name, String phone, String address, String email) {
        this.imageName = imageName;
        this.customer_name = customer_name;
        this.phone = phone;
        this.email = email;
    }
}
