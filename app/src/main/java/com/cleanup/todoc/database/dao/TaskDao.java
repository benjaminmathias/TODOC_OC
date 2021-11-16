package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    // READ a specific task
    @Query("SELECT * FROM Task WHERE id = :id")
    LiveData<Task> getTask(long id);

    // READ all tasks
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    // CREATE a new task
    @Insert
    void insertTask(Task task);

    // DELETE a specific task
    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);


    // -- Sort list --

    // READ all task sorted by name ASC
    @Query("SELECT * from Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksByNameASC();

    // READ all task sorted by name DESC
    @Query("SELECT * from Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksByNameDESC();

    // READ all task sorted by date ASC
    @Query("SELECT * from Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksByDateASC();

    // READ all task sorted by date DESC
    @Query("SELECT * from Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksByDateDESC();

}
