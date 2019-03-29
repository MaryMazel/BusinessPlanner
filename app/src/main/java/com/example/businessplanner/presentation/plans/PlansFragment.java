package com.example.businessplanner.presentation.plans;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;

import java.util.List;

public class PlansFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlansListAdapter adapter;
    private List<Plan> plans;
    private DatabaseManager databaseManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(requireContext());

        setAdapter();
    }

    public void setAdapter() {
        plans = databaseManager.getPlans();
        adapter = new PlansListAdapter(getContext());
        adapter.setItems(plans);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plans_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_plans);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        toolbar = view.findViewById(R.id.toolbar_plans);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.showMenu();
            }
        });
        return view;
    }
}
