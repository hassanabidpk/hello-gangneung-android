package com.hellogangneung.app.android.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by hassan1 on 18/02/2018.
 */

@IgnoreExtraProperties
public class Rest {

    public String eng_name;
    public String kor_name;
    public String eng_intro;
    public String category;
    public String address_kor;
    public String address_en;
    public String latitude;
    public String longitude;
    public String english_menu;
    public String phone_number;
    public String temperature;
    public String distance;
    public String hours;
    public String price_range;
    public String pic_1;
    public String pic_2;
    public String pic_3;
    public String pic_4;
    public String main_menu;

    public String restKey = "";

    public  Rest() {

    }
}
