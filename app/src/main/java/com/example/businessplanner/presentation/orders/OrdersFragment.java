package com.example.businessplanner.presentation.orders;

import android.app.Activity;
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
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;

import java.util.List;

public class OrdersFragment extends Fragment {
    private static final int REQUEST_CODE = 1;

    private List<Order> orders;
    private DatabaseManager manager;
    private RecyclerView recyclerView;
    private OrdersListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new DatabaseManager(requireContext());
        setAdapter();
    }

    private void setAdapter() {
        if (getArguments() != null) {
            long customerID = Long.parseLong(getArguments().getString("customer"));
            orders = manager.getCustomersOrders(customerID);
        } else {
            orders = manager.getOrders();
        }
        adapter = new OrdersListAdapter(requireContext(), orders, order -> {
            Intent intent = new Intent(requireContext(), OrderInputActivity.class);
            intent.putExtra("order_id", order.id);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toolbar toolbar = view.findViewById(R.id.toolbar_orders);
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_order);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), OrderInputActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data) {
        super.onActivityResult(requestCode, result, data);
        if (result == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            List<Order> orders = manager.getOrders();
            recyclerView.setAdapter(new OrdersListAdapter(requireContext(), orders, order -> {
                Intent intent = new Intent(requireContext(), OrderInputActivity.class);
                intent.putExtra("order_id", order.id);
                startActivityForResult(intent, REQUEST_CODE);
            }));
        }
        recyclerView.invalidate();
    }
}
