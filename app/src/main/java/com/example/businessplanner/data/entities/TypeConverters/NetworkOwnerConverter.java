package com.example.businessplanner.data.entities.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.businessplanner.data.entities.Order;

import static com.example.businessplanner.data.entities.Order.NetworkOwner.Kharkovoblenergo;
import static com.example.businessplanner.data.entities.Order.NetworkOwner.OSBB;
import static com.example.businessplanner.data.entities.Order.NetworkOwner.Other;
import static com.example.businessplanner.data.entities.Order.NetworkOwner.Railway;
import static com.example.businessplanner.data.entities.Order.NetworkOwner.Zhylkomservice;

public class NetworkOwnerConverter {
    @TypeConverter
    public static Order.NetworkOwner toNetworkOwner(int owner) {
        if (owner == Kharkovoblenergo.getCode()) {
            return Kharkovoblenergo;
        } else if (owner == Railway.getCode()) {
            return Railway;
        } else if (owner == Zhylkomservice.getCode()) {
            return Zhylkomservice;
        } else if (owner == OSBB.getCode()) {
            return OSBB;
        } else if (owner == Other.getCode()) {
            return Other;
        } else {
            throw new IllegalArgumentException("Could not recognize owner");
        }
    }

    @TypeConverter
    public static Integer toInteger(Order.NetworkOwner networkOwner) {
        return networkOwner.getCode();
    }

}
