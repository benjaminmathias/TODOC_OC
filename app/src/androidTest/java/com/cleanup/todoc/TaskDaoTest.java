package com.cleanup.todoc;


import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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

        this.dataBase.projectDao().insertProject(PROJECT_DEMO);
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
     *  db contain A THEN B
     */
    @Test
    public void sortTasksByNameASC1() throws InterruptedException {
        // Insert tasks in db
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B

        // Create a new sorted List of Tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameASC());

        // Assert that the first task in db have the same name has the first task in the sorted list
        assertEquals(TASK_DEMO.getName(), sortedTask.get(0).getName());
    }

    /**
     *  db contain B THEN A
     */
    @Test
    public void sortTasksByNameASC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A

        // Create a new sorted List of Tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameASC());

        // Assert that the first task in the sorted list contains TASK_DEMO name
        assertEquals(TASK_DEMO.getName(), sortedTask.get(0).getName());
    }

    /**
     *  db contain A THEN B
     */
    @Test
    public void sortTasksByNameDESC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameDESC());

        // Assert that the first task in the sorted list contains TASK_DEMO_2 name
        assertEquals(TASK_DEMO_2.getName(), sortedTask.get(0).getName());
    }

    /**
     *  db contain B THEN A
     */
    @Test
    public void sortTasksByNameDESC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // B
        this.dataBase.taskDao().insertTask(TASK_DEMO); // A

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByNameDESC());

        // Assert that the first task in the sorted list contains TASK_DEMO_2 name
        assertEquals(TASK_DEMO_2.getName(), sortedTask.get(0).getName());
    }

    /**
     * db contain 1 THEN 2
     */
    @Test
    public void sortTasksByDateASC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateASC());

        // Assert that the first task in the sorted list has the same timestamp as TASK_DEMO
        assertEquals(TASK_DEMO.getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());

    }

    /**
     * db contain 2 THEN 1
     */
    @Test
    public void sortTasksByDateASC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateASC());

        // Assert that the first task in the sorted list has the same timestamp as TASK_DEMO
        assertEquals(TASK_DEMO.getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }

    /**
     * db contain 1 THEN 2
     */
    @Test
    public void sortTasksByDateDESC1() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateDESC());

        // Assert that the first task in the sorted list has the same timestamp as TASK_DEMO_2
        assertEquals(TASK_DEMO_2.getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }

    /**
     * db contain 2 THEN 1
     */
    @Test
    public void sortTasksByDateDESC2() throws InterruptedException {
        // Insert tasks
        this.dataBase.taskDao().insertTask(TASK_DEMO_2); // 2
        this.dataBase.taskDao().insertTask(TASK_DEMO); // 1

        // Sort tasks
        List<Task> sortedTask = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasksByDateDESC());

        // Assert that the first task in the sorted list has the same timestamp as TASK_DEMO_2
        assertEquals(TASK_DEMO_2.getCreationTimestamp(), sortedTask.get(0).getCreationTimestamp());
    }
}
