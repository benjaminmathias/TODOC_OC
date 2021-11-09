package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;

    public TaskRepository(TaskDao mTaskDao, ProjectDao mProjectDao) {
        this.mTaskDao = mTaskDao;
        this.mProjectDao = mProjectDao;
    }

    // For Task
    // Return list of Task
    public LiveData<List<Task>> getTasks(int sortMethod) {
        switch (sortMethod) {
            case 1:
                return this.mTaskDao.getTasksByNameASC();

            case 2:
                return this.mTaskDao.getTasksByNameDESC();

            case 3:
                return this.mTaskDao.getTasksByDateASC();

            case 4:
                return this.mTaskDao.getTasksByDateDESC();

            case 0:
                return this.mTaskDao.getTasks();
        }
        return null;
    }

    // Return a single task
    public LiveData<Task> getTask(long taskId) {
        return this.mTaskDao.getTask(taskId);
    }

    // CREATE
    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    // DELETE
    public void deleteTask(long taskId) {
        mTaskDao.deleteTask(taskId);
    }


    //For Project
    // Return list of Project
    public LiveData<List<Project>> getProjects() {
        return this.mProjectDao.getProjects();
    }

    // Return a single project
    public LiveData<Project> getProject(long projectId) {
        return this.mProjectDao.getDaoProject(projectId);
    }

}
