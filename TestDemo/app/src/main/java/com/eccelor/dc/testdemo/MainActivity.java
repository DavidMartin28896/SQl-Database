package com.eccelor.dc.testdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.eccelor.dc.testdemo.adapter.FacultyAdapter;
import com.eccelor.dc.testdemo.database.Operation;
import com.eccelor.dc.testdemo.model.Faculty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements FacultyAdapter.onItemClick
{

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ArrayList<Faculty> faculties;
    private FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faculties = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, AddFaculty.class);
                startActivityForResult(intent, 101);
            }
        });

        faculties = Operation.getFacultyList(MainActivity.this);


        if (faculties != null && faculties.size() > 1)
        {
            adapter = new FacultyAdapter(MainActivity.this, faculties);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
            adapter.setOnClickListener(this);
        }
        else
            Toast.makeText(this, "No records..", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void itemClick(int position, View view)
    {
        Faculty faculty = faculties.get(position);
        Intent intent = new Intent(MainActivity.this, StudentsList.class);
        intent.putExtra("faculty", faculty);
        startActivity(intent);
    }

    @Override
    public void itemUpdate(FacultyAdapter.MyHolder holder, int position)
    {
        Faculty faculty = faculties.get(position);
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("faculty", faculty);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101)
        {
            if (resultCode == RESULT_OK)
            {
                faculties = Operation.getFacultyList(MainActivity.this);
                adapter = new FacultyAdapter(MainActivity.this, faculties);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                adapter.setOnClickListener(this);
            }
            else
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }
}
