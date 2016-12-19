package com.not.equal.morelock.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kyleduo.switchbutton.SwitchButton;
import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;

import java.util.ArrayList;

/**
 * Created by admin on 2016-05-31.
 */
public class LockManager<T> {
    T view;
    int type;

    public void setView(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        switch (type) {
            case 0:
                return ((ReturnLock) view).getPassword();
            case 1:
                return ((StarLock) view).getPassword();
            case 2:
                return ((ToggleLock) view).getPassword();
            case 3:
                return "";
            case 4:
                return ((TreeLock) view).getPassword();
        }
        return "";
    }

    public void clear() {
        switch (type) {
            case 0:
                ((ReturnLock) view).clear();
                break;
            case 1:
                ((StarLock) view).clear();
                break;
            case 2:
                ((ToggleLock) view).clear();
                break;
            case 3:
                break;
            case 4:
                ((TreeLock) view).clear();
                break;
        }
    }

    public boolean isClear() {
        switch (type) {
            case 0:
                return ((ReturnLock) view).isClear();
            case 1:
                return ((StarLock) view).isClear();
            case 2:
                return ((ToggleLock) view).isClear();
            case 3:
                return false;
            case 4:
                return ((TreeLock) view).isClear();
        }
        return false;
    }
}

class ReturnLock extends FrameLayout {
    private LineDrawerView drawerView;
    private FrameLayout frame;
    private PatternView pt1, pt2, pt3, pt4, pt5, pt6, pt7, pt8, pt9;
    private ArrayList<PatternView> ptArray;
    private LayoutInflater inflater;
    private boolean isSetting;
    private int type;

    public ReturnLock(Context context) {
        super(context);
        init();
    }

    public ReturnLock(Context context, boolean isSetting, int type) {
        super(context);
        this.isSetting = isSetting;
        this.type = type;
        init();
    }

    public ReturnLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReturnLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frame = (FrameLayout) inflater.inflate(R.layout.activity_pttern, null);
        drawerView = (LineDrawerView) frame.findViewById(R.id.drawer);

        pt1 = (PatternView) frame.findViewById(R.id.pt1);
        pt2 = (PatternView) frame.findViewById(R.id.pt2);
        pt3 = (PatternView) frame.findViewById(R.id.pt3);
        pt4 = (PatternView) frame.findViewById(R.id.pt4);
        pt5 = (PatternView) frame.findViewById(R.id.pt5);
        pt6 = (PatternView) frame.findViewById(R.id.pt6);
        pt7 = (PatternView) frame.findViewById(R.id.pt7);
        pt8 = (PatternView) frame.findViewById(R.id.pt8);
        pt9 = (PatternView) frame.findViewById(R.id.pt9);

        ptArray = new ArrayList<>();
        ptArray.add(pt1);
        ptArray.add(pt2);
        ptArray.add(pt3);
        ptArray.add(pt4);
        ptArray.add(pt5);
        ptArray.add(pt6);
        ptArray.add(pt7);
        ptArray.add(pt8);
        ptArray.add(pt9);
        drawerView.setPtArray(ptArray);
        drawerView.setisSetting(isSetting);
        drawerView.setType(type);

        frame.setBackgroundColor(Color.BLACK);
        frame.setForegroundGravity(Gravity.CENTER);
        addView(frame);
    }

    public String getPassword() {
        return drawerView.getPassword();
    }

    public boolean isClear() {
        return drawerView.isClear();
    }

    public void clear() {
        drawerView.clear();
    }
}

class StarLock extends FrameLayout {
    private LineDrawerView drawerView;
    private FrameLayout frame;
    private ArrayList<PatternView> ptArray;
    private LayoutInflater inflater;
    private boolean isSetting;
    private int type;
    private Animation an;

    public StarLock(Context context) {
        super(context);
        init();
    }

    public StarLock(Context context, boolean isSetting, int type) {
        super(context);
        this.isSetting = isSetting;
        this.type = type;
        init();
    }

    public StarLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frame = (FrameLayout) inflater.inflate(R.layout.activity_star, null);
        drawerView = (LineDrawerView) frame.findViewById(R.id.drawer);

        an = AnimationUtils.loadAnimation(getContext(), R.anim.animationmotion);

