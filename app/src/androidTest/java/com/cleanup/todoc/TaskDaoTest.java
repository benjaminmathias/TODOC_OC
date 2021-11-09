package com.cleanup.todoc;


import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // For DATA
    private TodocDatabase dataBase;

    // DATA FOR TEST
    private static Task TASK_DEMO = new Task(0, 1L, "A", 1);
    private static Task TASK_DEMO_2 = new Task(0, 1L, "B", 2);
    private static Project PROJECT_DEMO = new Project(1L, "Test Project", 0);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.dataBase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        // TODO: vérifier avec mentor
        this.dataBase.projectDao().insertProject(PROJECT_DEMO);
    }

    @After
    public void closeDb() throws Exception {
        dataBase.clearAllTables();
        dataBase.close();
    }

    // TODO: fix this
    @Test
    public void insertAndGetTask() throws InterruptedException {
        // Adding a new task
        this.dataBase.taskDao().insertTask(TASK_DEMO);

        // Retrieving the task that we added
        List<Task> task = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        assertEquals(task.get(0).getName(), TASK_DEMO.getName());
    }

    @Test
    public void getAllTasks() throws InterruptedException {
        // Making sure that our table is empty
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(tasks.size(), 0);

        // Adding 2 tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO);
        this.dataBase.taskDao().insertTask(TASK_DEMO_2);

        // Retrieving all tasks
        tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(tasks.size(), 2);
    }

    @Test
    public void deleteTask() throws InterruptedException {
        // Adding 2 new tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO);
        this.dataBase.taskDao().insertTask(TASK_DEMO_2);

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(tasks.size(), 2);

        // Deleting the task with corresponding Id
        this.dataBase.taskDao().deleteTask(1);

        // Checking that only one task is left
        tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(tasks.size(), 1);
    }


    // SORTING QUERY TESTS

    /**
     *  Retrieved list contain A THEN B
     */
    @Test
    public void sortTasksByNameASC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Create a new sorted List of Tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameASC());

        Log.d("tag","Résultat NameASC1: " + tasks.get(0).getName() + " & " + sortedTask.get(0).getName());

        // Assert that the first task in the tasks list have the same name has the first task in the sorted list
        assertEquals(tasks.get(0).getName(), sortedTask.get(0).getName());
    }

    /**
     *  Retrieved list contain B THEN A
     */
    @Test
    public void sortTasksByNameASC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Create a new sorted List of Tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameASC());

        Log.d("tag","Résultat NameASC2: " + tasks.get(0).getName() + " & " + sortedTask.get(0).getName());

        // Assert that the first task in the tasks list doesn't have the same name has the first task in the sorted list
        assertNotEquals(tasks.get(0).getName(), sortedTask.get(0).getName());
    }

    /**
     *  Retrieved list contain A THEN B
     */
    @Test
    public void sortTasksByNameDESC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameDESC());

        Log.d("tag","Résultat NameDESC1 : " + tasks.get(0).getName() + " & " + sortedTask.get(0).getName());

        // Assert that the first task in the tasks list doesn't have the same name has the first task in the sorted list
        assertNotEquals(tasks.get(0).getName(), sortedTask.get(0).getName());
    }

    /**
     *  Retrieved list contain B THEN A
     */
    @Test
    public void sortTasksByNameDESC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameDESC());

        Log.d("tag","Résultat NameDESC2 : " + tasks.get(0).getName() + " & " + sortedTask.get(0).getName());

        // Assert that the first task in the tasks list have the same name has the first task in the sorted list
        assertEquals(tasks.get(0).getName(), sortedTask.get(0).getName());
    }

    @Test
    public void sortTasksByDateASC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateASC());

        Log.d("tag","Résultat : " + tasks.get(0).getCreationTimestamp() + " & " + sortedTask.get(0).getCreationTimestamp());

        // Assert that the first task in task list have the same timestamp has the first task in the sorted list
        assertEquals(tasks.get(0).getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());

    }

    @Test
    public void sortTasksByDateASC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateASC());

        Log.d("tag","Résultat DateASC2 : " + tasks.get(0).getCreationTimestamp() + " & " + sortedTask.get(0).getCreationTimestamp());

        // Assert that the first task in task list doesn't have the same timestamp has the first task in the sorted list
        assertNotEquals(tasks.get(0).getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }

    @Test
    public void sortTasksByDateDESC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateDESC());

        Log.d("tag","Résultat DateDESC1 : " + tasks.get(0).getCreationTimestamp() + " & " + sortedTask.get(0).getCreationTimestamp());

        // Assert that the first task in task list doesn't have the same timestamp has the first task in the sorted list
        assertNotEquals(tasks.get(0).getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }

    @Test
    public void sortTasksByDateDESC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1

        // Retrieving all tasks
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateDESC());

        Log.d("tag","Résultat DateDESC2 : " + tasks.get(0).getCreationTimestamp() + " & " + sortedTask.get(0).getCreationTimestamp());

        // Assert that the first task in task list have the same timestamp has the first task in the sorted list
        assertEquals(tasks.get(0).getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }
}
