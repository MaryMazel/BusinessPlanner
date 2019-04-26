package com.example.businessplanner.presentation.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
        if (intent.hasExtra("event_id")) {
            openNote();
        }


        Toolbar toolbar = findViewById(R.id.toolbar_note_preview);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.add_note_cal_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.save_note_cal) {
                    saveNote();
                }
                return true;
            }
        });
    }

    public void openNote() {
        Long id = getIntent().getLongExtra("event_id", 123456789);
        CalendarEvent event = manager.getEvent(id);
        et_note.setText(event.text);
    }

    public void saveNote() {
        boolean newNote = true;
        String date = "";
        Intent intent = getIntent();
        if (intent.hasExtra("event_id")) {
            newNote = false;
        } else {
            date = intent.getStringExtra("new note");
        }
        long eventID = intent.getLongExtra("event_id", 1000000);
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