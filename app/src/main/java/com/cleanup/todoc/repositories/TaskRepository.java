package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.SortMethod;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao mTaskDao) {
        this.mTaskDao = mTaskDao;
    }

    /**
     * Return a list of task corresponding to sortMethod
     *
     * @param sortMethod the sorting method
     * @return a list of task based on sortMethod
     */
    public LiveData<List<Task>> getTasks(SortMethod sortMethod) {
        switch (sortMethod) {
            case ALPHABETICAL:
                return mTaskDao.getTasksByNameASC();

            case ALPHABETICAL_INVERTED:
                return mTaskDao.getTasksByNameDESC();

            case RECENT_FIRST:
                return mTaskDao.getTasksByDateDESC();

            case OLD_FIRST:
                return mTaskDao.getTasksByDateASC();

            case NONE:
                return mTaskDao.getTasks();
        }
        return null;
    }

    // Return a single task
    // used for tests
    public LiveData<Task> getTask(long taskId) {
        return mTaskDao.getTask(taskId);
    }

    // CREATE
    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    // DELETE
    public void deleteTask(long taskId) {
        mTaskDao.deleteTask(taskId);
    }
}
