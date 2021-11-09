package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getDaoProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProject(Project project);

}