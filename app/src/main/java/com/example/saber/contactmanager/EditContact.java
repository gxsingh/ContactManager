package com.example.saber.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Garry_12 on 12/3/2015.
 */
public class EditContact extends Activity {

    // Class
    //Database dbHandler = new Database(MainActivity.mainContext);
   //Database dbHandler = new Database(getApplicationContext());
   Database dbHandler = new Database(MainActivity.getContextMain());


    // Textviews
    ImageView iv;
    TextView name, phone, wphone, ophone,email, address;

    // Uri
    Uri imageUri = null;

    /**
     * This method will initialize the variables
     * @param savedInstanceState This is the first parameter of method onCreate of class Bundle type
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        // Get the contact to be updated
        final Contact tempContact = MainActivity.Contacts.get(MainActivity.holdingItemIdx);

        iv = (ImageView) findViewById(R.id.ivEditImage);
        iv.setImageURI(tempContact.getURIimg());

        name = (TextView) findViewById(R.id.txtEditName);
        name.setText(tempContact.getName());

        phone = (TextView) findViewById(R.id.txtEditPhone);
        phone.setText(tempContact.getPhone());

        wphone = (TextView) findViewById(R.id.txtEditwPhone);
        wphone.setText(tempContact.getWPhone());

        ophone = (TextView) findViewById(R.id.txtEditoPhone);
        ophone.setText(tempContact.getOPhone());

        email = (TextView) findViewById(R.id.txtEditEmail);
        email.setText(tempContact.getEmail());

        address = (TextView) findViewById(R.id.txtEditAddress);
        address.setText(tempContact.getAddress());

        Button btnDone = (Button) findViewById(R.id.btnEditDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.mainContext, iv.getDrawable().toString(), Toast.LENGTH_SHORT).show();

                if (imageUri != null) {
                    tempContact.setURIimg(imageUri);
                }
                tempContact.setName(String.valueOf(name.getText()));
                tempContact.setPhone(String.valueOf(phone.getText()));
                tempContact.setWPhone(String.valueOf(wphone.getText()));
                tempContact.setOPhone(String.valueOf(ophone.getText()));

                tempContact.setEmail(String.valueOf(email.getText()));
                tempContact.setAddress(String.valueOf(address.getText()));

                dbHandler.updateContact(tempContact);

                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        /**
         * This method is a new click listener for the image
         */
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newImage = new Intent();
                newImage.setType("image/*");
                newImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(newImage, "Select Contact Image"), 1);
            }
        });
    }


    /**
     * This method will capture our start activity for result
     * @param reqCode this is the first parameter of function onActivityResult an Integer
     * @param resCode this is the second parameter of function onActivityResult an Integer
     * @param data this is the third parameter of function onActivityResult an Intent
     */
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                iv.setImageURI(imageUri);
            }
        }
    }
}
