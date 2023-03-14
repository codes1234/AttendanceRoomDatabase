package com.example.attendance.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Constance.Appcons;
import com.example.attendance.DB.AppDataBase;
import com.example.attendance.DB.DataEntity;
import com.example.attendance.UserDao;
import com.example.attendance.databinding.ActivityAdminLoginBinding;

public class AdminLoginActivity extends AppCompatActivity {

    ActivityAdminLoginBinding binding;
    AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         db= Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class,"room_db").allowMainThreadQueries().build();

        binding.team1.setOnClickListener(v -> {
            binding.tabSelector.animate().x(0f).setDuration(300);
            binding.userLoginUi.setVisibility(View.VISIBLE);
            binding.userRegistrationUi.setVisibility(View.GONE);
        });

        binding.team2.setOnClickListener(v -> {
            binding.tabSelector.animate().x(binding.team2.getWidth()).setDuration(300);
            binding.userRegistrationUi.setVisibility(View.VISIBLE);
            binding.userLoginUi.setVisibility(View.GONE);

        });

        binding.button.setOnClickListener(view -> {
            UserDao userDao=db.userDao();
            DataEntity entity=userDao.login(binding.empId.getText().toString(),
                    binding.adminLoginPass.getText().toString(), Appcons.ADMIN);

            if(entity !=null){
                Toast.makeText(getApplicationContext(),"login success",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,AdminHomeActivity.class);
                startActivity(intent);

            }else {
                Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }

    private void validate() {
        if (binding.employName.getText().toString().trim().isEmpty()) {
            binding.employName.setError("pleas enter name");
            binding.employName.requestFocus();
        } else if (binding.empMobileNo.getText().toString().trim().isEmpty()) {
            binding.empMobileNo.setError("pleas enter phone number");
            binding.empMobileNo.requestFocus();
        } else if (binding.empMobileNo.getText().toString().trim().length() < 10) {
            binding.empMobileNo.setError("pleas enter 10 digit number");
            binding.empMobileNo.requestFocus();
        } else if (binding.empPassword.getText().toString().trim().isEmpty()) {
            binding.empPassword.setError("pleas enter password");
            binding.empPassword.requestFocus();
        } else if (binding.confPassword.getText().toString().trim().isEmpty()) {
            binding.confPassword.setError("pleas enter confirm password");
            binding.confPassword.requestFocus();
        } else if (!binding.empPassword.getText().toString().trim()
                .equalsIgnoreCase(binding.confPassword.getText().toString().trim())) {
            Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show();
        } else {
            registration();
        }
    }

    private void registration() {
        UserDao userDao = db.userDao();
        userDao.insertAll(new DataEntity(binding.employName.getText().toString(),
                binding.empMobileNo.getText().toString(),
                binding.empPassword.getText().toString(),
                binding.empMobileNo.getText().toString() + "@admin",
                Appcons.ADMIN
        ));
        Toast.makeText(this, binding.empMobileNo.getText().toString() + "@admin", Toast.LENGTH_LONG).show();
//        binding.userLoginUi.setVisibility(View.VISIBLE);
//        binding.userRegistrationUi.setVisibility(View.GONE);
        binding.employName.setText("");
        binding.empMobileNo.setText("");
        binding.empPassword.setText("");
        binding.confPassword.setText("");
    }
}