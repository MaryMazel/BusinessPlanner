package com.example.businessplanner.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.businessplanner.data.AppDatabase;
import com.example.businessplanner.data.DatabaseInitializer;
import com.example.businessplanner.data.dao.CalendarDao;
import com.example.businessplanner.data.dao.CustomerDao;
import com.example.businessplanner.data.dao.OrderDao;
import com.example.businessplanner.data.dao.PlanDao;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.data.entities.Plan;

import java.util.List;

public class DatabaseManager {
    private AppDatabase database;
    private PlanDao planDao;
    private CalendarDao calendarDao;
    private CustomerDao customerDao;
    private OrderDao orderDao;

    public DatabaseManager(@NonNull Context context) {
        database = DatabaseInitializer.createDatabase(context);
        this.planDao = database.planDao();
        this.calendarDao = database.calendarDao();
        this.customerDao = database.customerDao();
        this.orderDao = database.orderDao();
    }

    //planDao region

    public void insertPlan(Plan plan) {
        planDao.insert(plan);
    }

    public List<Plan> getPlans() {
        return planDao.getAllPlans();
    }

    public Plan getPlan(long id) {
        return planDao.getPlan(id);
    }

    public void deletePlan(long id) {
        planDao.deletePlan(id);
    }

    public void updatePlanNote(String note, String title, long date, long id) {
        planDao.updatePlan(note, title, date, id);
    }

    //planDao end region

    //calendarDao region

    public void insertEvent(CalendarEvent event) {
        calendarDao.insert(event);
    }

    public List<CalendarEvent> getCalendarEvents() {
        return calendarDao.getEvents();
    }

    public List<CalendarEvent> getCurrentDayEvents(String date) {
        return calendarDao.getCurrentDayEvents(date);
    }

    public CalendarEvent getEvent(long id) {
        return calendarDao.getEvent(id);
    }

    public void deleteEvent(long id) {
        calendarDao.deleteEvent(id);
    }

    public void deleteCurrentDayEvents(String date) {
        calendarDao.deleteCurrentDayEvents(date);
    }

    public void updateEvent(String text, long id, long fullDate) {
        calendarDao.updateCalendarEvent(text, id, fullDate);
    }

    //calendarDao end region

    //customerDao region

    public void insertCustomer(Customer customer) {
        customerDao.insert(customer);
    }

    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    public Customer getCustomerByID(long id) {
        return customerDao.getCustomerByID(id);
    }

    public String getCustomerName(long id) {
        return customerDao.getCustomerName(id);
    }

    public long getCustomerID(String name) {
        return customerDao.getCustomerID(name);
    }

    public void deleteCustomer(long id) {
        customerDao.deleteCustomer(id);
    }

    public void updateCustomer(long id, String imageName, String name, String phone, String email) {
        customerDao.updateCustomer(id, imageName, name, phone, email);
    }

    //customerDao end region

    //orderDao region

    public void insertOrder(Order order) {
        orderDao.insert(order);
    }

    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    public Order getOrderByID(long orderID) {
        return orderDao.getOrderByID(orderID);
    }

    public List<Order> getCustomersOrders(long customerID) {
        return orderDao.getCustomersOrders(customerID);
    }

    public void deleteOrder(long orderID) {
        orderDao.deleteOrder(orderID);
    }

    public void updateOrder(long orderID, long customerID, String address, String description,
                     Order.NetworkOwner networkOwner, long deal_date, float capacity,
                     float voltage, int reliability, String attachmentSpot, String meterType,
                     int transformers, Order.State state, Order.OrderType orderType) {
        orderDao.updateOrder(orderID, customerID, address, description, networkOwner, deal_date,
                capacity, voltage, reliability, attachmentSpot, meterType, transformers, state, orderType);
    }

    //orderDao end region
}
