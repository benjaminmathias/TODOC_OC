package com.cleanup.todoc.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository taskDatasource;
    private final ProjectRepository projectDatasource;
    private final Executor executor;

    public ViewModelFactory(TaskRepository taskDatasource, ProjectRepository projectDatasource, Executor executor){
        this.taskDatasource = taskDatasource;
        this.projectDatasource = projectDatasource;
        this.executor = executor;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            return (T) new TaskViewModel(taskDatasource, projectDatasource, executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
