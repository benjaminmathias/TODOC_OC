package com.cleanup.todoc.ui;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Executor executor;

    // Data
    @Nullable
    private LiveData<Task> currentTask;

    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;
    }

    public void init(long taskId, long projectId) {
        if (this.currentTask != null) {
            return;
        }
        currentTask = taskRepository.getTask(taskId);
        currentProject = taskRepository.getProject(projectId);
    }

    // For Task
    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks(0);
    }


    public void createTask(final Task task) {
        executor.execute(() -> {
            taskRepository.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            taskRepository.deleteTask(taskId);
        });
    }

    // For Project
    public LiveData<List<Project>> getProjects() {
        return taskRepository.getProjects();
    }

    // For Sorting
    public LiveData<List<Task>> updateSortMethod(MainActivity.SortMethod sortMethod) {
        switch (sortMethod) {
            case ALPHABETICAL:
                return taskRepository.getTasks(1);

            case ALPHABETICAL_INVERTED:
                return taskRepository.getTasks(2);

            case RECENT_FIRST:
                return taskRepository.getTasks(3);

            case OLD_FIRST:
                return taskRepository.getTasks(4);

            default:
                return taskRepository.getTasks(0);
        }
    }
}
