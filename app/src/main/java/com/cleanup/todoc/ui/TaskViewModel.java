package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //Repositories
    private final ProjectDataRepository mProjectDataSource;
    private final TaskDataRepository mTaskDataSource;
    private final Executor mExecutor;

    //Data
    @Nullable
    private LiveData<List<Project>> mProjects;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        mProjectDataSource = projectDataSource;
        mTaskDataSource = taskDataSource;
        mExecutor = executor;
    }

    public void init() {
        if (mProjects == null) {
            mProjects = mProjectDataSource.getProjects();
        }
    }

    //------------
    //For projects
    //------------
    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    //------------
    //For Tasks
    //------------
    public LiveData<List<Task>> getTasks() {
        return mTaskDataSource.getTasks();
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.deleteTask(task));
    }

    public LiveData<List<Task>> getTasksSortByAscTaskName() {
        return mTaskDataSource.getTasksSortByAscName();
    }

    public LiveData<List<Task>> getTasksSortByDescTaskName() {
        return mTaskDataSource.getTasksSortByDescName();
    }

    public LiveData<List<Task>> getTasksSortByAscNumberTime() {
        return mTaskDataSource.getTasksSortByAscNumberTime();
    }

    public LiveData<List<Task>> getTasksSortByDescNumberTime() {
        return mTaskDataSource.getTasksSortByDescNumberTime();
    }
}
