package com.example.businessplanner.presentation.calendar;

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

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private static final int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private CalendarNotesListAdapter adapter;
    private DatabaseManager databaseManager;
    private List<CalendarEvent> events;
    private CalendarView calendarView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(requireContext());
    }

    public void setAdapter(Calendar calendar) {
        events = databaseManager.getCurrentDayEvents(formatDate(calendar));
        Collections.reverse(events);
        adapter = new CalendarNotesListAdapter(requireContext(), events, event -> {
            Intent intent = new Intent(requireContext(), NotePreviewActivity.class);
            intent.putExtra("event_id", event.id);
            startActivityForResult(intent, REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);

        calendarView = view.findViewById(R.id.calendar_view);

        Calendar min = Calendar.getInstance();
        min.set(2019, 1, 1);
        calendarView.setMinimumDate(min);

        calendarView.setOnDayClickListener(eventDay -> {
            setAdapter(eventDay.getCalendar());
            adapter.notifyDataSetChanged();
        });

        recyclerView = view.findViewById(R.id.recycler_view_calendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_calendar);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NotePreviewActivity.class);
            intent.putExtra("new note", formatDate(calendarView.getFirstSelectedDate()));
            startActivityForResult(intent, REQUEST_CODE);
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_calendar);
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });

        return view;
    }

    public String formatDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy", Locale.UK);
        return dateFormat.format(date.getTime());
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data) {
        super.onActivityResult(requestCode, result, data);
        getActivity();
        if (result == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            setAdapter(calendarView.getFirstSelectedDate());
            recyclerView.invalidate();
        }
    }
}
