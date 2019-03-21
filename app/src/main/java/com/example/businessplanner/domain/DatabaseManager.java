package com.example.businessplanner.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.businessplanner.data.AppDatabase;
import com.example.businessplanner.data.DatabaseInitializer;
import com.example.businessplanner.data.dao.PlanDao;
import com.example.businessplanner.data.entities.Plan;

import java.util.List;

public class DatabaseManager {
    private AppDatabase database;
    private PlanDao planDao;

    public DatabaseManager(@NonNull Context context) {
        database = DatabaseInitializer.createDatabase(context);
        this.planDao = database.planDao();
    }

    public void insertPlan(Plan plan) {
        planDao.insert(plan);
    }

    public List<Plan> getPlans() {
        return planDao.getAllPlans();
    }

    public Plan getPlan(int id) {
        return planDao.getPlan(id);
    }

    public void deletePlan(int id) {
        planDao.deletePlan(id);
    }

    public void updatePlanNote(String note, int id) {
        planDao.updatePlanNote(note, id);
    }
}
