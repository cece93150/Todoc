package com.cleanup.todoc.DataBase.DataBase.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTask();

    @Query("SELECT * FROM tasks ORDER BY name ASC")
    LiveData<List<Task>> getTasksSortByAscName();

    @Query("SELECT * FROM tasks ORDER BY name DESC")
    LiveData<List<Task>> getTasksSortByDescName();


    @Query("SELECT * FROM tasks ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksSortByAscNumberTime();

    @Query("SELECT * FROM tasks ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksSortByDescNumberTime();
}
