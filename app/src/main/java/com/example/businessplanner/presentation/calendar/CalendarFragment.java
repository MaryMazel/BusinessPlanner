package com.example.businessplanner.presentation.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.businessplanner.R;
import com.example.businessplanner.presentation.MainActivity;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendar_view);

        Calendar min = Calendar.getInstance();
        min.set(2019, 1, 1);
        calendarView.setMinimumDate(min);

        Calendar max = Calendar.getInstance();
        calendarView.setMaximumDate(max);

        FloatingActionButton fab = view.findViewById(R.id.fab_calendar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NotePreviewActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_calendar);
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });

        return view;
    }
}
