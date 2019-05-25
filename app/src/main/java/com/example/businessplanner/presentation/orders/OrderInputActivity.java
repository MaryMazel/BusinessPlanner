package com.example.businessplanner.presentation.orders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderInputActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private Spinner customerSpinner;
    private Spinner networkOwnerSpinner;
    private Spinner orderTypeSpinner;
    private Spinner stateSpinner;

    private EditText transformersET;

    private TextInputLayout addressTIL;
    private TextInputLayout descriptionTIL;
    private TextInputLayout capacityTIL;
    private TextInputLayout voltageTIL;
    private TextInputLayout attachmentSpotTIL;
    private TextInputLayout meterTypeTIL;

    private RadioButton firstReliability;
    private RadioButton secondReliability;
    private RadioButton thirdReliability;

    private TextView dealDateTV;

    private ImageView datePickerIV;

    private DatabaseManager manager;

    int day;
    int month;
    int year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_input_activity);

        manager = new DatabaseManager(this);

        Calendar today = Calendar.getInstance();
        day = today.get(Calendar.DAY_OF_MONTH);
        month = today.get(Calendar.MONTH);
        year = today.get(Calendar.YEAR);

        setToolbar();

        setCustomerSpinner("");
        setNetworkOwnerSpinner();
        setOrderTypeSpinner();
        setStateSpinner();

        transformersET = findViewById(R.id.order_profile_transformers);
        dealDateTV = findViewById(R.id.profile_deal_date);

        datePickerIV = findViewById(R.id.date_picker_dialog);
        datePickerIV.setOnClickListener(v -> openDatePicker());

        firstReliability = findViewById(R.id.rb_1);
        secondReliability = findViewById(R.id.rb_2);
        thirdReliability = findViewById(R.id.rb_3);

        addressTIL = findViewById(R.id.order_profile_address);
        descriptionTIL = findViewById(R.id.order_profile_description);
        capacityTIL = findViewById(R.id.order_profile_capacity);
        voltageTIL = findViewById(R.id.order_profile_voltage);
        attachmentSpotTIL = findViewById(R.id.order_profile_attachment_spot);
        meterTypeTIL = findViewById(R.id.order_profile_meter_type);

        Intent intent = getIntent();
        if (intent.hasExtra("order_id")) {
            openOrder();
        }
    }

    private void openOrder() {
        Long id = getIntent().getLongExtra("order_id", 123456789);
        Order order = manager.getOrderByID(id);

        String customerName = manager.getCustomerName(order.customerID);
        setCustomerSpinner(customerName);

        addressTIL.getEditText().setText(order.address);

        if (!order.description.equals("0")) {
            descriptionTIL.getEditText().setText(order.description);
        }

        networkOwnerSpinner.setSelection(order.networkOwner.getCode());

        if (!(order.deal_date == 0)) {
            dealDateTV.setText(formatDate(order.deal_date));
        }

        if (!(order.capacity == 0)) {
            capacityTIL.getEditText().setText(String.valueOf(order.capacity));
        }

        if (!(order.voltage == 0)) {
            voltageTIL.getEditText().setText(String.valueOf(order.voltage));
        }

        switch (order.reliability) {
            case 0:
                firstReliability.setChecked(false);
                secondReliability.setChecked(false);
                thirdReliability.setChecked(false);
                break;
            case 1:
                firstReliability.setChecked(true);
                break;
            case 2:
                secondReliability.setChecked(true);
                break;
            case 3:
                thirdReliability.setChecked(true);
                break;
        }

        if (!order.attachmentSpot.equals("0")) {
            attachmentSpotTIL.getEditText().setText(order.attachmentSpot);
        }

        if (!order.meterType.equals("0")) {
            meterTypeTIL.getEditText().setText(order.meterType);
        }

        if (!(order.transformers == 0)) {
            transformersET.setText(String.valueOf(order.transformers));
        }

        stateSpinner.setSelection(order.state.getCode());

        orderTypeSpinner.setSelection(order.orderType.getCode());
    }

    private void saveOrder() {
        String name = customerSpinner.getSelectedItem().toString();

        String address;
        if (!addressTIL.getEditText().getText().toString().equals("")) {
            address = addressTIL.getEditText().getText().toString();
        } else {
            addressTIL.setErrorEnabled(true);
            addressTIL.setError("Field can`t be empty");
            return;
        }

        String description = "0";
        if (!descriptionTIL.getEditText().getText().toString().equals("")) {
            description = descriptionTIL.getEditText().getText().toString();
        }

        Order.NetworkOwner networkOwner = Order.NetworkOwner.Other;
        switch (networkOwnerSpinner.getSelectedItem().toString()) {
            case "Kharkovoblenergo":
                networkOwner = Order.NetworkOwner.Kharkovoblenergo;
                break;
            case "Railway":
                networkOwner = Order.NetworkOwner.Railway;
                break;
            case "Zhylkomservice":
                networkOwner = Order.NetworkOwner.Zhylkomservice;
                break;
            case "OSBB":
                networkOwner = Order.NetworkOwner.OSBB;
                break;
            case "Other":
                networkOwner = Order.NetworkOwner.Other;
                break;
        }

        Date date = Calendar.getInstance().getTime();
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).parse(dealDateTV.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dealDate = dealDateTV.getText() == "" ? 0 : date.getTime();

        float capacity = 0;
        if (!capacityTIL.getEditText().getText().toString().equals("")) {
            try {
                capacity = Float.parseFloat(capacityTIL.getEditText().getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        float voltage = 0;
        if (!voltageTIL.getEditText().getText().toString().equals("")) {
            try {
                voltage = Float.parseFloat(voltageTIL.getEditText().getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        int reliability;
        if (firstReliability.isChecked()) {
            reliability = 1;
        } else if (secondReliability.isChecked()) {
            reliability = 2;
        } else if (thirdReliability.isChecked()) {
            reliability = 3;
        } else {
            reliability = 0;
        }

        String attachmentSpot = "0";
        if (!attachmentSpotTIL.getEditText().getText().toString().equals("")) {
            attachmentSpot = attachmentSpotTIL.getEditText().getText().toString();
        }

        String meterType = "0";
        if (!meterTypeTIL.getEditText().getText().toString().equals("")) {
            meterType = meterTypeTIL.getEditText().getText().toString();
        }

        int transformers = 0;
        try {
            transformers = Integer.parseInt(transformersET.getText().toString());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }


        Order.State state = Order.State.ANALISE;
        switch (stateSpinner.getSelectedItem().toString()) {
            case "ANALISE":
                state = Order.State.ANALISE;
                break;
            case "IN_PROGRESS":
                state = Order.State.IN_PROGRESS;
                break;
            case "CLOSED":
                state = Order.State.CLOSED;
                break;
        }

        Order.OrderType orderType = Order.OrderType.ContractRenewal;
        switch (orderTypeSpinner.getSelectedItem().toString()) {
            case "ContractRenewal":
                orderType = Order.OrderType.ContractRenewal;
                break;
            case "ElectricHeating":
                orderType = Order.OrderType.ElectricHeating;
                break;
            case "GreenRate":
                orderType = Order.OrderType.GreenRate;
                break;
            case "TechConditions":
                orderType = Order.OrderType.TechConditions;
                break;
            case "InnerNetworkProject":
                orderType = Order.OrderType.InnerNetworkProject;
                break;
            case "OuterNetworkProject":
                orderType = Order.OrderType.OuterNetworkProject;
                break;
        }

        Intent intent = getIntent();
        if (!intent.hasExtra("order_id")) {
            Order order = new Order(manager.getCustomerID(name), address,
                    description, networkOwner, dealDate, capacity, voltage, reliability,
                    attachmentSpot, meterType, transformers, state, orderType);

            manager.insertOrder(order);
            Toast.makeText(this, "Order inserted", Toast.LENGTH_SHORT).show();
        } else {
            manager.updateOrder(intent.getLongExtra("order_id", 123456789), manager.getCustomerID(name), address, description,
                    networkOwner, dealDate, capacity, voltage, reliability, attachmentSpot, meterType, transformers, state, orderType);
            Toast.makeText(this, "Order updated", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        setResult(Activity.RESULT_OK, data);
        onBackPressed();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_order_input);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.customer_activity_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.save_order:
                    saveOrder();
                    break;
                case R.id.delete_order:
                    //to do
                    break;
            }
            return true;
        });
    }

    private void setCustomerSpinner(String name) {
        customerSpinner = findViewById(R.id.customer_spinner);

        List<String> data = new ArrayList<>();

        List<Customer> customers = manager.getCustomers();
        for (Customer customer : customers) {
            data.add(customer.customer_name);
        }

        if (data.size() == 0) {
            data.add("No customers yet. Insert a customer");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customerSpinner.setAdapter(adapter);
        for (int i = 0; i < customers.size(); i++) {
            if (!name.equals("") && name.equals(customers.get(i).customer_name)) {
                customerSpinner.setSelection(i);
            }
        }
        customerSpinner.setPrompt("Customer");
    }

    private void setStateSpinner() {
        stateSpinner = findViewById(R.id.state_spinner);

        List<String> data = new ArrayList<>();
        data.add(Order.State.ANALISE.toString());
        data.add(Order.State.IN_PROGRESS.toString());
        data.add(Order.State.CLOSED.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(adapter);
        stateSpinner.setPrompt("State");
    }

    private void setNetworkOwnerSpinner() {
        networkOwnerSpinner = findViewById(R.id.network_owner_spinner);

        List<String> data = new ArrayList<>();
        data.add(Order.NetworkOwner.Kharkovoblenergo.toString());
        data.add(Order.NetworkOwner.Railway.toString());
        data.add(Order.NetworkOwner.Zhylkomservice.toString());
        data.add(Order.NetworkOwner.OSBB.toString());
        data.add(Order.NetworkOwner.Other.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        networkOwnerSpinner.setAdapter(adapter);
        networkOwnerSpinner.setPrompt("Network Owner");
    }

    private void setOrderTypeSpinner() {
        orderTypeSpinner = findViewById(R.id.order_type_spinner);

        List<String> data = new ArrayList<>();
        data.add(Order.OrderType.ContractRenewal.toString());
        data.add(Order.OrderType.ElectricHeating.toString());
        data.add(Order.OrderType.GreenRate.toString());
        data.add(Order.OrderType.TechConditions.toString());
        data.add(Order.OrderType.InnerNetworkProject.toString());
        data.add(Order.OrderType.OuterNetworkProject.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        orderTypeSpinner.setAdapter(adapter);
        orderTypeSpinner.setPrompt("Order Type");
    }

    private void openDatePicker() {
        new DatePickerDialog(this, datePickerListener, year, month, day).show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);
            dealDateTV.setText(formatDate(c.getTime().getTime()));
        }
    };

    public String formatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return format.format(date);
    }
}
