package com.eccelor.dc.testdemo.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.eccelor.dc.testdemo.database.Schema;

public class Student implements Parcelable
{
    public String id;
    public String facultyId;
    public String name;
    public String stream;
    public String course;
    public String rollNo;


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeString(this.facultyId);
        dest.writeString(this.name);
        dest.writeString(this.stream);
        dest.writeString(this.course);
        dest.writeString(this.rollNo);
    }

    public Student()
    {
    }

    protected Student(Parcel in)
    {
        this.id = in.readString();
        this.facultyId = in.readString();
        this.name = in.readString();
        this.stream = in.readString();
        this.course = in.readString();
        this.rollNo = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>()
    {
        @Override
        public Student createFromParcel(Parcel source)
        {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size)
        {
            return new Student[size];
        }
    };

    public void parseFromCursor(Cursor cursor)
    {
        int indexId = cursor.getColumnIndex(Schema.SSM_Student.ID);
        if (!cursor.isNull(indexId))
            this.id = cursor.getString(indexId);

        int indexFId = cursor.getColumnIndex(Schema.SSM_Student.FACULTY_ID);
        if (!cursor.isNull(indexFId))
            this.facultyId = cursor.getString(indexId);

        int indexName = cursor.getColumnIndex(Schema.SSM_Student.NAME);
        if (!cursor.isNull(indexName))
            this.name = cursor.getString(indexName);

        int indexStream = cursor.getColumnIndex(Schema.SSM_Student.STREAM);
        if (!cursor.isNull(indexStream))
            this.stream = cursor.getString(indexStream);

        int indexCourse = cursor.getColumnIndex(Schema.SSM_Student.COURSE);
        if (!cursor.isNull(indexCourse))
            this.course = cursor.getString(indexCourse);

        int indexRollNo = cursor.getColumnIndex(Schema.SSM_Student.ROLLNO);
        if (!cursor.isNull(indexRollNo))
            this.rollNo = cursor.getString(indexRollNo);
    }
}
