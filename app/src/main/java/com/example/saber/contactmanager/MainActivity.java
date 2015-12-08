package com.example.saber.contactmanager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    EditText contactName;
    EditText contactNumber;
    EditText contactWNumber;
    EditText contactONumber;
    EditText contactEmail;
    EditText contactAddress;
    ImageView contactPic;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    //Uri imgURI = Uri.parse("android.resource://com.example.saber.contactmanager/drawable/user.png" );
    Uri imgURI = Uri.parse("android.resource://com.example.saber.contactmanager/drawable/user");

    //Uri imgURI = null;
    Database db_SQL;

    int holdingItemIdx;
    private boolean isEditMod;

    ArrayAdapter<Contact> contactAdapt;

    private static final int DELETE = 1, EDIT = 0, C_CALL = 2, C_MSG = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setContentView(R.layout.content_main);

        contactName = (EditText) findViewById(R.id.contactName);
        contactNumber = (EditText) findViewById(R.id.contactNumber);

        contactWNumber = (EditText) findViewById(R.id.wPhone);
        contactONumber = (EditText) findViewById(R.id.oPhone);

        contactEmail = (EditText) findViewById(R.id.cEmail);
        contactAddress = (EditText) findViewById(R.id.contactAddress);
        contactListView = (ListView) findViewById(R.id.listView);

        contactPic = (ImageView) findViewById(R.id.imageViewContactPic);
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


        final Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Contact contact = new Contact(db_SQL.getContactsCount(), String.valueOf(contactName.getText()), String.valueOf(contactNumber.getText()), String.valueOf(contactWNumber.getText()), String.valueOf(contactONumber.getText()), String.valueOf(contactEmail.getText()), String.valueOf(contactAddress.getText()), imgURI);
                //Contacts.add(new Contact(0,contactName.getText().toString(), contactNumber.getText().toString(), contactEmail.getText().toString(), contactAddress.getText().toString(),imgURI));
                if (!contactExts(contact)) {
                    db_SQL.createContact(contact);
                    Contacts.add(contact);
                    contactAdapt.notifyDataSetChanged();
                    //populateList();
                    if (isEditMod) {
                        Contact newContact = Contacts.get(holdingItemIdx);
                        newContact.setName(String.valueOf(contactName.getText()));
                        newContact.setPhone(String.valueOf(contactNumber.getText()));
                        newContact.setWPhone(String.valueOf(contactWNumber.getText()));
                        newContact.setOPhone(String.valueOf(contactONumber.getText()));
                        newContact.setEmail(String.valueOf(contactEmail.getText()));
                        newContact.setAddress(String.valueOf(contactAddress.getText()));
                        newContact.setURIimg(imgURI);
                        Contacts.remove(holdingItemIdx);
                        db_SQL.updateContact(contact);

                        isEditMod = false;
                        Toast.makeText(getApplicationContext(), contactName.getText().toString() + " EDITED!", Toast.LENGTH_SHORT).show();
                        resetPanel();
                        tabHost.setCurrentTab(1);
                        return;
                    }
                    Toast.makeText(getApplicationContext(), contactName.getText().toString() + " has been added to your contacts!", Toast.LENGTH_SHORT).show();
                    resetPanel();
                    return;
                }

                Toast.makeText(getApplicationContext(), String.valueOf(contactName.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();
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
    }

    public void onCreateContextMenu(ContextMenu Menu, View V, ContextMenu.ContextMenuInfo MenuInformation) {

        super.onCreateContextMenu(Menu, V, MenuInformation);

        Menu.setHeaderIcon(R.drawable.pencil);
        Menu.setHeaderTitle("Options");
        Menu.add(Menu.NONE, EDIT, Menu.NONE, "Edit Contact");
        Menu.add(Menu.NONE, DELETE, Menu.NONE, "Del Contact");
        Menu.add(Menu.NONE, C_CALL, Menu.NONE, "Call");
        Menu.add(Menu.NONE, C_MSG, Menu.NONE, "Msg");

    }

    public boolean onContextItemSelected(MenuItem Item) {


        switch (Item.getItemId()) {
            case EDIT:
                editmode(Contacts.get(holdingItemIdx));
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
                    callIntent.setData(Uri.parse("tel:" +(Contacts.get(holdingItemIdx).getPhone())));
                    startActivity(callIntent);

                break;
            case C_MSG:

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    //sendIntent.setType("vnd.android-dir/mms-sms");
                    //sendIntent.putExtra("address","12125551212");
                    sendIntent.setData(Uri.parse("sms:12125551212"));
                    startActivity(sendIntent);


                break;
        }
        return super.onContextItemSelected(Item);
    }



        private boolean contactExts(Contact contact) {

        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }

        return false;
    }

    private void editmode(Contact contacts){

        TabHost tabhost = (TabHost)findViewById(R.id.tabHost);
        tabhost.setCurrentTab(0);
        contactName.setText(contacts.getName());
        contactNumber.setText(contacts.getPhone());
        contactWNumber.setText(contacts.getWPhone());
        contactONumber.setText(contacts.getOPhone());
        contactEmail.setText(contacts.getEmail());
        contactAddress.setText(contacts.getAddress());
        contactPic.setImageURI(imgURI);
        Button edit = (Button) findViewById(R.id.addBtn);
        edit.setText("Update");
        isEditMod = true;


    }

    private void resetPanel(){

        contactName.setText("");
        contactNumber.setText("");
        contactWNumber.setText("");
        contactONumber.setText("");
        contactEmail.setText("");
        contactAddress.setText("");
        contactPic.setImageURI(Uri.parse("android.resource://com.example.saber.contactmanager/drawable/user"));
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if(requestCode == 1) {
                imgURI = (Uri)data.getData();
                Log.d("Image", imgURI.toString());
                contactPic.setImageURI(data.getData());
            }
        }



    }

    private void populateList(){
        contactAdapt = new ContactListAdapter();
        contactListView.setAdapter(contactAdapt);
    }




    private class ContactListAdapter extends ArrayAdapter<Contact>{
        public ContactListAdapter(){
            super(MainActivity.this, R.layout.listview_item, Contacts);
        }
    @Override
        public View getView(int position, View view, ViewGroup parent){
        if(view==null)
        {
            view=getLayoutInflater().inflate(R.layout.listview_item, parent,false);
        }
        Contact currentContact= Contacts.get(position);
        TextView name=(TextView) view.findViewById(R.id.contactName);
        name.setText(currentContact.getName());

        TextView phone=(TextView) view.findViewById(R.id.phoneNumber);
        phone.setText(currentContact.getPhone());

        TextView wphone=(TextView) view.findViewById(R.id.wPhoneNum);
        wphone.setText(currentContact.getWPhone());

        TextView ophone=(TextView) view.findViewById(R.id.oPhoneNum);
        ophone.setText(currentContact.getOPhone());

        TextView email=(TextView) view.findViewById(R.id.emailAddress);
        email.setText(currentContact.getEmail());

        TextView address=(TextView) view.findViewById(R.id.cAddress);
        address.setText(currentContact.getAddress());

        ImageView piccontactlv = (ImageView) view.findViewById(R.id.imageContactLV);
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
}
