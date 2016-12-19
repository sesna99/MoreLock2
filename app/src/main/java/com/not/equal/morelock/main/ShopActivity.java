package com.not.equal.morelock.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;
import com.not.equal.morelock.lock.LockMain;

import java.util.ArrayList;

/**
 * Created by admin on 2016-06-03.
 */
/*
* ReturnLock type = 1
* StarLock type = 2
* ToggleLock type = 3
* CubeLock type = 4
* MapleLock type = 5
* SwipeLock type = 6
* SellAddLock type = 7
* */

public class ShopActivity extends Fragment {
    private PreferencesManager manager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> mDataset;
    private String[] lock = {"ReturnLock", "StarLock", "ToggleLock", "CubeLock", "TreeLock", "SwipeLock", "WifiLock"};
    private int[] beforePurchase = {R.drawable.cap_return_01, R.drawable.cap_star_01, R.drawable.cap_toggle_01, R.drawable.cap_cube_01, R.drawable.cap_tree_01, R.drawable.cap_swipe_01, R.drawable.cap_wifi_01};
    private int[] afterPurchase = {R.drawable.cap_return_02, R.drawable.cap_star_02, R.drawable.cap_toggle_02, R.drawable.cap_cube_02, R.drawable.cap_tree_02, R.drawable.cap_swipe_02, R.drawable.cap_wifi_02};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_shop, container, false);

        manager = PreferencesManager.getInstance(getContext());

        if(manager.getActionbarSize() == 0){
            TypedValue tv = new TypedValue();
            if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                manager.setActionbarSize(actionBarHeight);
            }
        }

        mDataset = new ArrayList<>();
        for(int i = 0; i < lock.length; i++) {
            if(manager.isBuy(i))
                mDataset.add(new MyData(lock[i], afterPurchase[i], i, Color.rgb(138, 219, 212)));
            else
                mDataset.add(new MyData(lock[i], beforePurchase[i], i, Color.rgb(249, 95, 113)));
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i < lock.length; i++) {
            if(manager.isBuy(i))
                mAdapter.setItem(i, new MyData(lock[i], afterPurchase[i], i, Color.rgb(138, 219, 212)));
        }
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mCardView = (CardView)view.findViewById(R.id.card);
            mImageView = (ImageView)view.findViewById(R.id.image);
            mTextView = (TextView)view.findViewById(R.id.title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final int a = position;
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LockMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PreferencesManager.getInstance(mContext).setCurType(mDataset.get(position).type);
                mContext.startActivity(intent);
            }
        });
        holder.mTextView.setText(mDataset.get(position).text);
        holder.mTextView.setTextColor(mDataset.get(position).color);
        holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setItem(int position, MyData item){
        mDataset.set(position, item);
        notifyDataSetChanged();
    }
}

class MyData{
    public String text;
    public int img;
    public int type;
    public int color;
    public MyData(String text, int img, int type, int color){
        this.text = text;
        this.img = img;
        this.type = type;
        this.color = color;
    }
}

