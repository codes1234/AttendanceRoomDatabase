package com.example.attendance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.attendance.DB.DataEntity;
import com.example.attendance.DB.LoginEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertAll(DataEntity users);


    @Query("SELECT * FROM LoginEntity")
    List<LoginEntity> loadAllByIds();


    @Query("SELECT * FROM DATAENTITY WHERE emp_id = :empid AND " + "password LIKE :password AND type = :type ")
    DataEntity login(String empid, String password,String type);



    //login
    @Insert
    void loginInsert(LoginEntity loginusers);


}