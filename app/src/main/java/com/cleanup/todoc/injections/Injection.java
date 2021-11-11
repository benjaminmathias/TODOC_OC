package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskDataSource(Context context){
        return new TaskRepository(getTodocDatabase(context).taskDao());
    }

    public static ProjectRepository provideProjectDataSource(Context context){
        return new ProjectRepository(getTodocDatabase(context).projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskRepository dataSourceTask = provideTaskDataSource(context);
        ProjectRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }

    private static TodocDatabase getTodocDatabase(Context context){
        return TodocDatabase.getInstance(context);
    }
}