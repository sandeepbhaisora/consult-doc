package com.example.consultdoc.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.consultdoc.DbHelper;
import com.example.consultdoc.MainActivity;
import com.example.consultdoc.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userName;

    DbHelper dbHelper;

    TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signUp(View view){

        Intent intent =new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);

    }

    public void loginNow(View view){

        userName = findViewById(R.id.login_username);

        password = findViewById(R.id.login_password);

        String user = userName.getText().toString().trim();

        String pass = password.getText().toString().trim();

        dbHelper = new DbHelper(this);

        Boolean isSuccess = dbHelper.checkUser(user,pass);

        if(isSuccess){
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast toast = Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(this,"Login failed either username or password is incorrect ,please try again",Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}