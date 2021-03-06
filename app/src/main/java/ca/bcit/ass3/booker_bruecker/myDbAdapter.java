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

    public String getNameSearch(String searchString) {
        String[] whereArgs = new String[] {
               "%" + searchString + "%"
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.DATE, myDbHelper.TIME};
        Cursor cursor = db.query(true, myDbHelper.TABLE_NAME, columns, myDbHelper.NAME + " LIKE ?", whereArgs, null, null, null, null);
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

    public int updateDetails(String eventDetailID, String name, String unit, String quantity, String eventID) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ITEM_NAME, name);
        contentValues.put(myDbHelper.ITEM_UNIT, unit);
        contentValues.put(myDbHelper.ITEM_QUAN, quantity);
        contentValues.put(myDbHelper.EVENT_ID, eventID);
        String[] whereArgs = {eventDetailID};
        int count = db.update(myDbHelper.TABLE_NAME_DETAILS, contentValues, myDbHelper.UID + " = ?", whereArgs);
        return count;
    }

    public String getItemName(String eventDetailsID) {
        String[] whereArgs = new String[] {
                eventDetailsID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.ITEM_NAME};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME_DETAILS, columns, myDbHelper.UID + "=?", whereArgs, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_NAME));
            buffer.append("" + name);
        }
        return buffer.toString();
    }

    public String getItemUnit(String eventDetailsID) {
        String[] whereArgs = new String[] {
                eventDetailsID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.ITEM_UNIT};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME_DETAILS, columns, myDbHelper.UID + "=?", whereArgs, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            String unit = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_UNIT));
            buffer.append("" + unit);
        }
        return buffer.toString();
    }

    public String getItemQuantity(String eventDetailsID) {
        String[] whereArgs = new String[] {
                eventDetailsID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.ITEM_QUAN};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME_DETAILS, columns, myDbHelper.UID + "=?", whereArgs, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            String quantity = cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_QUAN));
            buffer.append("" + quantity);
        }
        return buffer.toString();
    }

    public String getName(String eventID) {
        String[] whereArgs = new String[] {
                eventID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, myDbHelper.UID + "=?", whereArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            buffer.append("" + name);
        }
        return buffer.toString();
    }

    public String getDate(String eventID) {
        String[] whereArgs = new String[] {
                eventID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.DATE};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, myDbHelper.UID + "=?", whereArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            buffer.append("" + date);
        }
        return buffer.toString();
    }

    public String getTime(String eventID) {
        String[] whereArgs = new String[] {
                eventID
        };
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.TIME};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, myDbHelper.UID + "=?", whereArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex(myDbHelper.TIME));
            buffer.append("" + time);
        }
        return buffer.toString();
    }

    public int updateEvent(String eventID, String name, String date, String time) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.DATE, date);
        contentValues.put(myDbHelper.TIME, time);
        String[] whereArgs = {eventID};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.UID + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "EVENTS";    // Database Name
        private static final String TABLE_NAME = "EVENTS";   // Table Name
        private static final String TABLE_NAME_DETAILS = "EVENT_DETAILS";   // Table Name
        private static final int DATABASE_Version = 12;    // Database Version

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
                ContentValues contentValues = new ContentValues();
                contentValues.put(myDbHelper.NAME, "Christmas Potluck");
                contentValues.put(myDbHelper.DATE, "12.25.17");
                contentValues.put(myDbHelper.TIME, "6:30");
                long id = db.insert(myDbHelper.TABLE_NAME, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Paper Plates");
                contentValues.put(myDbHelper.ITEM_UNIT, "Pieces");
                contentValues.put(myDbHelper.ITEM_QUAN, "20");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Paper Cups");
                contentValues.put(myDbHelper.ITEM_UNIT, "Pieces");
                contentValues.put(myDbHelper.ITEM_QUAN, "20");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Napkins");
                contentValues.put(myDbHelper.ITEM_UNIT, "Pieces");
                contentValues.put(myDbHelper.ITEM_QUAN, "100");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Paper Plates");
                contentValues.put(myDbHelper.ITEM_UNIT, "Pieces");
                contentValues.put(myDbHelper.ITEM_QUAN, "20");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Beer");
                contentValues.put(myDbHelper.ITEM_UNIT, "6 Pack");
                contentValues.put(myDbHelper.ITEM_QUAN, "5");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Pop");
                contentValues.put(myDbHelper.ITEM_UNIT, "2 Liter Bottles");
                contentValues.put(myDbHelper.ITEM_QUAN, "3");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Pizza");
                contentValues.put(myDbHelper.ITEM_UNIT, "Large");
                contentValues.put(myDbHelper.ITEM_QUAN, "3");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);

                contentValues = new ContentValues();
                contentValues.put(myDbHelper.ITEM_NAME, "Peanuts");
                contentValues.put(myDbHelper.ITEM_UNIT, "Grams");
                contentValues.put(myDbHelper.ITEM_QUAN, "200");
                contentValues.put(myDbHelper.EVENT_ID, id);
                db.insert(myDbHelper.TABLE_NAME_DETAILS, null, contentValues);
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