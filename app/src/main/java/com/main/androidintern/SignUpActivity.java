package com.main.androidintern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.androidintern.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;

    String stName,
            stMobile,
            stEmail,
            stPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());


        stName = signUpBinding.edtName.getText().toString();
        stMobile = signUpBinding.edtMobile.getText().toString();
        stEmail = signUpBinding.edtEmail.getText().toString();
        stPassword = signUpBinding.edtPassword.getText().toString();
    }
}