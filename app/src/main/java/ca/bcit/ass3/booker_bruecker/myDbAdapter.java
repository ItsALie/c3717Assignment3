package ca.bcit.ass3.booker_bruecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    public long insertEvent(String name, String date, String time) {
        SQLiteDatabase dbb = myhelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.DATE, date);
        contentValues.put(myDbHelper.TIME, time);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertEventDetails(String name, String unit, String quantity, String eventID) {
        SQLiteDatabase dbb = myhelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ITEM_NAME, name);
        contentValues.put(myDbHelper.ITEM_UNIT, unit);
        contentValues.put(myDbHelper.ITEM_QUAN, quantity);
        contentValues.put(myDbHelper.EVENT_ID, eventID);
        long id = dbb.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.DATE, myDbHelper.TIME};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String date = cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String time = cursor.getString(cursor.getColumnIndex(myDbHelper.TIME));
            buffer.append(cid + "   " + name + "   " + date + " " + time + " \n");
        }
        return buffer.toString();
    }

    public String getDetailData(String eventID) {
        String[] whereArgs = new String[] {
                eventID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.ITEM_NAME, myDbHelper.ITEM_UNIT, myDbHelper.ITEM_QUAN, myDbHelper.EVENT_ID};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME_DETAILS, columns, myDbHelper.EVENT_ID + "=?", whereArgs, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_NAME));
            String unit = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_UNIT));
            String quantity = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_QUAN));
            buffer.append(cid + "   " + name + "   " + unit + " " + quantity + " \n");
        }
        return buffer.toString();
    }

    public int delete(String eventID) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {eventID};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.UID + " = ?", whereArgs);
        count += db.delete(myDbHelper.TABLE_NAME_DETAILS, myDbHelper.EVENT_ID + " = ?", whereArgs);
        return count;
    }

    public int deleteDetails(String eventDetailID) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {eventDetailID};

        int count = db.delete(myDbHelper.TABLE_NAME_DETAILS, myDbHelper.UID + " = ?", whereArgs);
        return count;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.NAME + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "EVENTS";    // Database Name
        private static final String TABLE_NAME = "EVENTS";   // Table Name
        private static final String TABLE_NAME_DETAILS = "EVENT_DETAILS";   // Table Name
        private static final int DATABASE_Version = 8;    // Database Version

        private static final String UID = "_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String DATE = "Date";    // Column III
        private static final String TIME = "Time";    // Column IV

        private static final String  ITEM_NAME= "ItemName";    //Column I
        private static final String ITEM_UNIT = "ItemUnit";    //Column II
        private static final String ITEM_QUAN = "ItemQuantity";    //Column III
        private static final String EVENT_ID = "eventID";    //Column II

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + DATE + " VARCHAR(255) ," + TIME + " VARCHAR(225));";
        private static final String CREATE_TABLE_DETAIL = "CREATE TABLE " + TABLE_NAME_DETAILS +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " VARCHAR(255) ," + ITEM_UNIT + " VARCHAR(255) ," + ITEM_QUAN + " VARCHAR(225) ," + EVENT_ID + " INTEGER, " + " FOREIGN KEY ("+EVENT_ID+") REFERENCES "+TABLE_NAME+"("+UID+"));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String DROP_TABLE_DETAILS = "DROP TABLE IF EXISTS " + TABLE_NAME_DETAILS;

        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE_DETAIL);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "OnUpgrade", Toast.LENGTH_LONG).show();
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE_DETAILS);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
            }
        }
    }
}