package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;

import java.io.Serializable;
public class Official implements Serializable {
    public static final String NO_DATA = "No Data Provided";
    public static final String UNKNOWN = "Unknown";

    // In Recycler View Entry
    private String name;
    private String office;
    private String party;
    private String location;

    // In Official Detail Activity
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String url;
    private String email;

    private String photoUrl;
    private String googleplus;
    private String facebook;
    private String twitter;
    private String youtube;

    public Official() {
        this.location = NO_DATA;
        this.name = NO_DATA;
        this.office = NO_DATA;
        this.party = UNKNOWN;
        this.address = NO_DATA;
        this.phone = NO_DATA;
        this.url = NO_DATA;
        this.email = NO_DATA;
        this.photoUrl = NO_DATA;
        this.googleplus = NO_DATA;
        this.facebook = NO_DATA;
        this.twitter = NO_DATA;
        this.youtube = NO_DATA;
        this.city = NO_DATA;
        this.state = NO_DATA;
        this.zip = NO_DATA;
    }

    public Official(String l, String o, String n, String a, String c, String s, String z, String pa, String ph, String e, String pURL, String u, String f, String y, String t) {
        location = l;
        office = o;
        name = n;
        address = a;
        city = c;
        state = s;
        zip = z;
        party = pa;
        phone = ph;
        email = e;
        photoUrl = pURL;
        url = u;
        facebook = f;
        youtube = y;
        twitter = t;
    }

    public String getOffice() {
        return office;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getZip() {
        return zip;
    }
    public String getParty() {
        return party;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public String getUrl() {
        return url;
    }
    public String getFacebook() {
        return facebook;
    }
    public String getGoogleplus() {
        return googleplus;
    }
    public String getTwitter() {
        return twitter;
    }
    public String getYoutube() {
        return youtube;
    }

    public void setOffice(String o){this.office = o;}
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public void setParty(String party) {
        this.party = party;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    public void setGoogleplus(String googleplus) {
        this.googleplus = googleplus;
    }
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @NonNull
    @Override
    public String toString() {
        return office + ", " + name;
    }
}
