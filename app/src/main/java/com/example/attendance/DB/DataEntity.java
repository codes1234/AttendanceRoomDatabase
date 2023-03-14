package com.example.attendance.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String firstName;

    @ColumnInfo(name = "mobile_no")
    public String mobileNo;
    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "emp_id")
    public String empId;

    @ColumnInfo(name = "type")
    public String type;

    public DataEntity(String firstName, String mobileNo, String password, String empId, String type) {
        this.firstName = firstName;
        this.mobileNo = mobileNo;
        this.password = password;
        this.empId = empId;
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
