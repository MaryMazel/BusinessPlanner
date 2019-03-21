package com.example.businessplanner.presentation.plans;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.businessplanner.R;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.ViewHolder> {
    private LayoutInflater inflater;

    PlansListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.plans_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView note;

        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            note = view.findViewById(R.id.note);
        }
    }
}

/*public class DisciplineListAdapter extends RecyclerView.Adapter<DisciplineListAdapter.ViewHolder> {
        private DisciplineListFragment disciplineListFragment;

        private LayoutInflater inflater;
        private List<Discipline> disciplines;

        DisciplineListAdapter(Context context, DisciplineListFragment disciplineListFragment) {
            this.disciplines = new ArrayList<>();

            this.disciplineListFragment = disciplineListFragment;

            this.inflater = LayoutInflater.from(context);
        }

        public void setDisciplines(List<Discipline> disciplines) {
            this.disciplines = disciplines;
            notifyDataSetChanged();
        }

        @Override
        public DisciplineListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.disciplines_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Discipline discipline = viewHolder.discipline;
                    if (discipline == null)
                        return;
                    disciplineListFragment.onItemClicked(discipline);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DisciplineListAdapter.ViewHolder holder, int position) {
            Discipline discipline = disciplines.get(position);
            holder.disciplineName.setText(discipline.name);
            holder.discipline = discipline;
        }

        @Override
        public int getItemCount() {
            return disciplines.size();
        }


    }*/
