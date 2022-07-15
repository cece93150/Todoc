package com.cleanup.todoc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.DataBase.DataBase.TodocMasterDataBase;
import com.cleanup.todoc.DataBase.DataBase.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TaskDao mTaskDao;
    private TodocMasterDataBase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodocMasterDataBase.class).build();
        mTaskDao = db.taskDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    final Task task1 = new Task(1, 1, "aaa", 123);
    final Task task2 = new Task(2, 2, "zzz", 124);
    final Task task3 = new Task(3, 3, "hhh", 125);

    @Test
    public void testAddAndDeleteTask() throws InterruptedException {
        Task taskToDelete = new Task(4, 1, "ccc", 127);
        mTaskDao.insertTask(taskToDelete);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(mTaskDao.getTasks());
        assertTrue(tasks.contains(taskToDelete));
        mTaskDao.deleteTask(taskToDelete);
        assertFalse(tasks.contains(taskToDelete));
    }

    @Test
    public void testGetTasksSortByAscName() throws InterruptedException {
        mTaskDao.insertTask(task1);
        mTaskDao.insertTask(task2);
        mTaskDao.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(mTaskDao.getTasksSortByAscName());
        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void testGetTasksSortByDescName() throws InterruptedException {
        mTaskDao.insertTask(task1);
        mTaskDao.insertTask(task2);
        mTaskDao.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(mTaskDao.getTasksSortByDescName());
        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void testGetTasksSortByAscNumberTime() throws InterruptedException {
        mTaskDao.insertTask(task1);
        mTaskDao.insertTask(task2);
        mTaskDao.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(mTaskDao.getTasksSortByAscNumberTime());
        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void testGetTasksSortByDescNumberTime() throws InterruptedException {
        mTaskDao.insertTask(task1);
        mTaskDao.insertTask(task2);
        mTaskDao.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(mTaskDao.getTasksSortByDescNumberTime());
        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}
