package com.hellogangneung.app.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hellogangneung.app.android.R;
import com.hellogangneung.app.android.models.Rest;

import java.util.HashMap;

public class RestDetailActivity extends AppCompatActivity implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnMapReadyCallback {

    private static final String TAG = RestDetailActivity.class.getSimpleName();

    public static final String REST_KEY_ARG = "rest_key";
    public static final String REST_LAT_KEY_ARG = "rest_lat_key";
    public static final String REST_LNG_KEY_ARG = "rest_lng_key";
    public static final String REST_ADD_KEY_ARG = "rest_add_key";
    public static final String REST_NAME_KEY_ARG = "rest_name_key";



    private DatabaseReference mDatabase;
    private Query mEventsQuery;
    private Rest mRest;
    private String mLat;
    private String mLng;
    private String mAdress;
    private String mRestName;

    private SliderLayout mSlider;
    private TextView mRestNameView;
    private TextView mRestKorNameView;
    private TextView mRestMenuView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_detail);
        mSlider = (SliderLayout) findViewById(R.id.slider);
        mRestNameView = findViewById(R.id.restName);
        mRestKorNameView = findViewById(R.id.restCat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String restKey = getIntent().getStringExtra(REST_KEY_ARG);
        mLat = getIntent().getStringExtra(REST_LAT_KEY_ARG);
        mLng = getIntent().getStringExtra(REST_LNG_KEY_ARG);
        mAdress = getIntent().getStringExtra(REST_ADD_KEY_ARG);
        mRestName = getIntent().getStringExtra(REST_NAME_KEY_ARG);



        mEventsQuery = mDatabase.child("restaurants").child(restKey);
        addFirebaseListener();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addFirebaseListener() {

        mEventsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    mRest = dataSnapshot.getValue(Rest.class);
                    mRest.restKey = dataSnapshot.getKey();
                    updateUI();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {

        if(mRest == null)
            return;


        if(!mRest.main_menu.isEmpty()) {
            String[] separated = mRest.main_menu.split("\\+");
            Log.d(TAG, "updateUI | separated " + separated);
        }


        HashMap<String,String> url_maps = new HashMap<String, String>();

        if(!mRest.pic_1.isEmpty()) {
            url_maps.put("Menu 1", mRest.pic_1);
        }
        if(!mRest.pic_2.isEmpty()) {
            url_maps.put("Menu 2", mRest.pic_2);
        }
        if(!mRest.pic_3.isEmpty()) {
            url_maps.put("Menu 3", mRest.pic_3);
        }
        if(!mRest.pic_4.isEmpty()) {
            url_maps.put("Menu 4", mRest.pic_4);
        }

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(RestDetailActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(7000);
        mSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady");
        LatLng eventLatLng;

        if(!mLat.isEmpty() && !mLng.isEmpty()) {
            eventLatLng = new LatLng(Double.valueOf(mLat), Double.valueOf(mLng));
        } else {
            eventLatLng = new LatLng(37.7510344, 128.8741694);

        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 14));

        googleMap.addMarker(new MarkerOptions()
                .title(mRestName)
                .snippet(mAdress)
                .position(eventLatLng));
    }


}
