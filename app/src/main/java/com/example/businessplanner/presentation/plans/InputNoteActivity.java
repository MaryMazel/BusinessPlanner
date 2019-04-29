package com.example.businessplanner.presentation.plans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputNoteActivity extends Activity {
    private Toolbar toolbar;
    private EditText editTextNote;
    private EditText editTextTitle;
    private DatabaseManager manager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan_activity);
        manager = new DatabaseManager(this);

        editTextNote = findViewById(R.id.note_et);
        editTextTitle = findViewById(R.id.title_et);
        Intent intent = getIntent();
        if (intent.hasExtra("plan_id")) {
            openNote();
        }

        toolbar = findViewById(R.id.toolbar_add_plan);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.add_plan_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_note) {
                saveNote();
            }
            return true;
        });
    }

    public void openNote() {
        Long id = getIntent().getLongExtra("plan_id", 123456789);
        Plan plan = manager.getPlan(id);
        editTextNote.setText(plan.note);
        editTextTitle.setText(plan.title);
    }

    public String formatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return format.format(date);
    }

    public void saveNote() {
        boolean newNote = true;

        Intent intent = getIntent();
        if (intent.hasExtra("plan_id")) {
            newNote = false;
        }
        String note = editTextNote.getText().toString();
        String title = editTextTitle.getText().toString().equals("") ? "Title" : editTextTitle.getText().toString();
        if (note.length() != 0) {
            if (newNote) {
                manager.insertPlan(new Plan(title, note, Calendar.getInstance().getTime().getTime()));
            } else {
                long id = getIntent().getLongExtra("plan_id", 123456789);
                manager.updatePlanNote(note, title, Calendar.getInstance().getTime().getTime(), id);
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
