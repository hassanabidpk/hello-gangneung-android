package com.hellogangneung.app.android.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hellogangneung.app.android.R;
import com.hellogangneung.app.android.fragments.RestCatFragment;
import com.hellogangneung.app.android.fragments.RestTabFragment;
import com.hellogangneung.app.android.models.Rest;

public class RestBaseActivity extends AppCompatActivity implements RestTabFragment.OnMainFragmentInteractionListener,
        RestCatFragment.OnListFragmentInteractionListener{

    private static final String TAG = RestBaseActivity.class.getSimpleName();

    public static final String type = "image/*";

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;
    private Toolbar toolbar;


    public static Intent createIntent(Context context) {
        Intent in = new Intent(context, RestBaseActivity.class);
        return in;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d(TAG, "navigation_home");
//                    toolbar.setTitle(getString(R.string.marathon_events_2017));
                    fragment = RestTabFragment.newInstance();
                    break;
                case R.id.navigation_posts:
//                    toolbar.setTitle(getString(R.string.my_marathon_events));
//                    fragment = MyRunsFragment.newInstance();
                    fragment = RestTabFragment.newInstance();
                    break;
                case R.id.navigation_settings:
//                    toolbar.setTitle(getString(R.string.title_crews));
//                    fragment = CrewListFragment.newInstance();
                    fragment = RestTabFragment.newInstance();
                    break;

            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment).commit();
            return true;
        }

    };

    @Override
    public void onFragmentInteraction(Uri uri) {

        Log.d(TAG,"onFragmentInteraction");
    }

    @Override
    public void onListFragmentInteraction(Rest item) {

        Log.d(TAG,"onListFragmentInteraction");
        Intent i = new Intent(RestBaseActivity.this, RestDetailActivity.class);
        i.putExtra(RestDetailActivity.REST_KEY_ARG, item.restKey);
        i.putExtra(RestDetailActivity.REST_LAT_KEY_ARG, item.latitude);
        i.putExtra(RestDetailActivity.REST_LNG_KEY_ARG, item.longitude);
        i.putExtra(RestDetailActivity.REST_ADD_KEY_ARG, item.address_en);
        i.putExtra(RestDetailActivity.REST_NAME_KEY_ARG, item.eng_name);
        startActivity(i);

    }
}
