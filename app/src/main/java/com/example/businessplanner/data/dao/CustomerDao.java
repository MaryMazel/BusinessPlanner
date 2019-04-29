package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.businessplanner.data.entities.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Insert
    void insert(Customer... customers);

    @Query("select * from Customer")
    List<Customer> getCustomers();

    @Query("select * from Customer where id = :id")
    Customer getCustomerByID(long id);

    @Query("delete from customer where id = :id")
    void deleteCustomer(long id);
}