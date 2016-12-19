package com.not.equal.morelock.lock;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;
import com.not.equal.morelock.service.ScreenService;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by admin on 2016-06-20.
 */

//잠금 상세 화면에 들어가는 프래그먼트
public class LockFragment extends Fragment {
    private int type;
    private Intent intent;
    private Button apply, present;
    private TextView setting, lockName, contents;
    private PreferencesManager manager;
    private GifImageView gif;
    private GifDrawable gifDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lock_fragment, container, false);
        type = getArguments().getInt("type", 0);
        apply = (Button) v.findViewById(R.id.apply);
        manager = PreferencesManager.getInstance(getContext());
        present = (Button) v.findViewById(R.id.present);
        lockName = (TextView) v.findViewById(R.id.lock_name);
        contents = (TextView) v.findViewById(R.id.contents);
        gif = (GifImageView) v.findViewById(R.id.gif);
        try {
            gifDrawable = new GifDrawable(getResources(), LockMain.gif[type]);
            gif.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lockName.setText(getName());
        contents.setText(getResources().getStringArray(R.array.lock_contents)[type]);
        if (manager.isBuy(type)) {
            if (manager.isRun(type)) {
                apply.setText("사용중");
                apply.setBackgroundResource(R.drawable.buy_bar_03);
                present.setBackgroundResource(R.drawable.pr_bar_03);
            } else {
                cancel();
            }
        }

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.isBuy(type)) {
                    if (!manager.isRun(type)) {
                        chkPermission();
                    } else {
                        cancel();
                        intent = new Intent(getContext(), ScreenService.class);
                        getContext().stopService(intent);
                        manager.setIsRun(false, type);
                    }
                } else {
                    manager.buy(type);
                    cancel();
                }
            }
        });

        return v;
    }

    public String getName() {
        switch (type) {
            case 0:
                return "ReturnLock";
            case 1:
                return "StarLock";
            case 2:
                return "ToggleLock";
            case 3:
                return "CubeLock";
            case 4:
                return "TreeLock";
            case 5:
                return "SwipeLock";
            case 6:
                return "WifiLock";
        }
        return "";
    }

    public void chkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()));
                getActivity().startActivityForResult(intent, 100);
            } else
                start();
        } else
            start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("code", requestCode+"");
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (Settings.canDrawOverlays(getContext()))
                    start();
                else
                    start();
        } else if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                apply.setText("사용중");
                apply.setBackgroundResource(R.drawable.buy_bar_03);
                present.setBackgroundResource(R.drawable.pr_bar_03);
                lock();
            }
        }
    }

    private void lock() {
        intent = new Intent(getContext(), ScreenService.class);
        getContext().startService(intent);
        for (int i = 0; i < 9; i++)
            manager.setIsRun(false, i);
        manager.setIsRun(true, type);
        manager.setType(type);
    }

    private void cancel() {
        apply.setText("적용하기");
        apply.setBackgroundResource(R.drawable.buy_bar_02);
        present.setBackgroundResource(R.drawable.pr_bar_02);
    }

    private void start() {
        PreferencesManager.getInstance(getContext()).setCurType(type);
        intent = new Intent(getContext(), LockSetting.class);
        this.startActivityForResult(intent, 0);
    }

}
