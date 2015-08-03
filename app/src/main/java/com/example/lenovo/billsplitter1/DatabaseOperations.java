package com.example.lenovo.billsplitter1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by lenovo on 06-07-2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    private static final String DATABASE_NAME="billInfo";

    public static final String BILL_TABLE = "Bill";
    public static final String BILL_NUMBER = "Id";
    public static final String BILL_TYPE = "Type";
    public static final String BILL_DATE = "Date";
    public static final String BILL_AMOUNT = "Amount";

    public static final String FRIENDS_TABLE = "Friends";
    public static final String FRIENDS_ID = "Id";
    public static final String FRIENDS_NAME = "Name";
    public static final String FRIENDS_EMAIL = "Email";
    public static final String FRIENDS_PERCENT = "Percent";
    public static final String FRIENDS_AMOUNT = "Amount";

    //Every time getcount from collection
    //number of friends is already here can u show sqlite sample code

    public static String[] allBillColumns = {
            BILL_NUMBER,
            BILL_TYPE,
            BILL_DATE,
            BILL_AMOUNT
    };

    public static String[] allFriendsColumns = {
            FRIENDS_ID,
            FRIENDS_NAME,
            FRIENDS_EMAIL,
            FRIENDS_PERCENT,
            FRIENDS_AMOUNT
    };

    public DatabaseOperations(Context context) {
        super(context,DATABASE_NAME, null, database_version);

        Log.d("Database operations", "Database created");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ BILL_TABLE
                +" (" + BILL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BILL_TYPE + " TEXT, "
                + BILL_DATE +" TEXT, "
                + BILL_AMOUNT +" INTEGER);");

        db.execSQL("CREATE TABLE " + FRIENDS_TABLE + " ("
                + FRIENDS_ID + " INTEGER, "
                + FRIENDS_NAME + " TEXT, "
                + FRIENDS_EMAIL + " TEXT, "
                + FRIENDS_PERCENT + " INTEGER, "
                + FRIENDS_AMOUNT + " INTEGER);");
        Log.d("Database operations", "Table created");
        //db.execSQL(String.valueOf(count));

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    public int PutBillInformation(String BillType, String Date, int TotalAmount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BILL_TYPE, BillType);
        cv.put(BILL_DATE, Date);
        cv.put(BILL_AMOUNT, TotalAmount);
        long k = db.insert(BILL_TABLE, null, cv);
        Log.d("Database operations", "rows inserted Id: "+k);
        db.close();
        return (int) k;
    }

    public int PutFriendInformation(int id,String Name, String EmailId, int Percent, int Amount) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(FRIENDS_ID,id);
        cv1.put(FRIENDS_NAME, Name);
        cv1.put(FRIENDS_EMAIL, EmailId);
        cv1.put(FRIENDS_PERCENT, Percent);
        cv1.put(FRIENDS_AMOUNT, Amount);// extra field for storing split amount
        long k1 = db.insert(FRIENDS_TABLE, null, cv1);
        Log.d("Database operations", " rows inserted" +k1);
        db.close();
        return (int)k1;
    }

    //getting All Bill info
    public Cursor GetAllBillInformation() {//cursor is used here its wrong
        Cursor cr = null;
        SQLiteDatabase SQ = this.getReadableDatabase();
        cr= SQ.query(BILL_TABLE, allBillColumns,null, null, null, null, null);
        return cr;
    }

    //getting Bill Info accourding to billnumber
    public Cursor GetBillInformation(int billnumber) {//cursor is used here its wrong
        Cursor cr = null;
        SQLiteDatabase SQ = this.getReadableDatabase();
        cr= SQ.query(BILL_TABLE, allBillColumns,BILL_NUMBER + " = "+ billnumber, null, null, null, null);
        return cr;
     }

    //getting all Friends accourding to billnumber
    public Cursor GetFriendInformation(int billnumber) {
        Log.d("GetFriendInformation", "billnumber:: " +billnumber);
        Cursor cr1 = null;
        SQLiteDatabase SQ = this.getReadableDatabase();
        //cr1 = SQ.rawQuery("select * from Friends where Id = " + billnumber, null);
        cr1 = SQ.query(FRIENDS_TABLE, allFriendsColumns, FRIENDS_ID + " = " + billnumber, null, null, null, null);
        int count=cr1.getCount();
        Log.d("GetFriendInformation", "row:: " +count);
        if (cr1.moveToFirst()){
            do{
                String id = cr1.getString(1);
                Log.d("GetFriendInformation", "Name:: " +id);
            } while(cr1.moveToNext());
        }
        return cr1;
    }
}
