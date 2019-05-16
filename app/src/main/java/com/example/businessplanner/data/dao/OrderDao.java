package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.businessplanner.data.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order... orders);

    @Query("select * from `order` group by customerID")
    List<Order> getOrders();

    @Query("select * from `order` where customerID = :customerID")
    List<Order> getCustomersOrders(long customerID);

    @Query("select * from `order` where id = :id")
    Order getOrderByID(long id);

    @Query("delete from `order` where id = :id")
    void deleteOrder(long id);
}
