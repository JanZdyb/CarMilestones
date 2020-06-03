package com.example.localdatabasetesting;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Cars.db";
    private static final int DATABASE_VERSION = 1;

    private static final String MAIN_TABLE_NAME = "my_cars";
    private static final String COLUMN_ID = "ID";
    private static final String CAR_NAME = "name";
    private static final String CAR_MODEL = "model";
    private static final String CAR_MILEAGE = "mileage";
    private static final String CAR_FUEL = "fuel";
    private static final String CAR_ENGINE = "engine";
    private static final String CAR_VIN = "vin";

    private static final String INFO_TABLE_NAME = "cars_info";
    private static final String CAR_ID = "car_id";
    private static final String BASIC_CAR_INFO = "bcar_info";
    private static final String ALL_CAR_INFO = "acar_info";
    private static final String CURR_DATE = "curr_date";



    public MyDatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_main_table_query = "CREATE TABLE IF NOT EXISTS " + MAIN_TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAR_NAME + " TEXT, " +
                    CAR_MODEL + " TEXT, " +
                    CAR_MILEAGE + " INTEGER, " +
                    CAR_FUEL + " TEXT, " +
                    CAR_ENGINE + " TEXT, " +
                    CAR_VIN + " TEXT);";

        String create_info_table_query = "CREATE TABLE IF NOT EXISTS " + INFO_TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAR_ID + " TEXT, " +
                    BASIC_CAR_INFO + " TEXT, " +
                    ALL_CAR_INFO + " TEXT, " +
                    CURR_DATE + " TEXT);";
        db.execSQL(create_main_table_query);
        db.execSQL(create_info_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + INFO_TABLE_NAME);
        onCreate(db);
    }

    void addCar(String carname, String carmodel, int carmileage, String carfuel, String carengine, String carvin){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CAR_NAME, carname);
        cv.put(CAR_MODEL, carmodel);
        cv.put(CAR_MILEAGE, carmileage);
        cv.put(CAR_FUEL, carfuel);
        cv.put(CAR_ENGINE, carengine);
        cv.put(CAR_VIN, carvin);
        
        long result = db.insert(MAIN_TABLE_NAME, null, cv);
        if(result == -1){

        }
        else{

        }
    }

    void addInfo(String carid, String bcar_info, String acar_info, String curr_date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CAR_ID, carid);
        cv.put(BASIC_CAR_INFO, bcar_info);
        cv.put(ALL_CAR_INFO, acar_info);
        cv.put(CURR_DATE, curr_date);

        long result = db.insert(INFO_TABLE_NAME, null, cv);
        if(result == -1){

        }
        else{

        }
    }

    void deleteDatabase()
    {
        context.deleteDatabase("Cars.db");
    }

    Cursor readAllDataFromMainTable()
    {
        String query = "SELECT * FROM " + MAIN_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readDataFromCarInfoTable(String id)
    {
        String query = "SELECT * FROM cars_info WHERE CAR_ID like \"" + id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateMileage(String row_id, String mileage)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CAR_MILEAGE, mileage);

        long result = db.update(MAIN_TABLE_NAME, cv, "ID=?", new String[]{row_id});
        if(result == -1)
        {
          //  Toast.makeText(context, "update failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
          //  Toast.makeText(context, "update succesful", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteFromCarsInfo(String row_id)
    {
        String query = "DELETE FROM cars_info WHERE id like \"" + row_id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
}
