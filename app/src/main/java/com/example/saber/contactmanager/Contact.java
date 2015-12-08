package com.example.saber.contactmanager;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Saber on 11/8/2015.
 */

import android.net.Uri;

public class Contact {

    private String _name, _phone, _email, _address;
    private Uri viewIMGURI;
    private int id;


    public Contact(int id, String name, String phone, String email, String address, Uri viewIMGURI){
        id =id;
        _name=name;
        _phone=phone;
        _email=email;
        _address=address;
    }

    public int getID(){
        return id;
    }

    public String getName(){
        return _name;
    }

    public String getPhone(){
        return _phone;
    }

    public String getEmail(){
        return _email;
    }

    public String getAddress(){
        return _address;
    }

    public Uri getURIimg() {return viewIMGURI;}
}
