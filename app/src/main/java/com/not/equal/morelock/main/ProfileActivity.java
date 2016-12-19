package com.not.equal.morelock.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.not.equal.morelock.R;

/**
 * Created by admin on 2016-06-07.
 */
public class ProfileActivity extends Fragment {
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        linearLayout = (LinearLayout)v.findViewById(R.id.btn_coin);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FpangSession.showAdsyncList(getActivity(), "AdSync2");
            }
        });
        return v;
    }

}

