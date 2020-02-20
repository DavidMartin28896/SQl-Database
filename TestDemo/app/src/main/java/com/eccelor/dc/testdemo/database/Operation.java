package com.eccelor.dc.testdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eccelor.dc.testdemo.model.Faculty;
import com.eccelor.dc.testdemo.model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Operation
{
    public static void createFacultyTable(SQLiteDatabase database)
    {
        String createTable = "CREATE TABLE " + Schema.SSM_Faculty.TABLE + " (" +
                Schema.SSM_Faculty.ID + " TEXT NOT NULL PRIMARY KEY, " +
                Schema.SSM_Faculty.NAME + " TEXT NOT NULL, " +
                Schema.SSM_Faculty.DESIGNATION + " TEXT NOT NULL, " +
                Schema.SSM_Faculty.POSITION + " TEXT NOT NULL, " +
                Schema.SSM_Faculty.EXPERT + " TEXT NOT NULL, " +
                Schema.SSM_Faculty.EMAIL + " TEXT NOT NULL, " +
                Schema.SSM_Faculty.PHONE + " TEXT NOT NULL);";
        database.execSQL(createTable);
    }

    public static void createStudentTable(SQLiteDatabase database)
    {
        String createTable = "CREATE TABLE " + Schema.SSM_Student.TABLE + " (" +
                Schema.SSM_Student.ID + " TEXT NOT NULL PRIMARY KEY, " +
                Schema.SSM_Student.FACULTY_ID + " TEXT NOT NULL, " +
                Schema.SSM_Student.NAME + " TEXT NOT NULL, " +
                Schema.SSM_Student.STREAM + " TEXT NOT NULL, " +
                Schema.SSM_Student.COURSE + " TEXT NOT NULL, " +
                Schema.SSM_Student.ROLLNO + " TEXT NOT NULL);";
        database.execSQL(createTable);
    }

    public static SQLiteDatabase getReadable(Context context)
    {
        return Database.getInstance(context).getReadableDatabase();
    }

    public static SQLiteDatabase getWritable(Context context)
    {
        return Database.getInstance(context).getWritableDatabase();
    }

    public static void clearDatabase(Context context)
    {
        SQLiteDatabase database = getWritable(context);
        database.delete(Schema.SSM_Faculty.TABLE, null, null);
        database.delete(Schema.SSM_Student.TABLE, null, null);
    }

    public static void insertFaculty(Context context, Faculty faculty)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.SSM_Faculty.ID, faculty.id);
        contentValues.put(Schema.SSM_Faculty.NAME, faculty.name);
        contentValues.put(Schema.SSM_Faculty.DESIGNATION, faculty.designation);
        contentValues.put(Schema.SSM_Faculty.POSITION, faculty.position);
        contentValues.put(Schema.SSM_Faculty.EXPERT, faculty.expert);
        contentValues.put(Schema.SSM_Faculty.EMAIL, faculty.email);
        contentValues.put(Schema.SSM_Faculty.PHONE, faculty.phone);
        SQLiteDatabase database = getWritable(context);
        Long affected = database.insert(Schema.SSM_Faculty.TABLE, null, contentValues);
        Log.e("tableF", affected + " ");
    }

    public static void insertStudent(Context context, Student student)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.SSM_Student.ID, student.id);
        contentValues.put(Schema.SSM_Student.FACULTY_ID, student.facultyId);
        contentValues.put(Schema.SSM_Student.NAME, student.name);
        contentValues.put(Schema.SSM_Student.STREAM, student.stream);
        contentValues.put(Schema.SSM_Student.COURSE, student.course);
        contentValues.put(Schema.SSM_Student.ROLLNO, student.rollNo);
        SQLiteDatabase database = getWritable(context);
        Long affected = database.insert(Schema.SSM_Student.TABLE, null, contentValues);
        Log.e("tableS", affected + " ");
    }

    public static ArrayList<Faculty> getFacultyList(Context context)
    {
        SQLiteDatabase database = getReadable(context);
        ArrayList<Faculty> faculties = new ArrayList<>();

        Cursor cursor = database.query(Schema.SSM_Faculty.TABLE, null, null, null, null, null, Schema.SSM_Faculty.ID + " DESC");
        try
        {
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
            {
                do
                {
                    Faculty faculty = new Faculty();
                    faculty.parseFromCursor(cursor);
                    faculties.add(faculty);
                } while (cursor.moveToNext());
                return faculties;
            }
        }
        catch (Exception e)
        {
            Log.e("exe", e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static ArrayList<Student> getStudentList(Context context, String facultyId)
    {
        SQLiteDatabase database = getReadable(context);
        ArrayList<Student> students = new ArrayList<>();

        Cursor cursor = database.query(Schema.SSM_Student.TABLE, null, Schema.SSM_Student.FACULTY_ID + " = ?", new String[]{facultyId}, null, null, Schema.SSM_Faculty.ID + " DESC");
        try
        {
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
            {
                do
                {
                    Student student = new Student();
                    student.parseFromCursor(cursor);
                    students.add(student);

                } while (cursor.moveToNext());
                return students;
            }
        }
        catch (Exception e)
        {
            Log.e("exe", e.getMessage());
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static void updateFaculty(Context context, Faculty faculty)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.SSM_Faculty.NAME, faculty.name);
        contentValues.put(Schema.SSM_Faculty.DESIGNATION, faculty.designation);
        contentValues.put(Schema.SSM_Faculty.POSITION, faculty.position);
        contentValues.put(Schema.SSM_Faculty.EXPERT, faculty.expert);
        contentValues.put(Schema.SSM_Faculty.EMAIL, faculty.email);
        contentValues.put(Schema.SSM_Faculty.PHONE, faculty.phone);

        String where = Schema.SSM_Faculty.ID + " = ?";
        String[] whereArgs = new String[]{faculty.id};
        SQLiteDatabase database = getWritable(context);
        database.update(Schema.SSM_Faculty.TABLE, contentValues, where, whereArgs);
    }

    public static void deleteFaculty(Context context, String id)
    {
        SQLiteDatabase database = getWritable(context);
        String where = Schema.SSM_Faculty.ID + " = ?";
        String[] whereArgs = new String[]{id};
        database.delete(Schema.SSM_Faculty.TABLE, where, whereArgs);
    }


}
