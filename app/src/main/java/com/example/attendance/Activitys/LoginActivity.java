package com.example.attendance.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.attendance.Constance.Appcons;
import com.example.attendance.DB.AppDataBase;
import com.example.attendance.DB.LoginEntity;
import com.example.attendance.DB.DataEntity;
import com.example.attendance.UserDao;
import com.example.attendance.databinding.ActivityLoginBinding;
import com.google.android.gms.location.FusedLocationProviderClient;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    AppDataBase db;
    private static final int REQUEST_LOCATION = 1;
    String address;
    LocationManager locationManager;
    Geocoder geocoder;
    List<Address> addresses;
    double latitude, longitude;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "room_db").allowMainThreadQueries().build();

        getAutoAddress();

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

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        binding.signIn.setOnClickListener(v -> {
            getLocation();
            String time = getTime();
            String date = getDate();

            UserDao userDao = db.userDao();
            DataEntity user = userDao.login(binding.name.getText().toString(),
                    binding.loginpasssword.getText().toString(), Appcons.EMP);

            if (user != null) {
                userDao.loginInsert(new LoginEntity(user.empId, user.firstName, date, time, address));
                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
                binding.name.setText("");
                binding.loginpasssword.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
            }
//
//
        });

    }
    private void getAutoAddress() {
        locationManager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
//            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.v("sahashga","1");
            } else {
                Log.v("sahashga","2");
            }
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationGPS = getLastKnownLocation();
            Log.v("hdasjh",""+locationGPS);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = lat;
                longitude = longi;
                try {
                    getAutoDetail();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v("sbkskholisj","Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
//                Toast.makeText(this, "Unable to find location1.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //
    private void getAutoDetail() throws IOException
    {
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        Log.v("kjgdifgdgd",addresses.toString());
        Log.v("gdjhgdjv",address+",1"+city+",2"+state+",3"+country+",4"+postalCode+",5"+knownName);

    }


    private void OnGPS()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    Location myLocation = getLastKnownLocation();
                    if (myLocation != null) {
                        double lat = myLocation.getLatitude();
                        double longi = myLocation.getLongitude();
                        latitude = lat;
                        longitude = longi;
                        try {
                            getAutoDetail();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.v("sbkskholisj", "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                    Log.v("fddfdffddfdfs","1"+myLocation);
                }
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);

    }
    private String getTime() {
        String currentDateTimeString = java.text.DateFormat.getTimeInstance().format(new Date());
        return currentDateTimeString;
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
                binding.empMobileNo.getText().toString() + "@emp",
                Appcons.EMP
        ));
        Toast.makeText(this, binding.empMobileNo.getText().toString() + "@emp", Toast.LENGTH_LONG).show();

        binding.employName.setText("");
        binding.empMobileNo.setText("");
        binding.empPassword.setText("");
        binding.confPassword.setText("");
    }


}
