package com.eccelor.dc.testdemo.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.eccelor.dc.testdemo.database.Schema;

public class Faculty implements Parcelable
{
    public String id;
    public String name;
    public String designation;
    public String position;
    public String expert;
    public String email;
    public String phone;


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.designation);
        dest.writeString(this.position);
        dest.writeString(this.expert);
        dest.writeString(this.email);
        dest.writeString(this.phone);
    }

    public Faculty()
    {
    }

    protected Faculty(Parcel in)
    {
        this.id = in.readString();
        this.name = in.readString();
        this.designation = in.readString();
        this.position = in.readString();
        this.expert = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<Faculty> CREATOR = new Parcelable.Creator<Faculty>()
    {
        @Override
        public Faculty createFromParcel(Parcel source)
        {
            return new Faculty(source);
        }

        @Override
        public Faculty[] newArray(int size)
        {
            return new Faculty[size];
        }
    };

    public void parseFromCursor(Cursor cursor)
    {
        int indexId = cursor.getColumnIndex(Schema.SSM_Faculty.ID);
        if (!cursor.isNull(indexId))
            this.id = cursor.getString(indexId);
        int indexName = cursor.getColumnIndex(Schema.SSM_Faculty.NAME);
        if (!cursor.isNull(indexName))
            this.name = cursor.getString(indexName);
        int indexDesignation = cursor.getColumnIndex(Schema.SSM_Faculty.DESIGNATION);
        if (!cursor.isNull(indexDesignation))
            this.designation = cursor.getString(indexDesignation);
        int indexPosition = cursor.getColumnIndex(Schema.SSM_Faculty.POSITION);
        if (!cursor.isNull(indexPosition))
            this.position = cursor.getString(indexPosition);
        int indexExpert = cursor.getColumnIndex(Schema.SSM_Faculty.EXPERT);
        if (!cursor.isNull(indexExpert))
            this.expert = cursor.getString(indexExpert);
        int indexEmail = cursor.getColumnIndex(Schema.SSM_Faculty.EMAIL);
        if (!cursor.isNull(indexEmail))
            this.email = cursor.getString(indexEmail);
        int indexPhone = cursor.getColumnIndex(Schema.SSM_Faculty.PHONE);
        if (!cursor.isNull(indexPhone))
            this.phone = cursor.getString(indexPhone);
    }
}


/*
 1.diff- synch and Asynch
 2.push notification work
 3.sqlite all query and different primary and unique key
 4.all library used like pdf,excel sheet
 5.apk uploaded step
 */