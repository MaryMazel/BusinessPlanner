package com.example.businessplanner.presentation.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.domain.DatabaseManager;
import com.example.businessplanner.presentation.MainActivity;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    public static final String EVENT_ID = "event_id";
    public static final String CURRENT_DATE = "current_date";

    private RecyclerView recyclerView;
    private CalendarNotesListAdapter adapter;
    private DatabaseManager databaseManager;
    private List<CalendarEvent> events;
    private MaterialCalendarView calendarView;

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
            intent.putExtra(EVENT_ID, event.id);
            intent.putExtra(CURRENT_DATE, formatDate(calendarView.getSelectedDate().getCalendar()));
            startActivityForResult(intent, REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);

        calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setCurrentDate(Calendar.getInstance().getTime());
        calendarView.setSelectedDate(Calendar.getInstance().getTime());
        setCalendar();

        recyclerView = view.findViewById(R.id.recycler_view_calendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_calendar);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NotePreviewActivity.class);
            intent.putExtra(CURRENT_DATE, formatDate(calendarView.getSelectedDate().getCalendar()));
            startActivityForResult(intent, REQUEST_CODE);
            calendarView.invalidateDecorators();
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_calendar);
        toolbar.setNavigationOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showMenu();
        });
        toolbar.inflateMenu(R.menu.calendar_picker_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.pickDateItem) {
                openDatePicker();
            }
            return true;
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCalendar();
    }

    public void setCalendar() {
        addDecorators();
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            setAdapter(date.getCalendar());
        });
    }

    public void addDecorators() {
        calendarView.addDecorator(new EventDecorator(getResources().getColor(R.color.colorAccent), getSelectedDates()));
        calendarView.addDecorator(new TodayDecorator());
    }

    public void openDatePicker() {
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                R.style.CustomDatePickerDialogTheme,
                (view, year1, month1, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year1, month1, dayOfMonth);
                    calendarView.setCurrentDate(cal);
                    calendarView.setSelectedDate(cal);
                }, year,
                month,
                day);
        dialog.show();
    }

    public List<Calendar> getSelectedDates() {
        List<Calendar> dates = new ArrayList<>();
        List<CalendarEvent> calendarEvents = databaseManager.getCalendarEvents();
        for (CalendarEvent calendarEvent : calendarEvents) {
            SimpleDateFormat format = new SimpleDateFormat("EEE dd MMM yyyy", Locale.UK);
            Date date;
            try {
                date = format.parse(calendarEvent.date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                dates.add(cal);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
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
            setAdapter(calendarView.getSelectedDate().getCalendar());
            recyclerView.invalidate();
            addDecorators();
        }
    }
}
