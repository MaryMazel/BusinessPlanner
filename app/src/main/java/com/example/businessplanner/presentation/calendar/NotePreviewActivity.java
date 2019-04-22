package com.example.businessplanner.presentation.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.example.businessplanner.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotePreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_preview_activity);

        Intent intent = getIntent();

        TextView note = findViewById(R.id.note);

        Toolbar toolbar = findViewById(R.id.toolbar_note_preview);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (intent != null) {
            Object event = intent.getParcelableExtra(CalendarFragment.EVENT);

            if (event instanceof MyEventDay) {
                MyEventDay myEventDay = (MyEventDay) event;

                toolbar.setTitle(getFormattedDate(myEventDay.getCalendar().getTime()));
                note.setText(myEventDay.getNote());

                return;
            }

            if (event instanceof EventDay) {
                EventDay eventDay = (EventDay) event;
                toolbar.setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.UK);
        return simpleDateFormat.format(date);
    }
}