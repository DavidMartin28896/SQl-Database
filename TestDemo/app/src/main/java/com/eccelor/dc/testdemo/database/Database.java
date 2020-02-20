package com.eccelor.dc.testdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper
{
    private static final String NAME = "SSM";
    private static final int VERSION = 1;
    private static Database _instance;
    private Context context;

    public Database(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    public static synchronized Database getInstance(Context context)
    {
        if (_instance == null)
        {
            _instance = new Database(context);
        }
        return _instance;
    }

    public static synchronized void closeDatabse()
    {
        if (_instance != null)
        {
            _instance.close();
            _instance = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Operation.createFacultyTable(sqLiteDatabase);
        Operation.createStudentTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
