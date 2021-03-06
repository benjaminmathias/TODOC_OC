package com.cleanup.todoc.ui;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.SortMethod;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // The SortMethod used to display Tasks
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    // Data
    private final MediatorLiveData<List<Task>> tasks = new MediatorLiveData<>();


    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    public void init() {
        tasks.addSource(taskRepository.getTasks(sortMethod), tasks::setValue);
    }

    // For Task
    public LiveData<List<Task>> getTasks() {
        return tasks;
    }


    public void createTask(final Task task) {
        executor.execute(() -> taskRepository.createTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskRepository.deleteTask(taskId));
    }

    // For Sorting
    public void updateSortMethod(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        init();
    }


    // For Project
    public LiveData<List<Project>> getProjects() {
        return projectRepository.getProjects();
    }
}
