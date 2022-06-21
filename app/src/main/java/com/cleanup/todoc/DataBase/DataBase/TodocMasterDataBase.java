package com.cleanup.todoc.DataBase.DataBase;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.DataBase.DataBase.dao.ProjectDao;
import com.cleanup.todoc.DataBase.DataBase.dao.TaskDao;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocMasterDataBase extends RoomDatabase {
    public static volatile TodocMasterDataBase INSTANCE;

    public static TodocMasterDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocMasterDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TodocMasterDataBase.class,
                                    "TodocDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("projects", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();
}
