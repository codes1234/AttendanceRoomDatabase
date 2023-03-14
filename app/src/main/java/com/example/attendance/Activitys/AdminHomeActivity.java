package com.example.attendance.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.attendance.Adapters.SheetAdapter;
import com.example.attendance.DB.AppDataBase;
import com.example.attendance.DB.DataEntity;
import com.example.attendance.DB.LoginEntity;
import com.example.attendance.R;
import com.example.attendance.UserDao;
import com.example.attendance.databinding.ActivityAdminHomeBinding;

import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    ActivityAdminHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        AppDataBase db= Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class,"room_db").allowMainThreadQueries().build();


        UserDao userDao=db.userDao();
        List<LoginEntity> userlist = userDao.loadAllByIds();

        Log.e("aass#22323", "onCreate: "+userlist.size() );

        binding.recyclerView.setHasFixedSize(true);
        SheetAdapter adapter=new SheetAdapter(this,userlist);
        binding.recyclerView.setAdapter(adapter);
    }
}