package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao mProjectDao) {
        this.mProjectDao = mProjectDao;
    }

    // Return list of Project
    public LiveData<List<Project>> getProjects() {
        return this.mProjectDao.getProjects();
    }

    // Return a single project
    // used for tests
    public LiveData<Project> getProject(long projectId) {
        return this.mProjectDao.getDaoProject(projectId);
    }
}
