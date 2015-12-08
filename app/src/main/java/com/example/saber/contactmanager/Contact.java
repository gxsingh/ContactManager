package com.example.saber.contactmanager;

import android.net.Uri;

import java.net.URI;
import java.util.Collections;

/**
 * Created by Saber on 11/8/2015.
 */

import android.net.Uri;

public class Contact {

    private String _name, _phone, _email, _address;
    private Uri _viewIMGURI;
    private int _id;
    private String _wPhone, _oPhone;



    public Contact(int id, String name, String phone, String wphone, String ophone,String email, String address, Uri viewIMGURI){
        _id = id;
        _name=name;
        _phone=phone;
        _wPhone = wphone;
        _oPhone = ophone;
        _email=email;
        _address=address;
        _viewIMGURI = viewIMGURI;
    }

    /**
     * This method gets the contact Id integer
     * @return integer
     */
    public int getID(){
        return _id;
    }

    /**
     * This method gets the contact name string
     * @return String
     */
    public String getName(){
        return _name;
    }

    /**
     * This method gets the phone string
     * @return String
     */
    public String getPhone(){
        return _phone;
    }

    /**
     * This method gets the work phone string
     * @return string
     */
    public String getWPhone(){
        return _wPhone;
    }
    /**
     * This method gets the other phone string
     * @return string
     */
    public String getOPhone(){
        return _oPhone;
    }
    /**
     * This method gets the email string
     * @return string
     */
    public String getEmail(){
        return _email;
    }
    /**
     * This method gets the address string
     * @return string
     */
    public String getAddress(){
        return _address;
    }
    /**
     * This method gets the Uri image location
     * @return Uri
     */
    public Uri getURIimg() {return _viewIMGURI;}


    /**
     * This method is used to set the contact name
     * @param n This is the first parameter setName method
     */
    public void setName( String n){
        _name = n;
    }
    /**
     * This method is used to set the contact phone number
     * @param n This is the first parameter setPhone method
     */
    public void setPhone(String n){
        _phone = n;
    }
    /**
     * This method is used to set the contact work phone number
     * @param n This is the first parameter setWPhone method
     */
    public void setWPhone(String n){
        this._wPhone = n;
    }
    /**
     * This method is used to set the contact other phone number
     * @param n This is the first parameter setOPhone method
     */
    public void setOPhone(String n){
        _oPhone = n;
    }
    /**
     * This method is used to set the contact email
     * @param n This is the first parameter setEmail method
     */
    public void setEmail(String n){
        _email = n;
    }
    /**
     * This method is used to set the contact address
     * @param n This is the first parameter setAddress method
     */
    public void setAddress(String n){
        _address = n;
    }
    /**
     * This method is used to set the contact Uri image location
     * @param img This is the first parameter setURIimg method
     */
    public void setURIimg(Uri img) {_viewIMGURI = img;}
    /**
     * This method is used to set the contact Id
     * @param i This is the first parameter setID method
     */
    public void setID(int i){
        _id = i;
    }
}
