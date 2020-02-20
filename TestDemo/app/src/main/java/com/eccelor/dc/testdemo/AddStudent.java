package com.eccelor.dc.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.eccelor.dc.testdemo.database.Operation;
import com.eccelor.dc.testdemo.model.Faculty;
import com.eccelor.dc.testdemo.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

public class AddStudent extends AppCompatActivity
{

    private EditText textName;
    private EditText textStream;
    private EditText textCourse;
    private EditText textRollNo;
    private FloatingActionButton fabAddStudent;
    private Faculty faculty;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        faculty = new Faculty();
        textName = findViewById(R.id.textName);
        textStream = findViewById(R.id.textStream);
        textCourse = findViewById(R.id.textCourse);
        textRollNo = findViewById(R.id.textRollNo);
        fabAddStudent = findViewById(R.id.fadAddStudent);

        Intent intent = getIntent();
        if (intent != null)
        {
            faculty = intent.getParcelableExtra("faculty");
            Log.e("id", faculty.name);
        }

        fabAddStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = textName.getText().toString().trim();
                String stream = textStream.getText().toString().trim();
                String course = textCourse.getText().toString().trim();
                String rollNo = textRollNo.getText().toString().trim();

                if (TextUtils.isEmpty(name))
                {
                    textName.setError("Enter student name");
                    textName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(stream))
                {
                    textStream.setError("Enter Stream");
                    textStream.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(course))
                {
                    textCourse.setError("Enter Course");
                    textCourse.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(rollNo))
                {
                    textRollNo.setError("Enter RollNo");
                    textRollNo.requestFocus();
                    return;
                }
                else
                {
                    Student student = new Student();
                    student.id = UUID.randomUUID().toString();
                    student.facultyId = faculty.id;
                    student.name = name;
                    student.stream = stream;
                    student.course = course;
                    student.rollNo = rollNo;

                    Operation.insertStudent(AddStudent.this, student);
                    setResult(RESULT_OK);
                    finish();

                }
            }
        });
    }

}
