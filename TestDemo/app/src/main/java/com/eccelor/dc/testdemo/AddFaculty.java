package com.eccelor.dc.testdemo;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.EditText;


import com.eccelor.dc.testdemo.database.Operation;
import com.eccelor.dc.testdemo.model.Faculty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;



public class AddFaculty extends AppCompatActivity
{

    private EditText textName;
    private EditText textDesignation;
    private EditText textPosition;
    private EditText textExpert;
    private EditText textEmail;
    private EditText textPhone;
    private FloatingActionButton fabAddFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        textName = findViewById(R.id.textName);
        textDesignation = findViewById(R.id.textDesignation);
        textPosition = findViewById(R.id.textPosition);
        textExpert = findViewById(R.id.textExpert);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textMobile);
        fabAddFaculty = findViewById(R.id.fadAddFaculty);

        fabAddFaculty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = textName.getText().toString().trim();
                String designation = textDesignation.getText().toString().trim();
                String pos = textPosition.getText().toString().trim();
                String expert = textExpert.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String phone = textPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name))
                {
                    textName.setError("Enter Name");
                    textName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(designation))
                {
                    textDesignation.setError("Enter Designation");
                    textDesignation.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pos))
                {
                    textPosition.setError("Enter Position");
                    textPosition.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(expert))
                {
                    textExpert.setError("Enter Expert");
                    textExpert.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email))
                {
                    textEmail.setError("Enter Email");
                    textEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phone))
                {
                    textPhone.setError("Enter Phone");
                    textPhone.requestFocus();
                    return;
                }
                else
                {

                    Faculty faculty = new Faculty();
                    faculty.id = UUID.randomUUID().toString();
                    faculty.name = name;
                    faculty.designation = designation;
                    faculty.position = pos;
                    faculty.expert = expert;
                    faculty.email = email;
                    faculty.phone = phone;

                    Operation.insertFaculty(AddFaculty.this, faculty);
                    setResult(RESULT_OK);
                    finish();

                }
            }
        });
    }
}
