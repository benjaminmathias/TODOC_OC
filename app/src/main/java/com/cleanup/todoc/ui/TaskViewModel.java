package com.cleanup.todoc.ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.SortMethod;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    // Data
    @Nullable
    private LiveData<Task> currentTask;

    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    public void init(long taskId, long projectId) {
        if (this.currentTask != null) {
            return;
        }
        currentTask = taskRepository.getTask(taskId);
        currentProject = projectRepository.getProject(projectId);
    }

    // For Task
    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks(sortMethod);
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
        return projectRepository.getProjects();
    }

    // For Sorting
    public void updateSortMethod(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }
}
