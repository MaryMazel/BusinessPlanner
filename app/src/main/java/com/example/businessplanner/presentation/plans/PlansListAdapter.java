package com.example.businessplanner.presentation.plans;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Plan> plans;
    private DatabaseManager manager;
    private Context context;

    PlansListAdapter(Context context, List<Plan> plans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.manager = new DatabaseManager(context);
        this.plans = plans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.plans_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(plans.get(i));
    }

    @Override
    public int getItemCount() {
        return plans == null ? 0 : plans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView note;
        final ImageView delete;
        final LinearLayout linearLayout;

        void bind(final Plan plan) {
            title.setText(plan.title);
            note.setText(plan.note);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.deletePlan(plan.id);
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InputNoteActivity.class);
                    intent.putExtra("plan_id", plan.id);
                    context.startActivity(intent);
                }
            });
        }

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            note = view.findViewById(R.id.note);
            delete = view.findViewById(R.id.delete_plan);
            linearLayout = view.findViewById(R.id.layout_item);
        }
    }
}

