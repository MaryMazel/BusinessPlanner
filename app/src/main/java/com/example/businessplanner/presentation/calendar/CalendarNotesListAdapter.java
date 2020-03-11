package com.example.businessplanner.presentation.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.CalendarEvent;
import com.example.businessplanner.domain.DatabaseManager;

import java.util.List;

public class CalendarNotesListAdapter extends RecyclerView.Adapter<CalendarNotesListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<CalendarEvent> calendarEvents;
    private DatabaseManager manager;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CalendarEvent event);
    }

    CalendarNotesListAdapter(Context context, List<CalendarEvent> events, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.manager = new DatabaseManager(context);
        this.calendarEvents = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.cal_plan_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(calendarEvents.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return calendarEvents == null ? 0 : calendarEvents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView text;
        final ImageView delete;

        void bind(final CalendarEvent event, final OnItemClickListener listener) {
            text.setText(event.text);

            delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation")
                        .setMessage("Do you really want to delete event?")
                        .setNegativeButton("No",
                                (dialog, id) -> dialog.cancel())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            manager.deleteEvent(event.id);
                            calendarEvents.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });

            itemView.setOnClickListener(v -> listener.onItemClick(event));
        }

        ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text_cal_item);
            delete = view.findViewById(R.id.delete_event);
        }
    }
}