        ptArray = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            ptArray.add((PatternView) frame.findViewById(R.id.pt1 + i));
            ptArray.get(i).setAnimation(an);
        }

        drawerView.setPtArray(ptArray);
        drawerView.setisSetting(isSetting);
        drawerView.setType(type);

        frame.setBackgroundColor(Color.BLACK);
        addView(frame);
    }

    public String getPassword() {
        return drawerView.getPassword();
    }

    public boolean isClear() {
        return drawerView.isClear();
    }

    public void clear() {
        drawerView.clear();
    }
}

class ToggleLock extends FrameLayout {
    private LayoutInflater inflater;
    private FrameLayout frame;
    private RelativeLayout touchLayout;
    private LinearLayout linear;
    private ArrayList<SwitchButton> switches;
    private int type;
    private StringBuilder password;
    private boolean isClear;
    private float lastTouch;

    public ToggleLock(Context context) {
        super(context);
        init();
    }

    public ToggleLock(Context context, int type) {
        super(context);
        this.type = type;
        init();
    }


    public ToggleLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToggleLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frame = (FrameLayout) inflater.inflate(R.layout.activity_toggle, null);
        touchLayout = (RelativeLayout) frame.findViewById(R.id.touch);
        linear = (LinearLayout) frame.findViewById(R.id.linear);

