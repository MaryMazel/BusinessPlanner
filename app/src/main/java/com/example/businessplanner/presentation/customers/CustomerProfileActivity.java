package com.example.businessplanner.presentation.customers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.utils.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    private Uri customerImageURI;
    private long selectedDate;

    private DatabaseManager manager;

    int day = 1;
    int month = 0;
    int year = 2019;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile_activity);

        manager = new DatabaseManager(this, orderDao);

        setToolbar();

        imageCustomer = findViewById(R.id.profile_image);
        inputName = findViewById(R.id.profile_name);
        inputPhone = findViewById(R.id.profile_phone);
        inputEmail = findViewById(R.id.profile_email);
        inputProfit = findViewById(R.id.profile_profit);
        inputAddress = findViewById(R.id.profile_address);

        setSpinner();

        dealDate = findViewById(R.id.profile_deal_date);
        datePickerButton = findViewById(R.id.date_picker_dialog);
        datePickerButton.setOnClickListener(v -> openDatePicker());

        FloatingActionButton fab = findViewById(R.id.fab_profile);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "select a picture"), 10);
        });

        Intent intent = getIntent();
        if (intent.hasExtra("customer_id")) {
            openNote();
        }
    }

    private void openNote() {
        Long id = getIntent().getLongExtra("customer_id", 123456789);
        Customer customer = manager.getCustomerByID(id);
        if (!customer.imageName.equals("0")) {

            Glide.with(this).
                    load(customer.imageName).
                    into(imageCustomer);
            Toast.makeText(this, "Image loaded", Toast.LENGTH_SHORT).show();

            customerImageURI = Uri.parse(customer.imageName);
        }
        inputName.getEditText().setText(customer.customer_name);
        inputPhone.getEditText().setText(customer.phone);

        if (customer.email.equals("0")) {
            inputEmail.getEditText().setText("");
        } else {
            inputEmail.getEditText().setText(customer.email);
        }

        if (customer.address.equals("0")) {
            inputAddress.getEditText().setText("");
        } else {
            inputAddress.getEditText().setText(customer.address);
        }

        if (customer.profit == 0) {
            inputProfit.getEditText().setText("");
        } else {
            inputEmail.getEditText().setText(customer.email);
        }

        if (customer.deal_date == 0) {
            dealDate.setText("");
        }

        stateSpinner.setSelection(customer.state.getCode());
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_customers_profile);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.save_profile_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.save_profile:
                    saveProfile();
                    break;
                case R.id.delete_profile:
                    //to do
                    break;
            }
            return true;
        });
    }

    private void saveImageAsFile(String fileName, ImageView iv) {
        FileOutputStream outStream = null;

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath());

            File outFile = new File(dir, fileName);

            outStream = new FileOutputStream(outFile);
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProfile() {
        String name = inputName.getEditText().getText().toString();
        String phone = inputPhone.getEditText().getText().toString();
        String email = inputEmail.getEditText().getText().toString();
        String address = inputAddress.getEditText().getText().toString();
        String profitString = inputProfit.getEditText().getText().toString();

        long dealDateValue = selectedDate;
        Customer.State state = null;
        switch (stateSpinner.getSelectedItem().toString()) {
            case "ANALISE":
                state = Customer.State.ANALISE;
                break;
            case "IN_PROGRESS":
                state = Customer.State.IN_PROGRESS;
                break;
            case "CLOSED":
                state = Customer.State.CLOSED;
                break;
        }

        if (Validator.validateFields(name, inputName, phone, inputPhone, email, inputEmail, profitString, inputProfit)) {
            if (email.equals("")) {
                email = "0";
            }
            if (address.equals("")) {
                address = "0";
            }
            if (profitString.equals("")) {
                profitString = "0";
            }

            String imageName;
            if (customerImageURI != null) {
                @SuppressLint("DefaultLocale")
                String fileName = String.format("%d.png", System.currentTimeMillis());
                saveImageAsFile(fileName, imageCustomer);
                imageName = fileName;
            } else {
                imageName = "0";
            }

            long profit = Validator.validateProfit(profitString);
            Intent intent = getIntent();
            if (intent.hasExtra("customer_id")) {
                Long id = getIntent().getLongExtra("customer_id", 123456789);
                manager.updateCustomer(id, imageName, name, phone, email, address, dealDateValue, profit, state);
                Toast.makeText(this, "Customer updated", Toast.LENGTH_SHORT).show();
            } else {
                manager.insertCustomer(new Customer(imageName, name, phone, address, email, profit, dealDateValue, state));
                Toast.makeText(this, "Customer inserted", Toast.LENGTH_SHORT).show();
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            onBackPressed();
        }
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
            selectedDate = c.getTimeInMillis();
            dealDate.setText(formatDate(selectedDate));
        }
    };

    public String formatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return format.format(date);
    }

    private void setSpinner() {
        stateSpinner = findViewById(R.id.state_spinner);

        List<String> data = new ArrayList<>();
        data.add(Customer.State.ANALISE.toString());
        data.add(Customer.State.IN_PROGRESS.toString());
        data.add(Customer.State.CLOSED.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(adapter);
        stateSpinner.setPrompt("State");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            if (data != null) {
                customerImageURI = data.getData();
                imageCustomer.setImageURI(customerImageURI);
            }
        }
    }
}
