package com.hellogangneung.app.android.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellogangneung.app.android.fragments.RestCatFragment.OnListFragmentInteractionListener;
import com.hellogangneung.app.android.models.Rest;

import com.hellogangneung.app.android.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;



public class RestRecyclerViewAdapter extends RecyclerView.Adapter<RestRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = RestRecyclerViewAdapter.class.getSimpleName();
    private final List<Rest> mRests;
    private final OnListFragmentInteractionListener mListener;

    public RestRecyclerViewAdapter(List<Rest> events, OnListFragmentInteractionListener listener) {
        Log.d(TAG, "RestRecyclerViewAdapter");
        mRests = events;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mRests.get(position);
        Resources res = holder.mRestName.getContext().getResources();


        if(!holder.mItem.eng_name.isEmpty()) {
            holder.mRestName.setText(holder.mItem.eng_name);
        } else {
            holder.mRestName.setText(holder.mItem.kor_name);
        }

        if(!holder.mItem.price_range.isEmpty()) {
            holder.mRestPrice.setText(holder.mItem.price_range);
        } else {
            holder.mRestPrice.setText("$$");
        }

        holder.mRestMenu.setText(holder.mItem.main_menu);

        if(!holder.mItem.pic_1.isEmpty()) {

            Picasso.with(holder.mRestName.getContext())
                    .load(holder.mItem.pic_1)
                    .placeholder(R.drawable.rest_dummy_pic)
                    .resize(500, 300)
                    .centerCrop()
                    .into(holder.mRestImage);
        } else {
            holder.mRestImage.setImageDrawable(res.getDrawable(R.drawable.cell_bg));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mRests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mRestName;
        public final TextView mRestPrice;
        public final TextView mRestMenu;
        public final ImageView mRestImage;
        public Rest mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRestName = (TextView) view.findViewById(R.id.restName);
            mRestPrice = (TextView) view.findViewById(R.id.restPrice);
            mRestMenu = (TextView) view.findViewById(R.id.restMenu);
            mRestImage = (ImageView) view.findViewById(R.id.restImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mRestMenu.getText() + "'";
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
