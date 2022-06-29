package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.DataBase.DataBase.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.mTaskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    public void deleteAllTask(Task task) {
        mTaskDao.deleteAllTask();
    }

    public LiveData<List<Task>> getTasksSortByAscName() {
        return mTaskDao.getTasksSortByAscName();
    }

    public LiveData<List<Task>> getTasksSortByDescName() {
        return mTaskDao.getTasksSortByDescName();
    }

    public LiveData<List<Task>> getTasksSortByAscNumberTime() {
        return mTaskDao.getTasksSortByAscNumberTime();
    }

    public LiveData<List<Task>> getTasksSortByDescNumberTime() {
        return mTaskDao.getTasksSortByDescNumberTime();
    }
}
