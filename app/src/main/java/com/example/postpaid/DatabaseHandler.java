package com.example.postpaid;
import java.util.ArrayList;
import java.util.List;
 



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PH_NO + " TEXT,"
        	+ KEY_TYPE + " TEXT,"  + KEY_DURATION + " TEXT,"+ KEY_DATE + " TEXT," 
            + KEY_TIME + " TEXT UNIQUE " + ")";
       // String CREATE_CONTACTS_TABLE = "CREATE TABLE contacts(id INTEGER PRIMARY KEY,phone_number TEXT,type TEXT,duration TEXT,date TEXT,time TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Number
        values.put(KEY_TYPE, contact.getType());        // Contact Type
        values.put(KEY_DURATION, contact.getDuration());   // Contact Duration
        values.put(KEY_DATE, contact.getDate());      // Contact Date
        values.put(KEY_TIME, contact.getTime());      // Contact Time
 
        // Inserting Row
        try{
        db.insertOrThrow(TABLE_CONTACTS, null, values);
        Log.d("entered", "value entered ");
        }catch(SQLiteConstraintException ex){
        	 Log.d("Not entered", "entered the exception");
        }
       // db.close(); // Closing database connection
    }
 
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_TYPE, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return contact
        return contact;
    }
     
    // Getting All Contacts by date wise
    public List<Contact> getAllContactsByDate(Contact contact1) {
        List<Contact> contactList = new ArrayList<Contact>();
         
        // Select All Query
       // String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        Log.d("data accessed", contact1.getStartDate()+"  "+contact1.getEndDate());
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" WHERE "+ KEY_DATE + " BETWEEN '"+contact1.getStartDate()+"' AND '"+contact1.getEndDate()+"'";
        //String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" WHERE "+ KEY_DATE + " < '"+contact1.getStartDate()+"' AND "+ KEY_DATE + " > '"+contact1.getEndDate()+"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0){
        	 Log.d("cursor", "value exist");
        }
        else
       	 Log.d("cursor", "value not exist");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setPhoneNumber(cursor.getString(1));
                contact.setType(cursor.getString(2));
                contact.setDuration(cursor.getString(3));
                Log.d("value", cursor.getString(2)+"  "+cursor.getString(3));
                contact.setDate(cursor.getString(4));
                contact.setTime(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
    
 // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
         
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
       // Log.d("data accessed", contact1.getStartDate()+"  "+contact1.getEndDate());
       // String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" WHERE "+ KEY_DATE + " BETWEEN '"+contact1.getStartDate()+"' AND '"+contact1.getEndDate()+"'";
        //String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" WHERE "+ KEY_DATE + " < '"+contact1.getStartDate()+"' AND "+ KEY_DATE + " > '"+contact1.getEndDate()+"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0){
        	 Log.d("cursor", "value exist");
        }
        else
       	 Log.d("cursor", "value not exist");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setPhoneNumber(cursor.getString(1));
                contact.setType(cursor.getString(2));
                contact.setDuration(cursor.getString(3));
                Log.d("value", cursor.getString(2)+"  "+cursor.getString(3));
                contact.setDate(cursor.getString(4));
                contact.setTime(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, contact.getType());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
       // db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}