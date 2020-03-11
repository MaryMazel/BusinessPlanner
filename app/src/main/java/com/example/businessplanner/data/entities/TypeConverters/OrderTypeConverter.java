package com.example.businessplanner.data.entities.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.businessplanner.data.entities.Order;

import static com.example.businessplanner.data.entities.Order.OrderType.ContractRenewal;
import static com.example.businessplanner.data.entities.Order.OrderType.ElectricHeating;
import static com.example.businessplanner.data.entities.Order.OrderType.GreenRate;
import static com.example.businessplanner.data.entities.Order.OrderType.InnerNetworkProject;
import static com.example.businessplanner.data.entities.Order.OrderType.OuterNetworkProject;
import static com.example.businessplanner.data.entities.Order.OrderType.TechConditions;

public class OrderTypeConverter {
    @TypeConverter
    public static Order.OrderType toNetworkOwner(int owner) {
        if (owner == ContractRenewal.getCode()) {
            return ContractRenewal;
        } else if (owner == ElectricHeating.getCode()) {
            return ElectricHeating;
        } else if (owner == GreenRate.getCode()) {
            return GreenRate;
        } else if (owner == TechConditions.getCode()) {
            return TechConditions;
        } else if (owner == InnerNetworkProject.getCode()) {
            return InnerNetworkProject;
        } else if (owner == OuterNetworkProject.getCode()) {
            return OuterNetworkProject;
        } else {
            throw new IllegalArgumentException("Could not recognize owner");
        }
    }

    @TypeConverter
    public static Integer toInteger(Order.OrderType orderType) {
        return orderType.getCode();
    }
}
