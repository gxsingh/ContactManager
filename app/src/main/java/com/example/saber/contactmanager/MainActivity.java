package com.example.saber.contactmanager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.net.Uri;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
/**
 * Created by Sabur on 11/5/2015.
 */
public class MainActivity extends AppCompatActivity {

    public static Context mainContext;


    EditText contactName;
    EditText contactNumber;
    EditText contactWNumber;
    EditText contactONumber;
    EditText contactEmail;
    EditText contactAddress;
    ImageView contactPic;

    Button contactExpBut;

    static List<Contact> Contacts = new ArrayList<Contact>();

    ListView contactListView;
    //Uri imgURI = Uri.parse("android.resource://com.example.saber.contactmanager/drawable/user.png" );
    //Uri imgURI = Uri.parse("android.resource://com.example.saber.contactmanager/drawable/user");

    Uri imgURI = null;
    Database db_SQL;
    private static int contactID;
    public static int holdingItemIdx;
    //int holdingItemIdx;
    private boolean isEditMod;

    ArrayAdapter<Contact> contactAdapt;

    private static final int DELETE = 1, EDIT = 0, C_CALL = 2, C_MSG = 3;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
   // public static Context getMainContext(){
//
   //     return getApplicationContext();
//
   // }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
         setContentView(R.layout.content_main);

        contactName = (EditText) findViewById(R.id.contactName);
        contactNumber = (EditText) findViewById(R.id.contactNumber);

        contactWNumber = (EditText) findViewById(R.id.wPhone);
        contactONumber = (EditText) findViewById(R.id.oPhone);

        contactEmail = (EditText) findViewById(R.id.cEmail);
        contactAddress = (EditText) findViewById(R.id.contactAddress);
        contactListView = (ListView) findViewById(R.id.listView);

        contactPic = (ImageView) findViewById(R.id.imageViewContactPic);
        /////

        contactExpBut = (Button) findViewById(R.id.expBtn);

        /////
        MainActivity.mainContext = getApplicationContext();

        db_SQL = new Database(getApplicationContext());


        registerForContextMenu(contactListView);
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                holdingItemIdx = position;
                return false;
            }
        });


        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creatorTab);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabcontactList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        // registerForContextMenu(tabHost.getCurrentTabView());
        // tabHost.getCurrentTabView().setOnLongClickListener(new View.OnLongClickListener() {
//
        //                     @Override
        //                     public boolean onLongClick(View v) {
