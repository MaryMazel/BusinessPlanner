package com.example.businessplanner.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.businessplanner.data.entities.Plan;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert
    void insert(Plan... plans);

    @Query("delete from `plan` where id = :id")
    void deletePlan(long id);

    @Query("select * from `plan`")
    List<Plan> getAllPlans();

    @Query("select * from `plan` where id = :id")
    Plan getPlan(long id);

    @Query("update `plan` set note = :note, title = :title, date = :date where id = :id")
    void updatePlan(String note, String title, long date, long id);
}
