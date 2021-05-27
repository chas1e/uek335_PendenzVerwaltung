package com.uek335.do_too.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.uek335.do_too.model.Task;
import com.uek335.do_too.util.TaskRoomDatabase;

import java.util.List;
//repository für DB, über DAO kommunikation mit DB. Erlaubt zugriff mit ViewModel
public class DoTooRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public DoTooRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    //Crud Logik
    public void insert(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.insertTask(task));
    }

    public LiveData<Task> get(long id) { return taskDao.get(id); }

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.update(task));
    }

    public void delete(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.delete(task));
    }
}
