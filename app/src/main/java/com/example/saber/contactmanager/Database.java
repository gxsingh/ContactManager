package com.example.saber.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry_12 on 11/12/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DB_VER = 1;

    private static final String DB_NAME = "ContactManager",
    TABLE_CONTACT ="contacts",
    KEYID = "id",
    KEYNAME ="name",
    KEYPHONE = "phone",
    KEYEMAIL = "email",
    KEYADDRESS = "address",
    KEYIMGURI = "imgURI";

    public Database(Context contxt)
        {
            super(contxt, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACT + "(" + KEYID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEYNAME + " TEXT," + KEYPHONE + " TEXT," + KEYEMAIL + " TEXT," + KEYADDRESS + " TEXT," + KEYIMGURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);

        onCreate(db);
    }

    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEYNAME, contact.getName());
        values.put(KEYPHONE, contact.getPhone());
        values.put(KEYEMAIL, contact.getEmail());
        values.put(KEYADDRESS, contact.getAddress());
        //values.put(KEYIMGURI, contact.getURIimg().toString());
        if (contact.getURIimg() == null)
        {
        values.put(KEYIMGURI, ("android.resource://com.example.saber.contactmanager/res/layout/drawable/user.png" ));
        }
        else {
            values.put(KEYIMGURI, "null");
        }


        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACT, new String[] { KEYID, KEYNAME, KEYPHONE, KEYEMAIL, KEYADDRESS, KEYIMGURI }, KEYID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));
        db.close();
        cursor.close();
        return contact;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACT, KEYID + "=?", new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEYNAME, contact.getName());
        values.put(KEYPHONE, contact.getPhone());
        values.put(KEYEMAIL, contact.getEmail());
        values.put(KEYADDRESS, contact.getAddress());
        values.put(KEYIMGURI, contact.getURIimg().toString());

        int rowsAffected = db.update(TABLE_CONTACT, values, KEYID + "=?", new String[] { String.valueOf(contact.getID()) });
        db.close();

        return rowsAffected;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);

        if (cursor.moveToFirst()) {
            do {
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

}
