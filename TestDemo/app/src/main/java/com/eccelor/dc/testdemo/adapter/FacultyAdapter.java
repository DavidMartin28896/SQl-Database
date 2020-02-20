package com.eccelor.dc.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eccelor.dc.testdemo.R;
import com.eccelor.dc.testdemo.model.Faculty;

import java.util.ArrayList;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.MyHolder>
{
    private Context context;
    private ArrayList<Faculty> faculties;
    private onItemClick onItemClick;

    public FacultyAdapter(Context context, ArrayList<Faculty> faculties)
    {
        this.context = context;
        this.faculties = faculties;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_faculty_list_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position)
    {
        Faculty faculty = faculties.get(position);

        holder.labelName.setText("Name : " + faculty.name);
        holder.labelPosition.setText("Position : " + faculty.position);
        holder.labelExpert.setText("Expert : " + faculty.expert);

        holder.imageViewUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (onItemClick != null) onItemClick.itemUpdate(holder, position);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return faculties.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView labelName;
        private TextView labelPosition;
        private TextView labelExpert;
        private ImageView imageViewUpdate;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewUpdate = itemView.findViewById(R.id.imageUpdate);
            labelName = itemView.findViewById(R.id.labelName);
            labelPosition = itemView.findViewById(R.id.labelPosition);
            labelExpert = itemView.findViewById(R.id.labelExpert);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (onItemClick != null) onItemClick.itemClick(getAdapterPosition(), view);
        }
    }

    public void setOnClickListener(onItemClick itemClick)
    {
        this.onItemClick = itemClick;
    }


    public interface onItemClick
    {
        void itemClick(int position, View view);

        void itemUpdate(MyHolder holder, int position);
    }

}
