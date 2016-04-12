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
import android.util.Log;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.animation.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class drawLineActivity extends Activity {
    private final static String TAG = "drawlineDouglas";
    private final static float start_pes = 350;
    private final static float end_pes = 350;
    private final static Integer COUNT_MAX = 1000;

    private int x = 0, y = 0, z = 0, v = 0;
    private int r = 0, b = 0, g = 0;

    private FrameLayout drawviewFrame = null;
    private TextView myTextView = null;
    private MyView testview = null;
    private SurfaceHolder mHolder = null;

    private Bitmap bitmap;
    private Canvas canvasSelf;
    private Rect mSrcRect = new Rect(350, 350, 350, 350);
    private Rect mDestRect = new Rect(350, 350, 350, 350);

    private boolean isClear = false;
    Random rdm = new Random(System.currentTimeMillis());
    Paint paint = new Paint();
    private Integer count = 0;

    public class MyView extends SurfaceView implements Callback {
        private Context mContext;

        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            init(context);
        }
        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }
        public MyView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context);
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startTimer();
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {        }


        private void init(Context context) {
            this.mContext = context;

            mHolder = this.getHolder();
            mHolder.addCallback(this);
            setZOrderOnTop(true);
            mHolder.setFormat(PixelFormat.TRANSLUCENT);

            bitmap = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888);
            canvasSelf = new Canvas(bitmap);
        }

        public void clear() {
            if (bitmap != null) {
                bitmap = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888);
                canvasSelf.setBitmap(bitmap);
            }
            isClear = true;
            invalidate();
        }

        protected void reSet() {
            r = Math.abs(rdm.nextInt(255));
            b = Math.abs(rdm.nextInt(255));
            g = Math.abs(rdm.nextInt(255));
            x = (int) start_pes;
            y = (int) end_pes;
            z = Math.abs(rdm.nextInt(699) + 1);
            v = Math.abs(rdm.nextInt(699) + 1);
            paint.setAntiAlias(true);//抗锯齿功能
            paint.setColor(Color.rgb(r, b, g));//设置画笔颜色
            paint.setStyle(Paint.Style.FILL);//设置填充样式
            paint.setStrokeWidth(Math.abs(rdm.nextInt(10)));//设置画笔宽度
            myTextView.setText("startTimer");
            myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                    + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n");
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
                    testview.invalidate();
                    //Log.i("startTranslate", "*****************************************************");
                    //Log.i("startTranslate", "testview.invalidate()");
                }
            });
            valueAnimator.start();
            Log.i("startTranslate", "*****************************************************");
            Log.i("startTranslate", "valueAnimator.start()");
        }

        protected void startTimer() {
            final Timer finalTimer = new Timer();;
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //handler处理消息
                    if(msg.what>0){
                        reSet();
                        startTranslate(x, y, z, v, 3000);
                    }else{
                        //在handler里可以更改UI组件

                        finalTimer.cancel();
                    }
                }
            };
            // 定义计划任务，根据参数的不同可以完成以下种类的工作：在固定时间执行某任务，在固定时间开始重复执行某任务，重复时间间隔可控，在延迟多久后执行某任务，在延迟多久后重复执行某任务，重复时间间隔可控
            finalTimer.schedule(new TimerTask() {
                int i = 10;
                // TimerTask 是个抽象类,实现的是Runable类
                @Override
                public void run() {
                    Log.i(TAG, Thread.currentThread().getName());
                    //定义一个消息传过去
                    Message msg = new Message();
                    msg.what = i--;
                    handler.sendMessage(msg);
                }

            }, 0, 3000);
        }

        public class PaintThread implements Runnable {
            @Override
            public void run() {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                    canvas.drawBitmap(bitmap, 0, 0, null);

                    reSet();

                    canvas.drawLine(x, y, z, v, paint);

                    if (isClear) {
                        Paint paint = new Paint();
                        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                        canvas.drawPaint(paint);//用于清空画布上的内容
                    }
                }
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_draw_line);

        drawviewFrame = (FrameLayout)findViewById(R.id.Frame);
        myTextView = (TextView) findViewById(R.id.content);

        myTextView.setText("onCreate");
        myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n");

        testview = new MyView(drawLineActivity.this);
        drawviewFrame.addView(testview);
        testview.draw(canvasSelf);
    }
}