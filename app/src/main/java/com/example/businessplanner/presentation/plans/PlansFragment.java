package com.example.businessplanner.presentation.plans;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;

import java.util.Collections;
import java.util.List;

public class PlansFragment extends Fragment {
    private static final int REQUEST_CODE = 1;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlansListAdapter adapter;
    private List<Plan> plans;
    private DatabaseManager databaseManager;
    private FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(requireContext());

        setAdapter();
    }

    public void setAdapter() {
        plans = databaseManager.getPlans();
        Collections.reverse(plans);
        adapter = new PlansListAdapter(getContext(), plans);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plans_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_plans);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        toolbar = view.findViewById(R.id.toolbar_plans);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.showMenu();
            }
        });

        fab = view.findViewById(R.id.fab_plans);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InputNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data){
        super.onActivityResult(requestCode, result, data);
        getActivity();
        if (result == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            List<Plan> plans = databaseManager.getPlans();
            Collections.reverse(plans);
            recyclerView.setAdapter(new PlansListAdapter(requireContext(), plans));
            recyclerView.invalidate();
        }
    }
}
