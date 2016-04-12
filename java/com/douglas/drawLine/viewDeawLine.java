/*
*
*PainterPanel extends JPanel implements MouseListener
*addMouseListener(this);
*
*syh*/
package com.douglas.drawLine;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.animation.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;
import android.graphics.Paint;

public class viewDeawLine extends Activity {
    private final static String TAG = "drawlineDouglas";

    private final static Integer COUNT_MAX = 20;

    private int x = 0, y = 0, z = 0, v = 0;
    private int r = 0, b = 0, g = 0;
    private static int start_pes = 0;
    private static int end_pes = 0;
    private FrameLayout drawviewFrame = null;
    private TextView myTextView = null;
    private MyView testview = null;


    private Bitmap bitmap;
    private Canvas canvasSelf;
    private Rect mSrcRect;
    private Rect mDestRect;

    Random rdm = new Random(System.currentTimeMillis());
    Paint paint = new Paint();
    private Integer count = 0;

    public class MyView extends View{
        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }
        //重写OnDraw（）函数，在每次重绘时自主实现绘图
        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            canvas.drawLine(x, y, mDestRect.left, mDestRect.top, paint);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            super.onDraw(canvas);
        }
    }

    protected void reSet() {
        r = Math.abs(rdm.nextInt(255));
        b = Math.abs(rdm.nextInt(255));
        g = Math.abs(rdm.nextInt(255));
        x = start_pes;
        y = end_pes;
        z = Math.abs(rdm.nextInt(start_pes * 2 - 1) + 1);
        v = Math.abs(rdm.nextInt(end_pes * 2 - 1) + 1);
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.rgb(r, b, g));//设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(Math.abs(rdm.nextInt(10)));//设置画笔宽度
        myTextView.setText("startTimer");
        myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n"
                + "当前直线数量： " + (COUNT_MAX - count));
    }

    protected void  startTranslate(final int x1, final int y1, final int x2, final int y2, long duration) {
        // 使用ValueAnimator创建一个过程
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentLeft = 0;
                int currentTop = 0;
                // 不断重新计算上下左右位置
                float fraction = (Float) animator.getAnimatedValue();

                if ((x2 < x1) && (y2 < y1)){
                    if(x2 > y2) {
                        mDestRect.left = (int) (x1 - (x1-x2) * fraction);// * ((float)(Math.max((x1-x2), (y1-y2)))/((float)Math.min((x1-x2), (y1-y2)))));
                        mDestRect.top = (int) (y1 - (y1-y2) * fraction);
                    }else if (x2 < y2) {
                        mDestRect.left = (int) (x1 - (x1-x2) * fraction);
                        mDestRect.top = (int) (y1 - (y1-y2) * fraction);// * ((float)(Math.max((x1-x2), (y1-y2)))/((float)Math.min((x1-x2), (y1-y2)))));
                    }
                }else if ((x2 > x1) && (y2 < y1)) {
                    if (x2  > y2) {
                        mDestRect.left = (int) (x1 + (x2-x1) * fraction);// * ((float)(Math.max((x2-x1), (y1-y2)))/((float)Math.min((x2-x1), (y1-y2)))));
                        mDestRect.top = (int) (y1 - (y1-y2) * fraction);
                    }else if (x2 < y2) {
                        mDestRect.left = (int) (x1 + (x2-x1) *  fraction);
                        mDestRect.top = (int) (y1 - (y1-y2) * fraction);// * ((float)(Math.max((x2-x1), (y1-y2)))/((float)Math.min((x2-x1), (y1-y2)))));
                    }
                }else if ((x2 < x1) && (y2 > y1)) {
                    if (x2 > y2) {
                        mDestRect.left = (int) (x1 - (x1-x2) * fraction);// * ((float)(Math.max((x1-x2), (y2-y1)))/((float)Math.min((x1-x2), (y2-y1)))));
                        mDestRect.top = (int) (y1 + (y2-y1) * fraction);
                    }else if (x2 < y2) {
                        mDestRect.left = (int) (x1 - (x1-x2) *  fraction);
                        mDestRect.top = (int) (y1 + (y2-y1) * fraction);// * ((float)(Math.max((x1-x2), (y2-y1)))/((float)Math.min((x1-x2), (y2-y1)))));
                    }
                }else if ((x2 > x1) && (y2 > y1)) {
                    if (x2 > y2) {
                        mDestRect.left = (int) (x1 + (x2-x1) * fraction);// * ((float)Math.max(x2-x1, y2-y1))/((float)Math.min(x2-x1, y2-y1)));
                        mDestRect.top = (int) (y1 + (y2-y1) * fraction);
                    }else if (x2 < y2) {
                        mDestRect.left = (int) (x1 + (x2-x1) * fraction);
                        mDestRect.top = (int) (y1 + (y2-y1) * fraction);// * ((float)Math.max(x2-x1, y2-y1))/((float)Math.min(x2-x1, y2-y1)));
                    }
                }else if((x2 < x1) && (y2 == y1)) {
                    mDestRect.left = (int) (x1 - (x1-x2) * fraction);
                    mDestRect.top = y1;
                }else if((x2 > x1) && (y2 == y1)) {
                    mDestRect.left = (int) (x1 + (x2-x1) * fraction);
                    mDestRect.top = y1;
                }else if((x2 == x1) && (y2 < y1)) {
                    mDestRect.left = x2;
                    mDestRect.top = (int) (y1 - (y1-y2) * fraction);
                }else if((x2 == x1) && (y2 > y1)) {
                    mDestRect.left = x1;
                    mDestRect.top = (int) (y1 + (y2-y1) * fraction);
                }
                mDestRect.right = mDestRect.left;
                mDestRect.bottom = mDestRect.top;
                // 重绘
                canvasSelf.drawLine(x, y, mDestRect.left, mDestRect.top, paint);
                canvasSelf.drawBitmap(bitmap, 0, 0, paint);
                testview.invalidate();
            }
        });
        valueAnimator.start();
    }

    protected void startTimer() {
        count = COUNT_MAX;
        final Timer finalTimer = new Timer();;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //handler处理消息
                if(msg.what>0){
                    reSet();
                    startTranslate(x, y, z, v, 220);
                }else{
                    //用于清空画布上的内容
                    Paint paint2 = new Paint();
                    paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    canvasSelf.drawPaint(paint2);
                    count = COUNT_MAX;

                    //finalTimer.cancel();
                }
            }
        };

        finalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName());
                Message msg = new Message();
                msg.what = count--;
                handler.sendMessage(msg);
            }

        }, 0, 250);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_draw_line);

        drawviewFrame = (FrameLayout)findViewById(R.id.Frame);
        myTextView = (TextView) findViewById(R.id.content);

        myTextView.setText("onCreate");
        myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n"
                + "当前直线数量： " + (COUNT_MAX - count));

        testview = new MyView(viewDeawLine.this);
        drawviewFrame.addView(testview);


        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth =dm.widthPixels;
        int screenHeight =dm.heightPixels;
        //int statusBarHeight = getResources().getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
        int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        /*<dimen name="status_bar_height">24dp</dimen> */
        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight;
        int contentHeight = getResources().getDimensionPixelSize(R.dimen.LinearLayouthight);

        Log.i("OnCreate", "testview.getWidth():" + drawviewFrame.getWidth() + "---------" + "testview.getHeight()" + drawviewFrame.getHeight() + "\n");//testview.getWidth():0---------testview.getHeight()0
        Log.i("OnCreate", "screenWidth: " + screenWidth+ "-----" + "screenHeight: " + screenHeight + "--------"+"\n");//screenWidth: 720-----screenHeight: 1184--------
        Log.i("OnCreate", "statusBarHeight: " + statusBarHeight + "\n");//statusBarHeight: 48
        Log.i("OnCreate", "contentTop: " + contentTop + "\n");//contentTop: 0
        Log.i("OnCreate", "titleBarHeight: " + titleBarHeight + "\n");//titleBarHeight: -48
        Log.i("OnCreate", "contentHeight: " + contentHeight + "\n");//contentHeight: 244

        start_pes = screenWidth / 2;
        end_pes = (screenHeight - contentHeight - statusBarHeight) / 2;
        mSrcRect = new Rect(start_pes, end_pes, start_pes, end_pes);
        mDestRect = new Rect(start_pes, end_pes, start_pes, end_pes);
        Log.i("OnCreate", "start_pes: " + start_pes + "\n");//start_pes: 360
        Log.i("OnCreate", "end_pes: " + end_pes + "\n");//end_pes: 446

        bitmap = Bitmap.createBitmap((start_pes * 2), (end_pes * 2), Bitmap.Config.ARGB_8888);
        canvasSelf = new Canvas(bitmap);

        startTimer();
    }
}