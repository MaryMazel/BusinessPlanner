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
    void deletePlan(int id);

    @Query("select * from `plan`")
    List<Plan> getAllPlans();

    @Query("select * from `plan` where id = :id")
    Plan getPlan(int id);

    @Query("update `plan` set note = :note where id = :id")
    void updatePlanNote(String note, int id);
}
