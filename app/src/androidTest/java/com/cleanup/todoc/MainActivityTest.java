package com.cleanup.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MainActivity activity;

    // For DATA
    private TodocDatabase dataBase;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule(MainActivity.class);

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
    public void addTask() throws InterruptedException {
        activity = mActivityTestRule.getActivity();
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        tasks.clear();
        assertEquals(tasks.size(), 0);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche exemple 1"));
        onView(withId(android.R.id.button1)).perform(click());

        tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        // assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getName().equals("Tâche exemple 1"));
    }

    @Test
    public void clearTasks() throws InterruptedException {
        activity = mActivityTestRule.getActivity();
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        tasks.clear();
        assertEquals(tasks.size(), 0);
    }

    @Test
    public void deleteTaskInst() throws InterruptedException {
        activity = mActivityTestRule.getActivity();
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        tasks.clear();
        assertEquals(tasks.size(), 0);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche exemple"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche exemple 2"));
        onView(withId(android.R.id.button1)).perform(click());

        assertEquals(tasks.size(), 0);
    }
}
