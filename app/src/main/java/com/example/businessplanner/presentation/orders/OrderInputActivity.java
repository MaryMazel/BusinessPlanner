package com.example.businessplanner.presentation.orders;

import android.app.DatePickerDialog;
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

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    private TextView dealDate;

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

        setCustomerSpinner();
        setNetworkOwnerSpinner();
        setOrderTypeSpinner();
        setStateSpinner();

        transformersET = findViewById(R.id.order_profile_transformers);
        dealDate = findViewById(R.id.profile_deal_date);

        datePickerIV = findViewById(R.id.date_picker_dialog);
        datePickerIV.setOnClickListener(v -> openDatePicker());

        firstReliability = findViewById(R.id.rb_1);
        firstReliability.setChecked(true);
        secondReliability = findViewById(R.id.rb_2);
        thirdReliability = findViewById(R.id.rb_3);

        addressTIL = findViewById(R.id.order_profile_address);
        descriptionTIL = findViewById(R.id.order_profile_description);
        capacityTIL = findViewById(R.id.order_profile_capacity);
        voltageTIL = findViewById(R.id.order_profile_voltage);
        attachmentSpotTIL = findViewById(R.id.order_profile_attachment_spot);
        meterTypeTIL = findViewById(R.id.order_profile_meter_type);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_order_input);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setCustomerSpinner() {
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
        customerSpinner.setPrompt("State");
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
            dealDate.setText(formatDate(c.getTime().getTime()));
        }
    };

    public String formatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return format.format(date);
    }
}
