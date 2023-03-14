package com.example.attendance.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoginEntity {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "emp_id")
    public String empId;

    @ColumnInfo(name = "name")
    public String firstName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "location")
    public String location;


    public LoginEntity(String empId, String firstName, String date, String time, String location) {
        this.empId = empId;
        this.firstName = firstName;
        this.date = date;
        this.time = time;
        this.location = location;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
