package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.StateConverter;

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

    @TypeConverters(StateConverter.class)
    @Query("update customer set imageName = :imageName, customer_name = :name, phone = :phone, email = :email, address = :address, deal_date = :dealDate, profit = :profit, state = :state where id = :id")
    void updateCustomer(long id, String imageName, String name, String phone, String email, String address, long dealDate, long profit, Customer.State state);
}
