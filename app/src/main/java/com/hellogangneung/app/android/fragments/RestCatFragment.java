package com.hellogangneung.app.android.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hellogangneung.app.android.adapters.RestRecyclerViewAdapter;
import com.hellogangneung.app.android.models.Rest;
import com.hellogangneung.app.android.utils.NetworkHelper;
import com.hellogangneung.app.android.R;


import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RestCatFragment extends Fragment {

    private static final String TAG = RestCatFragment.class.getSimpleName();
    public static final String MARATHON_EVENT_DATABASE = "event";
    public static final String INTL_MARATHON_EVENT_DATABASE = "intl-event";
    private static final String PAST_2017_START_DATE = "2017/03/18 08:00";

    private static final String ARG_TAB_INDEX = "arg_tab_index";

    private OnListFragmentInteractionListener mListener;
    private DatabaseReference mDatabase;
    private List<Rest> mRestList;
    private Query mEventsQuery;

    private RecyclerView restRecycleView;
    private AlertDialog progressDialog;
    private Context mContext;
    private int mTabIndex = 0;



    public RestCatFragment() {
    }

    public static RestCatFragment newInstance(int tabIndex) {
        RestCatFragment fragment = new RestCatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_INDEX, tabIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (getArguments() != null) {
            mTabIndex = getArguments().getInt(ARG_TAB_INDEX);

            Log.d(TAG,"mTabIndex : " + mTabIndex);

            if (mTabIndex == 0) {
                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Best10");

            } else if(mTabIndex == 1) {

                mEventsQuery = mDatabase.child("restaurants")
                        .orderByChild("category").equalTo("Western");

            } else if (mTabIndex == 2) {

                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Korean");
            } else if (mTabIndex == 3) {
                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Chinese");
            } else if (mTabIndex == 4) {
                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Japanese");
            }
            else if (mTabIndex == 5) {
                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Sushi Restaurant");
            } else if (mTabIndex == 6) {
                mEventsQuery = mDatabase
                        .child("restaurants")
                        .orderByChild("category").equalTo("Buffet");
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rest_cat_list, container, false);
        progressDialog = new SpotsDialog(getActivity(), getActivity().getResources().getString(R.string.fetching_data));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            restRecycleView = (RecyclerView) view;

        }

        // Read from the database
        if(mRestList == null) {
            //TODO: Find a way to handle this better
//            progressDialog.show();
        }
        if(!NetworkHelper.isNetworkAvailable(getActivity())) {

            showToastMessage(getActivity().getResources().getString(R.string.network_not_available));
            progressDialog.dismiss();
            return view;
        }

        if(savedInstanceState == null) {
            addFirebaseEventListener();
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_marathon_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_refresh){

//            createFilterDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(Rest item);
    }

    private void showToastMessage(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void addFirebaseEventListener() {

        Log.d(TAG, "addFirebaseEventListener");

        mEventsQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mRestList = new ArrayList<Rest>();

                if(dataSnapshot.exists()) {
                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                        Rest rest = eventSnapshot.getValue(Rest.class);
                        rest.restKey = eventSnapshot.getKey();
                        mRestList.add(rest);
                        Log.d(TAG, "eventSnapshot key(" + eventSnapshot.getKey() + "): " +
                                rest.eng_name);

                    }
                    restRecycleView.setAdapter(new RestRecyclerViewAdapter(mRestList, mListener));
                    progressDialog.dismiss();
                    Log.d(TAG, "Count is: " + dataSnapshot.getChildrenCount());

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }


        });
    }




}
