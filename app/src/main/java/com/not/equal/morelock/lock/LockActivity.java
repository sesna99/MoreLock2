package com.not.equal.morelock.lock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;


//잠금 화면
public class LockActivity extends Activity {
    private int type;
    private ReturnLock returnLock;
    private StarLock starLock;
    private ToggleLock toggleLock;
    private TreeLock treeLock;
    private LockManager<FrameLayout> lockManager;
    private String password;
    private boolean isRun;
    private Context mContext;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, 0xffffff);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(params);
        frameLayout.setBackgroundColor(Color.BLACK);

        mContext = getApplicationContext();

        type = PreferencesManager.getInstance(mContext).getType();

        lockManager = new LockManager<>();
        lockManager.setType(type);

        switch (type){
            case 0:
                returnLock = new ReturnLock(mContext, false, type);
                returnLock.setBackgroundColor(Color.WHITE);
                lockManager.setView(returnLock);
                break;
            case 1:
                starLock = new StarLock(mContext, false, type);
                starLock.setBackgroundColor(Color.WHITE);
                lockManager.setView(starLock);
                break;
            case 2:
                toggleLock = new ToggleLock(mContext, type);
                toggleLock.setBackgroundColor(Color.WHITE);
                lockManager.setView(toggleLock);
                break;
            case 3:
                break;
            case 4:
                treeLock = new TreeLock(mContext, false, type);
                treeLock.setBackgroundColor(Color.WHITE);
                lockManager.setView(treeLock);
                break;
        }

        password = PreferencesManager.getInstance(mContext).getPassword(type);

        isRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRun) {
                    if (lockManager.isClear()) {
                        finish();
                        unlock();
                        isRun = false;
                        lockManager = null;
                        returnLock = null;
                        starLock = null;
                        toggleLock = null;
                        treeLock = null;
                    }
                }
            }
        }).start();

        lock();
    }

    public void lock() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        params.type = TYPE_SYSTEM_ERROR;
        dialog = new Dialog(this, R.style.OverlayDialog);
        dialog.setContentView(lockManager.getView());
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void  unlock() {
        dialog.dismiss();
        dialog = null;
    }

}