        switches = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            switches.add((SwitchButton) frame.findViewById(R.id.switch01 + i));
        }

        touchLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN)
                    chkTouchView(event.getRawX(), event.getRawY());
                else if (action == MotionEvent.ACTION_MOVE)
                    chkTouchView(event.getRawX(), event.getRawY());
                else if (action == MotionEvent.ACTION_UP) {
                    lastTouch = 10;
                }
                return true;
            }
        });

        linear.setBackgroundColor(Color.WHITE);
        linear.setGravity(Gravity.CENTER);
        addView(frame);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        password = new StringBuilder();
        isClear = false;

        lastTouch = 10;
    }

    //터치된 장소에 뷰가 있는지 검사
    public boolean chkTouchInside(View view, float x, float y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (x >= location[0]) {
            if (x <= location[0] + view.getWidth()) {
                if (y >= location[1]) {
                    if (y <= location[1] + view.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //터치된 장소에 뷰가 있는지 검사하고 비밀번호 저장
    public boolean chkTouchView(float x, float y) {
        for (int i = 0; i < switches.size(); i++)
            if (lastTouch != i && chkTouchInside(switches.get(i), x, y)) {
                lastTouch = i;
                switches.get(i).setChecked(!switches.get(i).isChecked());
                isClear = PreferencesManager.getInstance(getContext()).getPassword(type).equals(getPassword());
                return true;
            }
        return false;
    }


    public String getPassword() {
        password = new StringBuilder();
        for (int i = 0; i < switches.size(); i++)
            password.append(switches.get(i).isChecked() ? i : "");
        return password.toString();
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        for (int i = 0; i < switches.size(); i++)
            switches.get(i).setChecked(false);
    }
}

class TreeLock extends FrameLayout implements View.OnTouchListener {
    private ArrayList<ImageButton> color, apples;
    private ImageButton clean, apple1, apple2, apple3, apple4, apple5, apple6;
    private int type, currentPosition;
    private StringBuilder password;
    private LinearLayout linear;
    private LayoutInflater inflater;
    private boolean isClear, isSetting;

    public TreeLock(Context context) {
        super(context);
        init();
    }

    public TreeLock(Context context, boolean isSetting, int type) {
        super(context);
        this.type = type;
        this.isSetting = isSetting;
        init();
    }

    public TreeLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TreeLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linear = (LinearLayout) inflater.inflate(R.layout.activity_tree, null);
        color = new ArrayList<>();
        apples = new ArrayList<>();
        apple1 = (ImageButton) linear.findViewById(R.id.apple1);
        apple2 = (ImageButton) linear.findViewById(R.id.apple2);
        apple3 = (ImageButton) linear.findViewById(R.id.apple3);
        apple4 = (ImageButton) linear.findViewById(R.id.apple4);
        apple5 = (ImageButton) linear.findViewById(R.id.apple5);
        apple6 = (ImageButton) linear.findViewById(R.id.apple6);
        apples.add(apple1);
        apples.add(apple2);
        apples.add(apple3);
        apples.add(apple4);
        apples.add(apple5);
        apples.add(apple6);

        for (int i = 0; i < 9; i++) {
            color.add((ImageButton) linear.findViewById(R.id.color1 + i));
            color.get(i).setOnTouchListener(this);
        }
        clean = (ImageButton) linear.findViewById(R.id.img_clean);
        clean.setOnTouchListener(this);

        password = new StringBuilder();

        currentPosition = 0;

        isClear = false;

        addView(linear);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            switch (id) {
                case R.id.color1:
                    setBackground(R.drawable.redapples, 1);
                    break;
                case R.id.color2:
                    setBackground(R.drawable.orangeapples, 2);
                    break;
                case R.id.color3:
                    setBackground(R.drawable.yellowapples, 3);
                    break;
                case R.id.color4:
                    setBackground(R.drawable.greenapples, 4);
                    break;
                case R.id.color5:
                    setBackground(R.drawable.blueapples, 5);
                    break;
                case R.id.color6:
                    setBackground(R.drawable.skyblueapples, 6);
                    break;
                case R.id.color7:
                    setBackground(R.drawable.puppleapples, 7);
                    break;
                case R.id.color8:
                    setBackground(R.drawable.whiteapples, 8);
                    break;
                case R.id.color9:
                    setBackground(R.drawable.blackapples, 9);
                    break;
                case R.id.img_clean:
                    deleteBackground();
                    break;
            }
        }
        return false;
    }

    public String getPassword() {
        return password.toString();
    }

    public void setBackground(int res, int number) {
        if (currentPosition < 6) {
            apples.get(currentPosition++).setBackgroundResource(res);
            password.append(number);
            isClear = PreferencesManager.getInstance(getContext()).getPassword(type).equals(password.toString());
            if (currentPosition == 6 && !isClear && !isSetting)
                clear();
        }
    }

    public void deleteBackground() {
        if (currentPosition > 0) {
            apples.get(--currentPosition).setBackgroundResource(R.drawable.backapples);
            if (password.length() > 1)
                password.deleteCharAt(password.length() - 1);
            else
                password = new StringBuilder();
        }
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        currentPosition = 0;
        password = new StringBuilder();
        for (int i = 0; i < apples.size(); i++)
            apples.get(i).setBackgroundResource(R.drawable.backapples);
    }
}

class LineDrawerView extends View {
    private Paint oneTouchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint twoTouchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public float initX, initY, curX, curY;
    private boolean drawing = false;
    private int radius = 60;
    private StringBuilder password;
    private ArrayList<PatternView> ptArray;
    private ArrayList<String> ptXYArray;
    private ArrayList<Float> xArray;
    private ArrayList<Float> yArray;
    private int lastPassword;
    private float lastX, lastY;
    private boolean isSetting, isClear;
    private int type;
    private int limit;

    public LineDrawerView(Context context) {
        super(context);
        init();
    }

    public LineDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        oneTouchPaint.setStyle(Paint.Style.STROKE);
        oneTouchPaint.setStrokeWidth(10);
        oneTouchPaint.setColor(Color.WHITE);
        oneTouchPaint.setAlpha(150);
        oneTouchPaint.setStrokeMiter(1000);
        oneTouchPaint.setStrokeCap(Paint.Cap.ROUND);
        oneTouchPaint.setStrokeJoin(Paint.Join.BEVEL);
        twoTouchPaint.setStyle(Paint.Style.STROKE);
        twoTouchPaint.setStrokeWidth(10);
        twoTouchPaint.setColor(Color.GREEN);
        twoTouchPaint.setAlpha(150);
        twoTouchPaint.setStrokeMiter(1000);
        twoTouchPaint.setStrokeCap(Paint.Cap.ROUND);
        twoTouchPaint.setStrokeJoin(Paint.Join.BEVEL);
        password = new StringBuilder();
        xArray = new ArrayList<>();
        yArray = new ArrayList<>();
        ptXYArray = new ArrayList<>();
        isClear = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float xy1[], xy2[];
        char temp[] = this.password.toString().toCharArray();
        int password[] = new int[temp.length];

        if (drawing) {
            for (int i = 0; i < temp.length; i++) {
                password[i] = Integer.valueOf(String.valueOf(temp[i]));
            }
            for (int i = 0; i < password.length; i++) {
                if (i != 0) {
                    xy1 = getXY(password[i - 1] - 1);
                    xy2 = getXY(password[i] - 1);
                    canvas.drawLine(xy1[0], xy1[1], xy2[0], xy2[1], oneTouchPaint);
                }
            }
            if (type == 0) {
                for (int i = 0; i < ptArray.size(); i++) {
                    xy1 = getXY(i);
                    Log.i("limit", getLimit(i) + ", " + i);
                    if (getLimit(i) == 1) {
                        canvas.drawCircle(xy1[0], xy1[1], radius, oneTouchPaint);
                    } else if (getLimit(i) > 1) {
                        canvas.drawCircle(xy1[0], xy1[1], radius, twoTouchPaint);
                    }
                }
            }
            canvas.drawLine(initX, initY, curX, curY, oneTouchPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        invalidate();
        if (action == MotionEvent.ACTION_DOWN) {
            if (isSetting)
                clear();
            if (chkTouchView(event.getRawX(), event.getRawY())) {
                curX = initX;
                curY = initY;
                drawing = true;
            }
        } else if (drawing && action == MotionEvent.ACTION_MOVE) {
            chkTouchView(event.getRawX(), event.getRawY());
            curX = event.getX();
            curY = event.getY();
        } else if (action == MotionEvent.ACTION_UP) {
            isClear = password.toString().equals(PreferencesManager.getInstance(getContext()).getPassword(type));
            if (!isSetting)
                clear();
            else {
                curX = initX;
                curY = initY;
            }
        }
        invalidate();
        return true;
    }

    //라인 시작 지점 설정
    public void touchStart(float x, float y, PatternView view, boolean drawing, int position) {
        int size = xArray.size();
        int passwordSize = password.length();
        String firstPassword;
        String secondPassword;
        int result;

        if (type == 0) {
            if (size > 0) {
                if (passwordSize > 1) {
                    firstPassword = password.substring(passwordSize - 2, passwordSize - 1);
                    secondPassword = password.substring(passwordSize - 1);
                    result = chkPassword(firstPassword, secondPassword);
                    if (result != 0) {
                        insertPassword(passwordSize, size, result, x, y);
                    } else {
                        setXY(x, y);
                        firstPassword = password.substring(passwordSize - 2, passwordSize - 1);
                        result = chkPassword(firstPassword, secondPassword);
                        Log.i("result", result + "");
                        if (result != 0)
                            insertPassword(passwordSize, size, result, x, y);
                        Log.i("password", password.toString());
                    }
                } else if (size < passwordSize) {
                    setXY(x, y);
                }
            } else {
                setXY(x, y);
            }
        } else {
            setXY(x, y);
        }

        initX = x;
        initY = y;
        if (drawing)
            increaseLimit(view);
        invalidate();
    }

    public int chkPassword(String firstPassword, String secondPassword) {
        if (firstPassword.equals("1") && secondPassword.equals("7") || firstPassword.equals("7") && secondPassword.equals("1")) {
            return 4;
        } else if (firstPassword.equals("2") && secondPassword.equals("8") || firstPassword.equals("8") && secondPassword.equals("2")) {
            return 5;
        } else if (firstPassword.equals("3") && secondPassword.equals("9") || firstPassword.equals("9") && secondPassword.equals("3")) {
            return 6;
        } else if (firstPassword.equals("1") && secondPassword.equals("3") || firstPassword.equals("3") && secondPassword.equals("1")) {
            return 2;
        } else if (firstPassword.equals("4") && secondPassword.equals("6") || firstPassword.equals("6") && secondPassword.equals("4")) {
            return 5;
        } else if (firstPassword.equals("7") && secondPassword.equals("9") || firstPassword.equals("9") && secondPassword.equals("7")) {
            return 8;
        } else if (firstPassword.equals("1") && secondPassword.equals("9") || firstPassword.equals("9") && secondPassword.equals("1")) {
            return 5;
        } else if (firstPassword.equals("3") && secondPassword.equals("7") || firstPassword.equals("7") && secondPassword.equals("3")) {
            return 5;
        } else {
            return 0;
        }
    }

    public void insertPassword(int passwordSize, int arraySize, int position, float x, float y) {
        password.insert(passwordSize - 1, position);
        String temp[] = ptXYArray.get(position - 1).split(",");
        increaseLimit(ptArray.get(position - 1));
        xArray.add(arraySize - 1, Float.valueOf(temp[0]));
        yArray.add(arraySize - 1, Float.valueOf(temp[1]));
        xArray.add(x);
        yArray.add(y);
        invalidate();
    }

    //터치된 장소에 뷰가 있는지 검사
    public boolean chkTouchInside(View view, float x, float y, int position) {
        int[] location = new int[2];
        float tempX = 0, tempY = 0;
        view.getLocationOnScreen(location);

        tempX = location[0] + view.getWidth() / 2;
        if (!isSetting)
            if (type == 0)
                tempY = (float) (location[1] + (view.getHeight() / 10));
            else
                tempY = (float) (location[1] - (view.getHeight() / 8));
        else if (type == 0)
            tempY = (float) (location[1] - view.getHeight() / 1.25);
        else
            tempY = (float) (location[1] - (view.getHeight() * 2));

        if (x >= location[0]) {
            if (x <= location[0] + view.getWidth()) {
                if (y >= location[1]) {
                    if (y <= location[1] + view.getHeight()) {
                        if (lastX == tempX && lastY == tempY) {
                            touchStart(lastX, lastY, (PatternView) view, false, position);
                            return true;
                        }
                        lastX = tempX;
                        lastY = tempY;
                        touchStart(lastX, lastY, (PatternView) view, true, position);
                        return true;
                    }
                }
            }
        }
        invalidate();
        return false;
    }

    //터치된 장소에 뷰가 있는지 검사하고 비밀번호 저장
    public boolean chkTouchView(float x, float y) {
        if (ptXYArray.size() == 0) {
            int location[] = new int[2];
            float tempY = 0;
            for (View view : ptArray) {
                view.getLocationOnScreen(location);
                if (!isSetting)
                    if (type == 0)
                        tempY = (float) (location[1] + (view.getHeight() / 10));
                    else
                        tempY = (location[1] - (view.getHeight() / 8));
                else if (type == 0)
                    tempY = (float) (location[1] - view.getHeight() / 1.25);
                else
                    tempY = (float) (location[1] - (view.getHeight() * 2));
                ptXYArray.add((location[0] + view.getWidth() / 2) + "," + tempY);
            }
        }
        for (int i = 0; i < ptArray.size(); i++) {
            if (ptArray.get(i).getLimit() < limit) {
                if (chkTouchInside(ptArray.get(i), x, y, i)) {
                    if (password.length() > 0) {
                        if (lastPassword != i + 1) {
                            password.append(i + 1);
                            lastPassword = i + 1;
                        }
                    } else {
                        lastPassword = i + 1;
                        password.append(i + 1);
                    }
                    invalidate();
                    return true;
                }
            }
        }
        invalidate();
        return false;
    }

    public void setXY(float x, float y) {
        xArray.add(x);
        yArray.add(y);
        invalidate();
    }

    public float[] getXY(int position) {
        String temp[] = ptXYArray.get(position).split(",");
        float xy[] = new float[2];
        xy[0] = Float.valueOf(temp[0]);
        xy[1] = Float.valueOf(temp[1]);
        return xy;
    }

    public void setPtArray(ArrayList<PatternView> array) {
        ptArray = array;
    }

    public void setisSetting(boolean isSetting) {
        this.isSetting = isSetting;
    }

    public int getLimit(int position) {
        return ptArray.get(position).getLimit();
    }

    public void setType(int type) {
        this.type = type;
        if (type == 0)
            limit = 2;
        else
            limit = 1;
    }

    public void increaseLimit(PatternView view) {
        view.setLimit(view.getLimit() + 1);
    }

    public void clearLimit() {
        for (PatternView view : ptArray)
            view.setLimit(0);
    }

    public String getPassword() {
        return password.toString();
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        drawing = false;
        xArray.clear();
        yArray.clear();
        password = new StringBuilder();
        clearLimit();
        lastX = 0;
        lastY = 0;
        invalidate();
    }

}

class PatternView extends ImageView {
    public int limit = 0;

    public PatternView(Context context) {
        super(context);
    }

    public PatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}



