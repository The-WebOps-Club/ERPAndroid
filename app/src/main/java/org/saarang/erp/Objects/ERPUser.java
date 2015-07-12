package org.saarang.erp.Objects;

import android.content.ContentValues;

/**
 * Created by Ahammad on 02/07/15.
 */
public class ERPUser {
    String _id, name, alternateNumber, phoneNumber, summerLocation, city, role, email, roomNumber,
        rollNumber;
    ERPWall[] dept, subDept;

    public static String TABLE_NAME = "ERPUsers";

    public static String KEY_ROWID = "_id";
    public static String COLUMN_USERID = "userId";
    public static String COLUMN_DEPT = "dept";
    public static String COLUMN_SUBDEPT = "subdept";
    public static String COLUMN_PHONENUMBER = "phoneNumber";
    public static String COLUMN_ALTPHONENUMBER = "alternatePhoneNumber";
    public static String COLUMN_SUMMER_LOCATION = "summerLocation";
    public static String COLUMN_CITY = "city";
    public static String COLUMN_ROLE = "role";
    public static String COLUMN_EMAIL = "email";
    public static String COLUMN_ROOMNUMBER = "roomNumber";
    public static String COLUMN_ROLLNUMBER = "rollNumber";
    public static String COLUMN_NAME = "name";

    public static String ERPNEWSFEED_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_USERID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_DEPT + " TEXT  , " +
            COLUMN_SUBDEPT + " TEXT  , " +
            COLUMN_PHONENUMBER + " TEXT  , " +
            COLUMN_ALTPHONENUMBER + " TEXT  , " +
            COLUMN_SUMMER_LOCATION + " TEXT  , " +
            COLUMN_CITY + " TEXT  , " +
            COLUMN_ROLE + " TEXT  , " +
            COLUMN_EMAIL + " TEXT  , " +
            COLUMN_ROOMNUMBER + " TEXT  , " +
            COLUMN_ROLLNUMBER + " TEXT  , " +
            COLUMN_NAME + " TEXT " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_USERID, COLUMN_DEPT , COLUMN_SUBDEPT,
            COLUMN_PHONENUMBER, COLUMN_ALTPHONENUMBER, COLUMN_SUMMER_LOCATION, COLUMN_CITY ,
            COLUMN_ROLE, COLUMN_EMAIL, COLUMN_ROOMNUMBER, COLUMN_ROLLNUMBER, COLUMN_NAME
    };

    public ContentValues getCV(){
        ContentValues cv = new ContentValues();

        return cv;
    }



    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSummerLocation() {
        return summerLocation;
    }

    public void setSummerLocation(String summerLocation) {
        this.summerLocation = summerLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public ERPWall[] getDept() {
        return dept;
    }

    public void setDept(ERPWall[] dept) {
        this.dept = dept;
    }

    public ERPWall[] getSubDept() {
        return subDept;
    }

    public void setSubDept(ERPWall[] subDept) {
        this.subDept = subDept;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
