package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    // READ a specific project
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getDaoProject(long projectId);

    // READ all projects
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    // CREATE a new project (used for tests)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

}