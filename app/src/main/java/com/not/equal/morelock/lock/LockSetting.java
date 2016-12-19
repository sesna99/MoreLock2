package com.not.equal.morelock.lock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;

/**
 * Created by admin on 2016-05-30.
 */
public class LockSetting extends AppCompatActivity {
    private int type;
    private RelativeLayout lockView;
    private ReturnLock returnLock;
    private StarLock starLock;
    private ToggleLock toggleLock;
    private TreeLock treeLock;
    private Button cancel, apply;
    private String password;
    private PreferencesManager manager;
    private LockManager<FrameLayout> lockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_setting);

        lockView = (RelativeLayout) findViewById(R.id.lock);
        cancel = (Button) findViewById(R.id.cancel);
        apply = (Button) findViewById(R.id.apply);
    }

    @Override
    protected void onStart() {
        super.onStart();
        type = PreferencesManager.getInstance(getApplicationContext()).getCurType();
        manager = PreferencesManager.getInstance(this);
        lockManager = new LockManager<>();
        lockManager.setType(type);

        switch (type) {
            case 0:
                returnLock = new ReturnLock(this, true, type);
                lockView.addView(returnLock);
                lockManager.setView(returnLock);
                break;
            case 1:
                starLock = new StarLock(this, true, type);
                lockView.addView(starLock);
                lockManager.setView(starLock);
                break;
            case 2:
                toggleLock = new ToggleLock(this, type);
                lockView.addView(toggleLock);
                lockManager.setView(toggleLock);
                break;
            case 3:
                break;
            case 4:
                treeLock = new TreeLock(this, true, type);
                lockView.addView(treeLock);
                lockManager.setView(treeLock);
                break;
        }


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password == null) {
                    apply.setText("확인");
                    password = lockManager.getPassword();
                    lockManager.clear();
                    lockManager.getView().invalidate();
                } else {
                    if (password.equals(lockManager.getPassword())) {
                        manager.setPassword(password, type);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        password = null;
                        apply.setText("다음");
                        Toast.makeText(getApplicationContext(), "다시 시도하세요.", Toast.LENGTH_SHORT).show();
                        lockManager.clear();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lockView = null;
        lockManager = null;
        returnLock = null;
        starLock = null;
        toggleLock = null;
        treeLock = null;
    }
}
