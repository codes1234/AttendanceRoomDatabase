package com.example.attendance.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.attendance.R;
import com.example.attendance.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.adminLoginBtn.setOnClickListener(view -> {
            Intent intent=new Intent(this,AdminLoginActivity.class );
            startActivity(intent);
        });
        binding.userLoginBtn.setOnClickListener(view -> {
            Intent intent=new Intent(this,LoginActivity.class );
            startActivity(intent);
        });
    }
}