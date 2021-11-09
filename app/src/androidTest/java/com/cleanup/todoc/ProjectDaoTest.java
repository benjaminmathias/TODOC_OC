package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    // For DATA
    private TodocDatabase dataBase;

    // DATA FOR TEST ONLY
    private static long PROJECT_ID = 10L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Test Project", 0);
    private static Project PROJECT_DEMO_2 = new Project(20, "Test Project 2", 0);
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
    public void InsertAndGetDaoProjectTest() throws InterruptedException {
        // Insert a test project
        this.dataBase.projectDao().insertProject(PROJECT_DEMO);
        // Get a specific project
        this.dataBase.projectDao().getDaoProject(PROJECT_ID);

        // Check that the database contain PROJECT_DEMO
        Project project = LiveDataTestUtil.getValue(this.dataBase.projectDao().getDaoProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void GetAllProjects() throws InterruptedException {
        // Insert both test projects
        this.dataBase.projectDao().insertProject(PROJECT_DEMO);
        this.dataBase.projectDao().insertProject(PROJECT_DEMO_2);

        // Retrieve all projects
        List<Project> projects = LiveDataTestUtil.getValue(this.dataBase.projectDao().getProjects());
        assertEquals(projects.size(), 2);
    }
}
