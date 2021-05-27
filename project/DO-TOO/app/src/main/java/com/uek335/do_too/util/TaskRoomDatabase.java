package com.uek335.do_too.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.uek335.do_too.Data.TaskDao;
import com.uek335.do_too.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Datenbank fuer unsere Pendenzverwaltungs App
@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "dotoo_db";
    //Threads und Threadpool fuer Performance
    public static final int NUMBER_OF_THREADS = 4;
    private static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //Callback um diverse Aktionen vor der Datenbank kreation geschehen zu lassen
    public static final RoomDatabase.Callback sRoomDatabaseCallback
            = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExecutor.execute(() -> {
                TaskDao taskDao = INSTANCE.taskDao();
                //loescht alles aus der datenbank wenn sie neu gemacht wird
                taskDao.deleteAll();
            });
        }
    };

    //Stellt sicher das es nur eine Instanz zur Datenbank gibt, gibt uns diese zurueck
    public static TaskRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //Task dao
    public abstract TaskDao taskDao();
}
