package com.eccelor.dc.testdemo.database;

public class Schema
{
    public class BaseColums
    {
        public static final String ID = "id";
        public static final String DELETE_DATE = "DeleteDate";
        public static final String INSERT_DATE = "InsertDate";
        public static final String UPDATE_DATE = "UpdateDate";
        public static final String IS_DELETED = "IsDeleted";
    }

    public class SSM_Faculty extends BaseColums
    {
        public static final String TABLE = "Faculty";
        public static final String NAME = "Name";
        public static final String DESIGNATION = "Designation";
        public static final String POSITION = "Position";
        public static final String EXPERT = "Expert";
        public static final String EMAIL = "Email";
        public static final String PHONE = "Phone";
    }

    public class SSM_Student extends BaseColums
    {
        public static final String TABLE = "Student";
        public static final String FACULTY_ID = "FacultyId";
        public static final String NAME = "Name";
        public static final String STREAM = "Stream";
        public static final String COURSE = "Course";
        public static final String ROLLNO = "RollNo";
    }

}