//
        //                         Toast.makeText(getApplicationContext(), "EXPORT", Toast.LENGTH_LONG).show();
        //                         return false;
        //                     }
        //   });

        contactExpBut.setOnClickListener(new View.OnClickListener() {
            /**
             * This method will process when the button is clicked
             * @param vw this is the first parameter of type View
             */
            public void onClick(View vw) {

               // db_SQL.exportDB();
               // db_SQL.expcsv();
            }

        });

        contactExpBut.setOnLongClickListener(new View.OnLongClickListener() {
            /**
             * This method will process when the button is held on for long time
             * @param vw this is the first parameter of type View
             * @return boolean
             */
            public boolean onLongClick(View vw) {

               // db_SQL.deleteDB();
              //  db_SQL.expcsv();
                return false;
            }

        });

        final Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setText("Create");
        addBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * This method will process when the specified button is clicked which is to create a contact
             * @param view this is the first parameter of type View
             */
            public void onClick(View view) {

                //Contacts.add(new Contact(0,contactName.getText().toString(), contactNumber.getText().toString(), contactEmail.getText().toString(), contactAddress.getText().toString(),imgURI));
       //       if (!contactExts(contact)) {
       //           //db_SQL.createContact(contact);
       //           //Contacts.add(contact);
       //           //contactAdapt.notifyDataSetChanged();

       //           //populateList();

       //           db_SQL.createContact(contact);
       //           Contacts.add(contact);
       //           contactAdapt.notifyDataSetChanged();

       //           Toast.makeText(getApplicationContext(), contactName.getText().toString() + " has been added to your contacts!", Toast.LENGTH_SHORT).show();
       //           resetPanel();
       //           return;
       //       }




          //latest stuff commented
         //      if (isEditMod) {
         //          Contact newContact = Contacts.get(holdingItemIdx);
         //          newContact.setName(String.valueOf(contactName.getText()));
         //          newContact.setPhone(String.valueOf(contactNumber.getText()));
         //          newContact.setWPhone(String.valueOf(contactWNumber.getText()));
         //          newContact.setOPhone(String.valueOf(contactONumber.getText()));
         //          newContact.setEmail(String.valueOf(contactEmail.getText()));
         //          newContact.setAddress(String.valueOf(contactAddress.getText()));
         //          newContact.setURIimg(imgURI);
         //         // Contacts.remove(holdingItemIdx);
         //          db_SQL.updateContact(newContact);

         //          isEditMod = false;
         //          addBtn.setText("Create");
         //          contactAdapt.notifyDataSetChanged();
         //          Toast.makeText(getApplicationContext(), contactName.getText().toString() + " EDITED!", Toast.LENGTH_SHORT).show();

         //          resetPanel();
         //          tabHost.setCurrentTab(1);
         //          //return;
         //      } else {

         //          addContact(db_SQL.getContactsCount(),String.valueOf(contactName.getText()), String.valueOf(contactNumber.getText()), String.valueOf(contactWNumber.getText()), String.valueOf(contactONumber.getText()), String.valueOf(contactEmail.getText()), String.valueOf(contactAddress.getText()), imgURI);

         //          contactAdapt.notifyDataSetChanged();
         //      }


                if(imgURI == null){
                    imgURI = resIdToUri(getApplicationContext(),R.drawable.user);
                }
                Contact contact = new Contact(db_SQL.getContactsCount(), String.valueOf(contactName.getText()), String.valueOf(contactNumber.getText()), String.valueOf(contactWNumber.getText()), String.valueOf(contactONumber.getText()), String.valueOf(contactEmail.getText()), String.valueOf(contactAddress.getText()), imgURI);
                if (!contactExts(contact)) {
                    db_SQL.createContact(contact);
                    Contacts.add(contact);
                    contactAdapt.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(contactName.getText()) + " was successfully added!", Toast.LENGTH_SHORT).show();
                    //return;
                }
                else {

                    Toast.makeText(getApplicationContext(), String.valueOf(contactName.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();
                }
                clearData();
                return;
            }
        });

        contactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                addBtn.setEnabled(String.valueOf(contactName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        contactPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pic For Contact"), 1);


            }

        });

        if (db_SQL.getContactsCount() != 0)
            Contacts.addAll(db_SQL.getAllContacts());

        populateList();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method will get the application's context to be shared by other java classes in this app
     * @return Context
     */
    public static Context getContextMain(){


        return MainActivity.mainContext;
    }

    /**
     * This method will create the options for context menu
     * @param Menu this is the first parameter of type ContextMenu
     * @param V this is the second parameter of type View
     * @param MenuInformation this is the third parameter of type ContextMenu.ContextMenuInfo
     */
    public void onCreateContextMenu(ContextMenu Menu, View V, ContextMenu.ContextMenuInfo MenuInformation) {

        super.onCreateContextMenu(Menu, V, MenuInformation);

        Menu.setHeaderIcon(R.drawable.pencil);
        Menu.setHeaderTitle("Options");
        Menu.add(Menu.NONE, EDIT, Menu.NONE, "Edit Contact");
        Menu.add(Menu.NONE, DELETE, Menu.NONE, "Del Contact");
        Menu.add(Menu.NONE, C_CALL, Menu.NONE, "Call");
        Menu.add(Menu.NONE, C_MSG, Menu.NONE, "Msg");

    }

    /**
     * This method will process the action selected from the context menu
     * @param Item this is the first parameter of type MenuItem
     * @return boolean
     */
    public boolean onContextItemSelected(MenuItem Item) {


        switch (Item.getItemId()) {
            case EDIT:
                //isEditMod = true;
                //editmode(Contacts.get(holdingItemIdx));



                Intent editContactIntent = new Intent(getApplicationContext(), EditContact.class);
                startActivityForResult(editContactIntent, 2);

                break;
            case DELETE:
                db_SQL.deleteContact(Contacts.get(holdingItemIdx));
                Contacts.remove(holdingItemIdx);
                contactAdapt.notifyDataSetChanged();
                break;
            case C_CALL:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //sendIntent.setType("vnd.android-dir/mms-sms");
                //sendIntent.putExtra("address","12125551212");
                callIntent.setData(Uri.parse("tel:" + (Contacts.get(holdingItemIdx).getPhone())));
                startActivity(callIntent);

                break;
            case C_MSG:

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                //sendIntent.setType("vnd.android-dir/mms-sms");
                //sendIntent.putExtra("address","12125551212");

                //  sendIntent.setData(Uri.parse("sms:12125551212"));
                sendIntent.setData(Uri.parse("sms:" + (Contacts.get(holdingItemIdx).getPhone())));
                startActivity(sendIntent);


                break;
        }
        return super.onContextItemSelected(Item);
    }

    /**
     * This method will check if the contact exists beforehand
     * @param contact this is the first parameter of type Contact
     * @return boolean
     */
    private boolean contactExts(Contact contact) {

        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }

        return false;
    }

   // private void editmode(Contact contacts) {
