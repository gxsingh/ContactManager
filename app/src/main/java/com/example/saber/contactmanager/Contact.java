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
    private String _wPhone, _oPhone;


    public Contact(int id, String name, String phone, String wphone, String ophone,String email, String address, Uri viewIMGURI){
        id =id;
        _name=name;
        _phone=phone;
        _wPhone = wphone;
                _oPhone = ophone;

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
    public String getWPhone(){
        return _wPhone;
    }
    public String getOPhone(){
        return _oPhone;
    }

    public String getEmail(){
        return _email;
    }

    public String getAddress(){
        return _address;
    }

    public Uri getURIimg() {return viewIMGURI;}



    public void setName( String n){
        this._name = n;
    }

    public void setPhone(String n){
        this._phone = n;
    }
    public void setWPhone(String n){
        this._wPhone = n;
    }
    public void setOPhone(String n){
        this._oPhone = n;
    }

    public void setEmail(String n){
        this._email = n;
    }

    public void setAddress(String n){
        this._address = n;
    }

    public void setURIimg(Uri img) {this.viewIMGURI = img;}

    public void setID(int i){
        this.id = i;
    }
}
