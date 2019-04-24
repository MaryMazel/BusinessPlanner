package com.example.businessplanner.presentation.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotePreviewActivity extends AppCompatActivity {
    private EditText et_note;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_preview_activity);

        manager = new DatabaseManager(this);

        et_note = findViewById(R.id.add_note_cal);

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

    public void saveNote() {
        String note = et_note.getText().toString();

        if (note.length() != 0) {
            long today = Calendar.getInstance().getTime().getTime();
            manager.insertEvent(new CalendarEvent(today, formatDate(today), note));
        }
        /*if (note.length() != 0) {
            if (newNote) {
                manager.insertPlan(new Plan(title, note, Calendar.getInstance().getTime().getTime()));
            } else {
                long id = getIntent().getLongExtra("plan_id", 123456789);
                manager.updatePlanNote(note, title, Calendar.getInstance().getTime().getTime(), id);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            goBack();
        } else {}*/
        goBack();
    }

    public void goBack() {
        onBackPressed();
    }

    public String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return dateFormat.format(date);
    }
}