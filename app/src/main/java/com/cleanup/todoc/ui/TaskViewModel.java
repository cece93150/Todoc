package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
    private MutableLiveData <Integer> idSort = new MutableLiveData<>();

    //Data
    @Nullable
    private LiveData<List<Project>> mProjects;
    private LiveData<List<Task>> mTasks;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        mProjectDataSource = projectDataSource;
        mTaskDataSource = taskDataSource;
        mExecutor = executor;
        mTasks = Transformations.switchMap(idSort, new Function<Integer, LiveData<List<Task>>>() {
            @Override
            public LiveData<List<Task>> apply(Integer input) {
                switch (input) {
                    case 1:
                        return mTaskDataSource.getTasksSortByAscName();

                    case 2:
                        return mTaskDataSource.getTasksSortByDescName();

                    case 3:
                        return mTaskDataSource.getTasksSortByAscNumberTime();

                    case 4:
                        return mTaskDataSource.getTasksSortByDescNumberTime();

                    default:
                        return mTaskDataSource.getTasks();
                }
            }
        });
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
        return mTasks;
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.deleteTask(task));
    }

    public void onSortChanged(int idSort){
        this.idSort.setValue(idSort);
    }
}