//
   //     TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
   //     tabhost.setCurrentTab(0);
   //     contactName.setText(contacts.getName());
   //     contactNumber.setText(contacts.getPhone());
   //     contactWNumber.setText(contacts.getWPhone());
   //     contactONumber.setText(contacts.getOPhone());
   //     contactEmail.setText(contacts.getEmail());
   //     contactAddress.setText(contacts.getAddress());
   //     contactPic.setImageURI(imgURI);
   //     Button edit = (Button) findViewById(R.id.addBtn);
//
//
//
//
   //     edit.setText("Update");
   //     isEditMod = true;
//
   //     db_SQL.deleteContact(contacts);
   //     Contacts.remove(holdingItemIdx);
   //     contactAdapt.notifyDataSetChanged();
//
//
   // }

    /**
     * This method will clear data in the input fields
     */
    private void clearData() {

        contactPic.setImageURI(resIdToUri(getApplicationContext(),R.drawable.user));
        contactName.setText("");
        contactNumber.setText("");
        contactWNumber.setText("");
        contactONumber.setText("");
        contactEmail.setText("");
        contactAddress.setText("");
        imgURI = null;
    }


    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);
    //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //fab.setOnClickListener(new View.OnClickListener() {
    //    @Override
    //    public void onClick(View view) {
    //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //                .setAction("Action", null).show();
    //    }
    //});
    //}

    /**
     * This method will capture our start activity for result
     * @param requestCode this is the first parameter of type integer
     * @param resultCode this is the second parameter of type integer
     * @param data this is the third parameter of type Intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imgURI = data.getData();
                //Log.d("Image", imgURI.toString());
                contactPic.setImageURI(imgURI);
            } else if (requestCode == 2) {
                // stuff for edit contact
                contactAdapt.notifyDataSetChanged();
                populateList();
            }
        }


    }

    /**
     * This method will add/create the contact information used with add button
     * @param id this is the first parametter
     * @param name
     * @param phone
     * @param wPhonee
     * @param oPhonee
     * @param email
     * @param address
     * @param imageUri
     */
   //public  void addContact(int id,String name,String phone,String wPhonee,String oPhonee, String email,String address,Uri imageUri){
   //    Contact contactDetail = new Contact(id,name,phone,wPhonee,oPhonee,email,address,imageUri);
   //    contactDetail.setID(id);
   //    if(!contactExts(contactDetail)){
   //        db_SQL.createContact(contactDetail);
   //        Contacts.add(contactDetail);
   //        Toast.makeText(getApplicationContext(), "Contact has been created!", Toast.LENGTH_SHORT).show();
   //    }else{
   //        Toast.makeText(getApplicationContext(), "Contact already exist", Toast.LENGTH_SHORT).show();
   //    }

   //}

    /**
     * This method will populate the list view with contacts
     */
    private void populateList() {
        contactAdapt = new ContactListAdapter();
        contactListView.setAdapter(contactAdapt);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.saber.contactmanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.saber.contactmanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super(MainActivity.this, R.layout.listview_item, Contacts);
        }



        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }
            Contact currentContact = Contacts.get(position);
            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());

            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText("Phone: " + currentContact.getPhone());

            TextView wphone = (TextView) view.findViewById(R.id.wPhoneNum);
            wphone.setText("Work-Phone: " + currentContact.getWPhone());

            TextView ophone = (TextView) view.findViewById(R.id.oPhoneNum);
            ophone.setText("Other-Phone: " + currentContact.getOPhone());

            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText("EMail: " + currentContact.getEmail());

            TextView address = (TextView) view.findViewById(R.id.cAddress);
            address.setText("Addr: " + currentContact.getAddress());

            ImageView piccontactlv = (ImageView) view.findViewById(R.id.imageViewContactPic);
            piccontactlv.setImageURI(currentContact.getURIimg());
            return (view);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method will get the id of the image view file and apply it to the location of the file
     * @param context This is the first parameter type Context
     * @param resId this is the second parameter type integer
     * @return Uri
     */
        public static Uri resIdToUri(Context context,int resId){

            return Uri.parse("android.resource://"+ context.getPackageName() + "/" +resId);

        }




}
