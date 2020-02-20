package com.eccelor.dc.testdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eccelor.dc.testdemo.adapter.FacultyAdapter;
import com.eccelor.dc.testdemo.adapter.StudentAdapter;
import com.eccelor.dc.testdemo.database.Operation;
import com.eccelor.dc.testdemo.model.Faculty;
import com.eccelor.dc.testdemo.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentsList extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private StudentAdapter adapter;
    private ArrayList<Student> students;
    private Faculty faculty;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        faculty = new Faculty();
        students = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        Intent intent = getIntent();
        if (intent != null)
        {
            faculty = intent.getParcelableExtra("faculty");
            Log.e("id", faculty.name);
            toolbar.setTitle(faculty.name);
        }

        students = Operation.getStudentList(StudentsList.this, faculty.id);

        if (students != null)
        {
            adapter = new StudentAdapter(StudentsList.this, students);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        }


        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(StudentsList.this, AddStudent.class);
                intent.putExtra("faculty", faculty);
                startActivityForResult(intent, 202);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 202)
        {
            if (resultCode == RESULT_OK)
            {
                students = Operation.getStudentList(StudentsList.this, faculty.id);
                adapter = new StudentAdapter(StudentsList.this, students);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
            }
            else
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }
}
