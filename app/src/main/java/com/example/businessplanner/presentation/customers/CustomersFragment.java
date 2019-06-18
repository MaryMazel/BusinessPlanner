package com.example.businessplanner.presentation.customers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;
import com.example.businessplanner.presentation.orders.OrdersFragment;

import java.util.List;

public class CustomersFragment extends Fragment {
    private static final int REQUEST_CODE = 1;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CustomersListAdapter adapter;
    private List<Customer> customers;
    private DatabaseManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new DatabaseManager(requireContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter() {
        customers = manager.getCustomers();
        adapter = new CustomersListAdapter(requireContext(), customers, new CustomersListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Customer customer) {
                Intent intent = new Intent(requireContext(), CustomerProfileActivity.class);
                intent.putExtra("customer_id", customer.id);
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onCustomerClick(Customer customer) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Confirmation")
                        .setMessage("Do you really want to open orders of " + customer.customer_name + "?")
                        .setNegativeButton("No",
                                (dialog, id) -> dialog.cancel())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            List<Order> orders = manager.getCustomersOrders(customer.id);
                            if (orders.size() == 0) {
                                alertNoOrders();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("customer", String.valueOf(customer.id));

                                OrdersFragment ordersFragment = new OrdersFragment();
                                ordersFragment.setArguments(bundle);

                                getChildFragmentManager().beginTransaction().add(R.id.customers_layout, ordersFragment).commit();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customers_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_customers);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        toolbar = view.findViewById(R.id.toolbar_customers);
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_customer);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CustomerProfileActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public void alertNoOrders() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Warning")
                .setMessage("There is no orders of such customer yet")
                .setNegativeButton("OK",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data) {
        super.onActivityResult(requestCode, result, data);
        if (result == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            List<Customer> customers = manager.getCustomers();
            recyclerView.setAdapter(new CustomersListAdapter(requireContext(), customers, new CustomersListAdapter.OnItemClickListener() {
                @Override
                public void onEditClick(Customer customer) {
                    Intent intent = new Intent(requireContext(), CustomerProfileActivity.class);
                    intent.putExtra("customer_id", customer.id);
                    startActivityForResult(intent, REQUEST_CODE);
                }

                @Override
                public void onCustomerClick(Customer customer) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Confirmation")
                            .setMessage("Do you really want to open orders of " + customer.customer_name + "?")
                            .setNegativeButton("No",
                                    (dialog, id) -> dialog.cancel())
                            .setPositiveButton("Yes", (dialog, which) -> {
                                List<Order> orders = manager.getCustomersOrders(customer.id);
                                if (orders.size() == 0) {
                                    alertNoOrders();
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("customer", String.valueOf(customer.id));

                                    OrdersFragment ordersFragment = new OrdersFragment();
                                    ordersFragment.setArguments(bundle);

                                    getChildFragmentManager().beginTransaction().add(R.id.customers_layout, ordersFragment).commit();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }));
        }
        recyclerView.invalidate();
    }
}

