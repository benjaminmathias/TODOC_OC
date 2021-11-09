package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
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
    private static long TASK_ID = 1;
    private static long TASK_ID_2 = 2;
    private static Task TASK_DEMO = new Task(TASK_ID, 1L, "Test 1", 1);
    private static Task TASK_DEMO_2 = new Task(TASK_ID_2, 1L, "Test 2", 2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.dataBase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        dataBase.clearAllTables();
        dataBase.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // Adding a new task
        this.dataBase.taskDao().insertTask(TASK_DEMO);

        // Retrieving the task that we added
        Task task = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTask(TASK_ID));
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_ID);
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


}
