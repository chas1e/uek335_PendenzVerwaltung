package com.uek335.do_too.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uek335.do_too.model.Task;

import java.util.List;
//TaskDao, kommuniziert direkt mit DB. Notwening für Repository
@Dao
public interface TaskDao {
//Crud Logik
    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    //Hier noch Ordnung für die Darstellung auf dem Home Screen
    @Query("SELECT * FROM task_table ORDER BY is_done ASC, date_created DESC")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM task_table WHERE task_id ==:id")
    LiveData<Task> get(long id);


    @Delete
    void delete(Task task);

    @Update
    void update(Task task);
}
