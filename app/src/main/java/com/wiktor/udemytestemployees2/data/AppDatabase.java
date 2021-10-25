package com.wiktor.udemytestemployees2.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wiktor.udemytestemployees2.pojo.Employee;

@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "employees.db";
    private static AppDatabase database;

    private static Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                //Чтобы при изменение версии базы данных все данные удалились нужно добавить метод .fallbackToDestructiveMigration()
                // - она говорит о следующем: Если версия базы данных изменилась, то удали все данные и создай новую базу данных
                database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract EmployeeDao employeeDao();
}
