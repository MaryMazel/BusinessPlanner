package com.example.businessplanner.presentation.customers;

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
import android.widget.Toast;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomersFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CustomersListAdapter adapter;
    private List<Customer> customers;
    private DatabaseManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new DatabaseManager(requireContext());
        setAdapter();
    }

    private void setAdapter() {
        // customers = manager.getCustomers();
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("My Name is", "+6468455", "Науки 21а 123", "marymazel@outlook.com", 200000, 1556496000, Customer.State.ANALISE));
        customers.add(new Customer("My Name Masha", "+6468487845", "Науки 21а 123", "marymazel@outlook.com", 200000, 1556496000, Customer.State.IN_PROGRESS));
        customers.add(new Customer("My Name", "+64684515845", "Науки 21а 123", "marymazel@outlook.com", 200000, 1556496000, Customer.State.ANALISE));
        customers.add(new Customer("My Name", "+646845", "Науки 21а 123", "marymazel@outlook.com", 200000, 1556496000, Customer.State.CLOSED));
        adapter = new CustomersListAdapter(requireContext(), customers, customer -> {
            //todo
            Toast.makeText(requireContext(), customer.customer_name, Toast.LENGTH_SHORT).show();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customers_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_customers);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
}
