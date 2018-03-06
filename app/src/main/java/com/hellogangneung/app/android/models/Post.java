package com.hellogangneung.app.android.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by hassan1 on 18/02/2018.
 */

@IgnoreExtraProperties
public class Post {

    public String title;
    public String category;
    public String para1;
    public String para2;
    public String link1_title;
    public String link2_title;
    public String link1_url;
    public String link2_url;
    public String author;
    public String pic_1;
    public String pic_2;
    public String pic_3;

    public String postKey = "";

    public Post() {

    }
}
