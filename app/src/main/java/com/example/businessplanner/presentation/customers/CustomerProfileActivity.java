package com.example.businessplanner.presentation.customers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.utils.Validator;

public class CustomerProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ImageView imageCustomer;
    private TextInputLayout inputName;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPhone;
    private DatabaseManager manager;

    int pictureCustomer = R.drawable.round_shape_with_gradient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile_activity);

        manager = new DatabaseManager(this);

        setToolbar();

        imageCustomer = findViewById(R.id.profile_image);
        inputName = findViewById(R.id.profile_name);
        inputPhone = findViewById(R.id.profile_phone);
        inputEmail = findViewById(R.id.profile_email);

        FloatingActionButton fab = findViewById(R.id.fab_profile);
        fab.setOnClickListener(v -> choosePicture());

        Intent intent = getIntent();
        if (intent.hasExtra("customer_id")) {
            openCustomer();
        }
    }

    public void choosePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose logo")
                .setItems(R.array.customer_logos, (dialog, which) -> {
                    int picture = R.drawable.round_shape_with_gradient;
                    switch (which) {
                        case 0:
                            picture = R.drawable.kharkovoblenergo;
                            break;
                        case 1:
                            picture = R.drawable.kievoblenergo;
                            break;
                        case 2:
                            picture = R.drawable.khersonoblenergo;
                            break;
                        case 3:
                            picture = R.drawable.zhylkomservice;
                            break;
                        case 4:
                            picture = R.drawable.osbb;
                            break;
                        case 5:
                            picture = R.drawable.factory;
                            break;
                    }

                    Glide.with(getApplicationContext())
                            .load(picture)
                            .apply(RequestOptions.circleCropTransform())
                            .into(imageCustomer);

                    pictureCustomer = picture;
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void openCustomer() {
        Long id = getIntent().getLongExtra("customer_id", 123456789);
        Customer customer = manager.getCustomerByID(id);
        if (!customer.imageName.equals("no picture")) {
            Glide.with(this)
                    .load(Integer.parseInt(customer.imageName))
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageCustomer);

            pictureCustomer = Integer.parseInt(customer.imageName);
            Toast.makeText(this, "Image loaded", Toast.LENGTH_SHORT).show();
        }

        inputName.getEditText().setText(customer.customer_name);
        inputPhone.getEditText().setText(customer.phone);

        if (customer.email.equals("0")) {
            inputEmail.getEditText().setText("");
        } else {
            inputEmail.getEditText().setText(customer.email);
        }
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

    private void saveProfile() {
        String name = inputName.getEditText().getText().toString();
        String phone = inputPhone.getEditText().getText().toString();
        String email = inputEmail.getEditText().getText().toString();

        if (Validator.validateFields(name, inputName, phone, inputPhone, email, inputEmail)) {
            if (email.equals("")) {
                email = "0";
            }

            String imageName;
            if (imageCustomer.getDrawable() != null) {
                int picture = pictureCustomer;
                imageName = String.valueOf(picture);
            } else {
                imageName = "no picture";
            }

            Intent intent = getIntent();
            if (intent.hasExtra("customer_id")) {
                Long id = getIntent().getLongExtra("customer_id", 123456789);
                manager.updateCustomer(id, imageName, name, phone, email);
                Toast.makeText(this, "Customer updated", Toast.LENGTH_SHORT).show();
            } else {
                manager.insertCustomer(new Customer(imageName, name, phone, email));
                Toast.makeText(this, "Customer inserted", Toast.LENGTH_SHORT).show();
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            onBackPressed();
        }
    }
}
