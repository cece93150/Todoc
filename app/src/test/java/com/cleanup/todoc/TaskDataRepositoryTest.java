package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.DataBase.DataBase.dao.TaskDao;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskDataRepository;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TaskDataRepositoryTest {

    TaskDao mTaskDao = Mockito.mock(TaskDao.class);
    private TaskDataRepository mTaskDataRepository = new TaskDataRepository(mTaskDao);

    @Test
    public void getTask(){
        LiveData<List<Task>> expected = new MutableLiveData<>();
        Mockito.doReturn(expected).when(mTaskDao).getTasks();
        LiveData<List<Task>> taskTest = mTaskDataRepository.getTasks();
        assertEquals(expected, taskTest);
        Mockito.verify(mTaskDao).getTasks();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void deleteTask(){
        Task task = new Task(5,1,"fff",129);
        mTaskDataRepository.deleteTask(task);
        Mockito.verify(mTaskDao).deleteTask(task);
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void createTask(){
        Task task = new Task(5,1,"fff",129);
        mTaskDataRepository.createTask(task);
        Mockito.verify(mTaskDao).insertTask(task);
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void test_az_comparator() {
        LiveData<List<Task>> expected = new MutableLiveData<>();
        Mockito.doReturn(expected).when(mTaskDao).getTasksSortByAscName();
        LiveData<List<Task>> taskTest = mTaskDataRepository.getTasksSortByAscName();
        assertEquals(expected, taskTest);
        Mockito.verify(mTaskDao).getTasksSortByAscName();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void test_za_comparator() {
        LiveData<List<Task>> expected = new MutableLiveData<>();
        Mockito.doReturn(expected).when(mTaskDao).getTasksSortByDescName();
        LiveData<List<Task>> taskTest = mTaskDataRepository.getTasksSortByDescName();
        assertEquals(expected, taskTest);
        Mockito.verify(mTaskDao).getTasksSortByDescName();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void test_recent_comparator() {
        LiveData<List<Task>> expected = new MutableLiveData<>();
        Mockito.doReturn(expected).when(mTaskDao).getTasksSortByAscNumberTime();
        LiveData<List<Task>> taskTest = mTaskDataRepository.getTasksSortByAscNumberTime();
        assertEquals(expected, taskTest);
        Mockito.verify(mTaskDao).getTasksSortByAscNumberTime();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void test_old_comparator() {
        LiveData<List<Task>> expected = new MutableLiveData<>();
        Mockito.doReturn(expected).when(mTaskDao).getTasksSortByDescNumberTime();
        LiveData<List<Task>> taskTest = mTaskDataRepository.getTasksSortByDescNumberTime();
        assertEquals(expected, taskTest);
        Mockito.verify(mTaskDao).getTasksSortByDescNumberTime();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }
}


