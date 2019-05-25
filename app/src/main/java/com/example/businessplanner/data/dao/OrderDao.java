package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.data.entities.TypeConverters.NetworkOwnerConverter;
import com.example.businessplanner.data.entities.TypeConverters.OrderTypeConverter;
import com.example.businessplanner.data.entities.TypeConverters.StateConverter;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order... orders);

    @Query("select * from `order`")
    List<Order> getOrders();

    @Query("select * from `order` where customerID = :customerID")
    List<Order> getCustomersOrders(long customerID);

    @Query("select * from `order` where id = :id")
    Order getOrderByID(long id);

    @Query("delete from `order` where id = :id")
    void deleteOrder(long id);

    @TypeConverters({NetworkOwnerConverter.class, OrderTypeConverter.class, StateConverter.class})
    @Query("update `order` set customerID = :customerID, address = :address, description = :description," +
            "networkOwner = :networkOwner, deal_date = :deal_date, capacity = :capacity, voltage = :voltage," +
            "reliability = :reliability, attachmentSpot = :attachmentSpot, meterType = :meterType, transformers = :transformers," +
            "state = :state, orderType = :orderType where id = :orderID")
    void updateOrder(long orderID, long customerID, String address, String description,
                     Order.NetworkOwner networkOwner, long deal_date, float capacity,
                     float voltage, int reliability, String attachmentSpot, String meterType,
                     int transformers, Order.State state, Order.OrderType orderType);
}
