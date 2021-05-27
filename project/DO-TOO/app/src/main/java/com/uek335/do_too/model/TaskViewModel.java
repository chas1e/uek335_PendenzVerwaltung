package com.uek335.do_too.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.uek335.do_too.Data.DoTooRepository;

import java.util.List;

//Kommuniziert mit dem Repository, welches ueber das taskdao auf die Datenabnk zugreift
public class TaskViewModel extends AndroidViewModel {
    public static DoTooRepository repository;
    public final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new DoTooRepository(application);
        allTasks = repository.getAllTasks();
    }

    //Simple CRUD logik im View Model, erlaubt uns schlussendlich mit tasks umzugehen und diese in der DB zu speichern.
    public LiveData<List<Task>> getAllTasks(){return allTasks;}
    public static void insert(Task task){repository.insert(task);}
    public LiveData<Task> get(long id){return repository.get(id);}
    public static void update(Task task) { repository.update(task);}
    public static void delete(Task task) { repository.delete(task);}
}
