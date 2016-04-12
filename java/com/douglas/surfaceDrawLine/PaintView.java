package com.douglas.surfaceDrawLine;

/**
 * Created by ${doufals} on 1/27/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {

    private float startX = 0;
    private float startY = 0;
    private float endX = 0;
    private float endY = 0;

    private List<RectF> rects;
    private Paint mPaint = null;

    public PaintView(Context context) {
        super(context);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFF00FF);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);

        rects = new ArrayList<RectF>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (RectF rect : rects) {
            canvas.drawRect(rect, mPaint);
        }
        RectF rect = new RectF(Math.min(startX, endX), Math.min(startY, endY),
                Math.max(startX, endX), Math.max(startY, endY));
        canvas.drawRect(rect, mPaint);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();
                RectF rect = new RectF(Math.min(startX, endX), Math.min(startY, endY),
                        Math.max(startX, endX), Math.max(startY, endY));
                rects.add(rect);
                invalidate();
                break;
        }
        return true;
    }

}
