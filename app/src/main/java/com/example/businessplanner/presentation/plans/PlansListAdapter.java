package com.example.businessplanner.presentation.plans;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    PlansListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        plans = new ArrayList<>();
    }

    public void setItems(List<Plan> planList) {
        plans.add(new Plan("first", "wefuhweu  hwuief  huwe ehufhef ehufheufwe  iwuehf wiehrf wiwe hiwewf hw"));
        plans.add(new Plan("second", "efw wheud  qe3 3ugew wygy g3ufwy wuyg fsb hbf f"));
        plans.add(new Plan("third", "ehifwu hf shriuf whiur wiur r3 qq[2 0- -40 -w0 t"));
        plans.add(new Plan("fourth", "vkx nsfjek flq2okwow[plplw;pork5g tejd dh j hdu4h iushgi d h khwnmemjr  n,wk rl"));
        if (planList == null || planList.size() == 0) {
            return;
        }
        plans.addAll(planList);
        notifyDataSetChanged();
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

    void bind(Plan plan) {
        title.setText(plan.title);
        note.setText(plan.note);
    }

    ViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title);
        note = view.findViewById(R.id.note);
    }
}
}

