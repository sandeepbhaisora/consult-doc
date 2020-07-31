package com.example.consultdoc.startup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.consultdoc.DbHelper;
import com.example.consultdoc.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SignUp extends AppCompatActivity implements LocationListener {

    TextInputLayout textInputLayout;

    LocationManager locationManager;

    TextInputEditText editText;

    TextInputEditText userName;

    TextInputEditText passWord;

    DbHelper dbHelper;

    String[] BLOOD_GROUP = new String[] {"A+","AB+","O","B", "A-" , "AB-","O-","B-"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputLayout = findViewById(R.id.get_location_sign_up);

        editText = findViewById(R.id.location_data);



        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(SignUp.this,"fetching location",Toast.LENGTH_SHORT);
                toast.show();
                getLocation();
            }
        });

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                       SignUp.this,
                        R.layout.layout_menu_pop_up,
                        BLOOD_GROUP);

        AutoCompleteTextView editTextFilledExposedDropdown =
                this.findViewById(R.id.blood_group_drop_down);
        editTextFilledExposedDropdown.setAdapter(adapter);



    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


        try {
            Geocoder geocoder = new Geocoder(SignUp.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            System.out.println(addresses);
            String country = addresses.get(0).getCountryName(),state = addresses.get(0).getAdminArea(),dist = addresses.get(0).getSubAdminArea(),locality = addresses.get(0).getLocality();
            String pin = addresses.get(0).getPostalCode();
            String add = addresses.get(0).getAddressLine(0);
            System.out.println(add);
            editText.setText(add);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getLocation() {

        if (ContextCompat.checkSelfPermission(
                Objects.requireNonNull(SignUp.this), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("did not got permission ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);
        } else {
            checkLocationEnabled();
            getLocationData();
        }


    }




    private void checkLocationEnabled() {


        LocationManager lm = (LocationManager) SignUp.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(SignUp.this).setTitle("Enable location service")
                    .setCancelable(true)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null).show();

        }
    }


    private void getLocationData() {
        try {
            System.out.println("+++++++++++++++++++++++++++++++++");
            locationManager = (LocationManager) SignUp.this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,  this);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    public void signUpNow(View view){
        dbHelper = new DbHelper(this);

        userName = findViewById(R.id.user_name);

        passWord = findViewById(R.id.password);

        String user =  userName.getText().toString().trim();
        String pass =  passWord.getText().toString().trim();

        long res = dbHelper.addUser(user,pass);

        if(res>0){
            Toast toast = Toast.makeText(this,"User Creation Successful",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this,"User Creation failed",Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void goTOLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}