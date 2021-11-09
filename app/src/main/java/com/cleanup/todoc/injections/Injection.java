package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskRepository(database.taskDao(), database.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskRepository dataSourceTask = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, executor);
    }
}