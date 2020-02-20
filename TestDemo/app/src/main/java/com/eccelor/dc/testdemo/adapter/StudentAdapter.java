package com.eccelor.dc.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eccelor.dc.testdemo.R;
import com.eccelor.dc.testdemo.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyHolder>
{
    private ArrayList<Student> students;
    private Context context;

    public StudentAdapter(Context context, ArrayList<Student> students)
    {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_student_list_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        Student student = students.get(position);
        holder.labelName.setText("Name : " + student.name);
        holder.labelRollNo.setText("Roll No :" + student.rollNo);
    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView labelName;
        TextView labelRollNo;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            labelName = itemView.findViewById(R.id.labelName);
            labelRollNo = itemView.findViewById(R.id.labelRollNo);
        }
    }
}
