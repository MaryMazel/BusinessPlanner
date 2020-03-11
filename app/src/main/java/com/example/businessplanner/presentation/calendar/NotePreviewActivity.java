package com.example.businessplanner.presentation.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.domain.DatabaseManager;

import java.util.Calendar;

public class NotePreviewActivity extends AppCompatActivity {
    private EditText et_note;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_preview_activity);

        manager = new DatabaseManager(this);

        et_note = findViewById(R.id.add_note_cal);
        Intent intent = getIntent();
        if (intent.hasExtra(CalendarFragment.EVENT_ID)) {
            openNote();
        }

        Toolbar toolbar = findViewById(R.id.toolbar_note_preview);
        if (intent.hasExtra(CalendarFragment.CURRENT_DATE)) {
            toolbar.setTitle(intent.getStringExtra(CalendarFragment.CURRENT_DATE));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.add_note_cal_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_note_cal) {
                saveNote();
            }
            return true;
        });
    }

    public void openNote() {
        Long id = getIntent().getLongExtra(CalendarFragment.EVENT_ID, 123456789);
        CalendarEvent event = manager.getEvent(id);
        et_note.setText(event.text);
    }

    public void saveNote() {
        boolean newNote = true;
        String date = "";
        Intent intent = getIntent();
        if (intent.hasExtra(CalendarFragment.EVENT_ID)) {
            newNote = false;
        } else {
            date = intent.getStringExtra(CalendarFragment.CURRENT_DATE);
        }
        long eventID = intent.getLongExtra(CalendarFragment.EVENT_ID, 1000000);
        String note = et_note.getText().toString();
        long today = Calendar.getInstance().getTime().getTime();
        if (note.length() != 0) {
            if (newNote) {
                manager.insertEvent(new CalendarEvent(today, date, note));
                Toast.makeText(this, "Note inserted", Toast.LENGTH_SHORT).show();
            } else {
                manager.updateEvent(note, eventID, today);
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            goBack();
        } else {
            goBack();
        }
    }

    public void goBack() {
        onBackPressed();
    }
}