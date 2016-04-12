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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;


public class bitmapDrawLine extends Activity {
    private final static String TAG = "drawline";
    private final static float start_pes = 350;
    private final static float end_pes = 350;
    private final static Integer count_max = 1000;

    private int x = 0, y = 0, z = 0, v = 0;
    private int r = 0, b = 0, g = 0;

    private FrameLayout drawviewFrame = null;
    private TextView myTextView = null;

    private Bitmap bitmap = Bitmap.createBitmap(500, 750, Bitmap.Config.ARGB_8888);// 用于保存所画图像的图画
    Canvas canvasSelf = new Canvas(bitmap);

    Random rdm = new Random(System.currentTimeMillis());
    Paint paint = new Paint();

    public class MyView extends View {

        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        //重写OnDraw（）函数，在每次重绘时自主实现绘图
        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            //canvas.drawBitmap(bitmap, 0, 0, paint);
            canvas.drawLine(x, y, z, v, paint);
        }

    }


    protected void reSet() {

        r = Math.abs(rdm.nextInt(255));
        b = Math.abs(rdm.nextInt(255));
        g = Math.abs(rdm.nextInt(255));

        x = (int) start_pes;
        y = (int) end_pes;
        z = Math.abs(rdm.nextInt(1000));
        v = Math.abs(rdm.nextInt(1000));

        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.rgb(r, b, g));  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(Math.abs(rdm.nextInt(10)));//设置画笔宽度

        myTextView.setText("");
        myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_draw_line);

        drawviewFrame = (FrameLayout)findViewById(R.id.Frame);
        myTextView = (TextView) findViewById(R.id.content);

        myTextView.setText("");
        myTextView.setText("颜色值: " + " [" + r + ", " + b + ", " + g  +"]" +"\n"
                     + "位置:   " + " [" + x + ", " + y + ", " + z + ", " + v + "]" +"\n");

        MyView testview = new MyView(bitmapDrawLine.this);
        drawviewFrame.addView(testview);

        canvasSelf = new Canvas(bitmap);

        for(int i = 0; i < 10; i++) {
            reSet();
            testview.draw(canvasSelf);
        }

        //DrawBitmapView
        //testview.startTranslate
    }


}