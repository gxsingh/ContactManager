package com.example.saber.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Garry_12 on 11/12/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DB_VER = 1;

    private static final String DB_NAME = "ContactManagerrrr",
    TABLE_CONTACT ="contacts",
    KEYID = "id",
    KEYNAME ="name",
    KEYPHONE = "phone",
            KEYWPHONE = "wphone",
            KEYOPHONE = "ophone",
    KEYEMAIL = "email",
    KEYADDRESS = "address",
    KEYIMGURI = "imgURI";

    public Database(Context contxt)
        {
            super(contxt, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACT + "(" + KEYID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEYNAME + " TEXT," + KEYPHONE + " TEXT," + KEYWPHONE + " TEXT," + KEYOPHONE + " TEXT," + KEYEMAIL + " TEXT," + KEYADDRESS + " TEXT," + KEYIMGURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);

        onCreate(db);
    }

    /**
     * This method will insert new contact information
     * @param contact This is the first parameter from class Contact
     */
    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEYNAME, contact.getName());
        values.put(KEYPHONE, contact.getPhone());
        values.put(KEYWPHONE, contact.getWPhone());
        values.put(KEYOPHONE, contact.getOPhone());
        values.put(KEYEMAIL, contact.getEmail());
        values.put(KEYADDRESS, contact.getAddress());
        //Log.d("Image", contact.getURIimg().toString());
      //  values.put(KEYIMGURI,"android.resource://com.example.saber.contactmanager/drawable/user");

       values.put(KEYIMGURI,contact.getURIimg().toString());





      //  values.put(KEYIMGURI, contact.getURIimg().toString());

        //values.put(KEYIMGURI, "null");

        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }

    /**
     * This method will get contact information by looking it up from the Id
     * @param id this is the first parameter which is an Integer
     * @return Contact
     */
    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACT, new String[] { KEYID, KEYNAME, KEYPHONE,KEYWPHONE,KEYOPHONE, KEYEMAIL, KEYADDRESS, KEYIMGURI }, KEYID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), Uri.parse(cursor.getString(7)));
        db.close();
        cursor.close();
        return contact;
    }

    /**
     * This method will delete contact information
     * @param contact this is the first parameter which is from class Contact
     */
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
       db.delete(TABLE_CONTACT, KEYID + "=?", new String[] { String.valueOf(contact.getID()) });
        //db.delete(TABLE_CONTACT, KEYID + " = " + contact.getID(), null);
        db.close();
    }

    /**
     * This method will get the number of contacts in the sqlite database
     * @return integer
     */
    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    /**
     * This method will update contact information in the database
     * @param contact This is the first parameter which is from class Contact
     * @return Integer
     */
    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEYNAME, contact.getName());
        values.put(KEYPHONE, contact.getPhone());
        values.put(KEYWPHONE, contact.getWPhone());
        values.put(KEYOPHONE, contact.getOPhone());
        values.put(KEYEMAIL, contact.getEmail());
        values.put(KEYADDRESS, contact.getAddress());
        //Log.d("Image", contact.getURIimg().toString());
        //values.put(KEYIMGURI,"android.resource://com.example.saber.contactmanager/drawable/user");
      values.put(KEYIMGURI,contact.getURIimg().toString());





      // values.put(KEYIMGURI, contact.getURIimg().toString());
        //values.put(KEYIMGURI, "null");

        int rowsAffected = db.update(TABLE_CONTACT, values, KEYID + "=?", new String[]{String.valueOf(contact.getID())});
        db.close();

        return rowsAffected;
    }

    /**
     * This method will select contacts from the database to be made into a list
     * @return List<Contact>
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        SQLiteDatabase db = getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);

        String asc = "ORDER BY name ASC";

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT +" ORDER BY name ASC",null);




        if (cursor.moveToFirst()) {
            do {
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), Uri.parse(cursor.getString(7))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }


    /**
     * This method will export database into SD card
     */
    public void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.example.saber.contactmanager" +"/databases/"+DB_NAME;
        String backupDBPath = DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will export the database into a .csv file
     */
    public void expcsv(){

        SQLiteDatabase sqldb = getReadableDatabase(); //My Database class
        Cursor c = null;
        try {
            c = sqldb.rawQuery("select * from "+TABLE_CONTACT, null);
            int rowcount = 0;
            int colcount = 0;
          //  File sdCardDir = Environment.getExternalStorageDirectory();
            //File sdCardDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File sdCardDir = new File("/mnt/sdcard/");
            String filename = "MyBackUp.csv";
            // the name of the file to export with
            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();
                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {
                        bw.write(c.getColumnName(i) + ",");
                    } else {
                        bw.write(c.getColumnName(i));
                    }
                }
                bw.newLine();
                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);
                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();
                //infotext.setText("Exported Successfully.");
                MainActivity ttt = new MainActivity();
                Toast.makeText(ttt.getApplicationContext(),"LOL", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            if (sqldb.isOpen()) {
                sqldb.close();
                //infotext.setText(ex.getMessage().toString());
            }
        } finally {
        }





    }


    /**
     * This method will delete the contact database
     */
    public void deleteDB(){
        MainActivity temp = new MainActivity();
        boolean result = temp.deleteDatabase(DB_NAME);
        if(result == true)
        {
            Toast.makeText(temp.getApplicationContext(),"DB Deleted",Toast.LENGTH_LONG).show();
        }

    }

}
