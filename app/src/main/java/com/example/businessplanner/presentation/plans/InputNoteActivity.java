package com.example.businessplanner.presentation.plans;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputNoteActivity extends Activity {
    private Toolbar toolbar;
    private EditText editText;
    private DatabaseManager manager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan_activity);
        manager = new DatabaseManager(this);

        editText = findViewById(R.id.note_et);

        toolbar = findViewById(R.id.toolbar_add_plan);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.add_plan_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save_note:
                        saveNote();
                        break;
                }
                return true;
            }
        });
    }

    public void saveNote() {
        String note = editText.getText().toString();
        if (note.length() != 0) {
            manager.insertPlan(new Plan(formatDate(Calendar.getInstance().getTime()), note));
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return format.format(date);
    }
}
