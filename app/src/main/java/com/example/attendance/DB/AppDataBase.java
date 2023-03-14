package com.example.attendance.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.attendance.UserDao;

@Database(entities = {DataEntity.class,LoginEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
