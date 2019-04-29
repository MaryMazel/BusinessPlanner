package com.example.businessplanner.presentation.customers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.businessplanner.R;

import java.util.Calendar;

public class CustomerProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageCustomer;
    private TextInputLayout inputName;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPhone;
    private TextInputLayout inputAddress;
    private TextInputLayout inputProfit;
    private Spinner stateSpinner;
    private ImageView datePickerButton;
    private TextView dealDate;

    private Calendar dealDateCalendar;

    int day = 1;
    int month = 0;
    int year = 2019;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile_activity);

        toolbar = findViewById(R.id.toolbar_customers_profile);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.save_profile_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.save_profile:
                    //to do
                    break;
                case R.id.delete_profile:
                    //to do
                    break;
            }
            return true;
        });

        imageCustomer = findViewById(R.id.profile_image);
        inputName = findViewById(R.id.profile_name);
        inputPhone = findViewById(R.id.profile_phone);
        inputEmail = findViewById(R.id.profile_email);
        inputProfit = findViewById(R.id.profile_profit);
        inputAddress = findViewById(R.id.profile_address);
        stateSpinner = findViewById(R.id.state_spinner);

        dealDate = findViewById(R.id.profile_deal_date);
        datePickerButton = findViewById(R.id.date_picker_dialog);
        datePickerButton.setOnClickListener(v -> openDatePicker());

        FloatingActionButton fab = findViewById(R.id.fab_profile);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "select a picture"), 1);
        });
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
            dealDate.setText(new StringBuilder().append(selectedDay).append("-").append(selectedMonth + 1).append("-").append(selectedYear).toString());
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if (data != null) {
                Uri selectedImage = data.getData();
                imageCustomer.setImageURI(selectedImage);
            }
        }
    }
}
