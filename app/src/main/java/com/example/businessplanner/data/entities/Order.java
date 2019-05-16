package com.example.businessplanner.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.businessplanner.data.entities.TypeConverters.NetworkOwnerConverter;
import com.example.businessplanner.data.entities.TypeConverters.OrderTypeConverter;
import com.example.businessplanner.data.entities.TypeConverters.StateConverter;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long customerID;

    public String address;

    public String description;

    @TypeConverters(NetworkOwnerConverter.class)
    public NetworkOwner networkOwner;

    public long deal_date;

    public Order(long customerID, String address, String description,
                 NetworkOwner networkOwner, long deal_date, float capacity,
                 float voltage, int reliability, String attachmentSpot, String meterType,
                 int transformers, State state, OrderType orderType) {
        this.customerID = customerID;
        this.address = address;
        this.description = description;
        this.networkOwner = networkOwner;
        this.deal_date = deal_date;
        this.capacity = capacity;
        this.voltage = voltage;
        this.reliability = reliability;
        this.attachmentSpot = attachmentSpot;
        this.meterType = meterType;
        this.transformers = transformers;
        this.state = state;
        this.orderType = orderType;
    }

    public float capacity;

    public float voltage;

    public int reliability;

    public String attachmentSpot;

    public String meterType;

    public int transformers;

    @TypeConverters(StateConverter.class)
    public Order.State state;

    @TypeConverters(OrderTypeConverter.class)
    public Order.OrderType orderType;

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

    public enum NetworkOwner {
        Kharkovoblenergo(0),
        Railway(1),
        Zhylkomservice(2),
        OSBB(3),
        Other(4);

        private int code;

        NetworkOwner(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderType {
        ContractRenewal(0),
        ElectricHeating(1),
        GreenRate(2),
        TechConditions(3),
        InnerNetworkProject(4),
        OuterNetworkProject(5);

        private int code;

        OrderType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
