package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM Task WHERE id = :id")
    LiveData<Task> getTask(long id);

    @Insert
    long insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);


    // -- Sort list --

    @Query("SELECT * from Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksByNameASC();

    @Query("SELECT * from Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksByNameDESC();

    @Query("SELECT * from Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksByDateASC();

    @Query("SELECT * from Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksByDateDESC();

}
