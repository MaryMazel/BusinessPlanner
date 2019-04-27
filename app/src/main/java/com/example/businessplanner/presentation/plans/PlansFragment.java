package com.example.businessplanner.presentation.plans;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;
import com.example.businessplanner.presentation.utils.PrintBitmapBuilder;

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
        setHasOptionsMenu(true);
        databaseManager = new DatabaseManager(requireContext());
        setAdapter();
    }

    public void setAdapter() {
        plans = databaseManager.getPlans();
        Collections.reverse(plans);
        adapter = new PlansListAdapter(getContext(), plans, plan -> {
            Intent intent = new Intent(requireContext(), InputNoteActivity.class);
            intent.putExtra("plan_id", plan.id);
            startActivityForResult(intent, REQUEST_CODE);
        });
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
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });

        processMenuItems(toolbar);

        fab = view.findViewById(R.id.fab_plans);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InputNoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        return view;
    }

    private void processMenuItems(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.plans_search);

        MenuItem actionSearch = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) actionSearch.getActionView();

        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSubmit(s);
                return false;
            }
        });

        MenuItem actionDownload = toolbar.getMenu().findItem(R.id.action_download);
        actionDownload.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_download) {
                downloadPlansAsFiles();
            }
            return true;
        });
    }

    private void alertNoPlans() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("There are no plans to download")
                .setTitle("Warning")
                .setCancelable(false)
                .setPositiveButton("Ok", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void downloadPlansAsFiles() {
        Context context = requireContext();

        List<Plan> plansList = databaseManager.getPlans();
        if (plansList == null) {
            alertNoPlans();
            return;
        }

        PrintBitmapBuilder builder = new PrintBitmapBuilder(context);
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (Plan plan : plansList) {
            sb.append(i).append(". ").append(plan.title).append("\n\t").append(plan.note).append("\n\n");
            if (i != plansList.size()) {
                sb.append("---------------------------\n\n");
            }
            i++;
        }

        builder.setTextAlign(PrintBitmapBuilder.ReceiptTextAlign.LEFT);
        builder.appendString(sb.toString());

        PrintHelper printHelper = new PrintHelper(context);
        printHelper.printBitmap("Print", builder.build());
    }

    public void textSubmit(String s) {
        List<Plan> allPlans = databaseManager.getPlans();
        Collections.reverse(allPlans);
        plans.clear();
        for (Plan plan : allPlans) {
            if (plan.title.toLowerCase().contains(s.toLowerCase()) || plan.note.toLowerCase().contains(s.toLowerCase())) {
                plans.add(plan);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data) {
        super.onActivityResult(requestCode, result, data);
        getActivity();
        if (result == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            List<Plan> plans = databaseManager.getPlans();
            Collections.reverse(plans);
            recyclerView.setAdapter(new PlansListAdapter(requireContext(), plans, new PlansListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Plan plan) {
                    Intent intent = new Intent(requireContext(), InputNoteActivity.class);
                    intent.putExtra("plan_id", plan.id);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }));
            recyclerView.invalidate();
        }
    }
}
