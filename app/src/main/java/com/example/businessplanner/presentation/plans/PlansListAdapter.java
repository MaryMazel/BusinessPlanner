package com.example.businessplanner.presentation.plans;

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
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Plan> plans;
    private DatabaseManager manager;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Plan plan);
    }

    PlansListAdapter(Context context, List<Plan> plans, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.manager = new DatabaseManager(context);
        this.plans = plans;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.plans_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(plans.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return plans == null ? 0 : plans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView note;
        final TextView date;
        final ImageView delete;

        void bind(final Plan plan, final OnItemClickListener listener) {
            title.setText(plan.title);
            note.setText(plan.note);
            date.setText(formatDate(plan.date));
            delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation")
                        .setMessage("Do you really want to delete a plan: '" + plan.title + "'?")
                        .setNegativeButton("No",
                                (dialog, id) -> dialog.cancel())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            manager.deletePlan(plan.id);
                            plans.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });
            itemView.setOnClickListener(v -> listener.onItemClick(plan));
        }


        public String formatDate(long date) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            return format.format(date);
        }

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            note = view.findViewById(R.id.note_preview);
            delete = view.findViewById(R.id.delete_plan);
            date = view.findViewById(R.id.date);
        }
    }
}

