package com.douglas.surfaceDrawLine;

/**
 * Created by ${doufals} on 1/27/16.
 */

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
/*
public class SurfacePaintView extends SurfaceView implements Callback {

    public static final int SHAPE_CURVE = 1;
    public static final int SHAPE_LINE = 2;
    public static final int SHAPE_SQUARE = 3;
    public static final int SHAPE_OVAL = 4;
    public static final int SHAPE_CIRCLE = 5;

    private int mShape = SHAPE_CURVE;
    private Context mContext;
    private SurfaceHolder mHolder;

    private Paint mPaint;
    private Bitmap mBitmap;
    private Path mPath;
    private Canvas mCanvas;

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    private boolean isPaint = false;
    private boolean isClear = false;

    public SurfacePaintView(Context context) {
        super(context);
        init(context);
    }

    public SurfacePaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SurfacePaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        mHolder = this.getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        mPaint = new Paint();
        mPaint.setColor(0xFFFF00FF);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);

        mPath = new Path();
        mBitmap = Bitmap.createBitmap(DisplayUtil.getScreenWidth(mContext),
                DisplayUtil.getScreenHeight(mContext), Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        isPaint = true;
        isClear = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchBegan(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMoved(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchEnded(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void touchBegan(float x, float y) {
        if (mShape == SHAPE_CURVE) {
            startX = x;
            startY = y;
            mPath.moveTo(startX, startY);
        } else {
            startX = x;
            startY = y;
            stopX = x;
            stopY = y;
        }
    }

    private void touchMoved(float x, float y) {
        if (mShape == SHAPE_CURVE) {
            float dx = Math.abs(x - startX);
            float dy = Math.abs(y - startY);
            if (dx > 0 || dy > 0) {
                mPath.quadTo(startX, startY, (x + startX) / 2,
                        (y + startY) / 2);
                startX = x;
                startY = y;
            }
            mCanvas.drawPath(mPath, mPaint);
        } else {
            stopX = x;
            stopY = y;
        }
    }

    private void touchEnded(float x, float y) {
        switch (mShape) {
            case SHAPE_CURVE:
                mPath.lineTo(x, y);
                mCanvas.drawPath(mPath, mPaint);
                break;
            case SHAPE_LINE:
                stopX = x;
                stopY = y;
                mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
                break;
            case SHAPE_CIRCLE:
                stopX = x;
                stopY = y;
                float radius = (float) Math.sqrt(Math.pow(
                        startX - stopX, 2) + Math.pow(startY - stopY, 2)) / 2;
                mCanvas.drawCircle((startX + stopX) /2, (startY + stopY) /2, radius, mPaint);
                break;
            case SHAPE_SQUARE:
                stopX = x;
                stopY = y;
                RectF rect = new RectF(Math.min(startX, stopX), Math.min(startY, stopY),
                        Math.max(startX, stopX), Math.max(startY, stopY));
                mCanvas.drawRect(rect, mPaint);
                break;
            case SHAPE_OVAL:
                stopX = x;
                stopY = y;
                RectF oval = new RectF(Math.min(startX, stopX), Math.min(startY, stopY),
                        Math.max(startX, stopX), Math.max(startY, stopY));
                mCanvas.drawOval(oval, mPaint);
                break;
        }
    }

    public void setShape(int shape) {
        mShape = shape;
        isPaint = false;
    }

    public void clear() {
        if (mBitmap != null) {
            mBitmap = Bitmap.createBitmap(DisplayUtil.getScreenWidth(mContext),
                    DisplayUtil.getScreenHeight(mContext), Config.ARGB_8888);
            mCanvas.setBitmap(mBitmap);
        }
        if (mPath != null) {
            mPath.reset();
        }
        isClear = true;
        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new PaintThread()).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                    canvas.drawBitmap(mBitmap, 0, 0, null);

                    if (isPaint) {
                        switch (mShape) {
                            case SHAPE_LINE:
                                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
                                break;
                            case SHAPE_CIRCLE:
                                float radius = (float) Math.sqrt(Math.pow(startX - stopX, 2)
                                        + Math.pow(startY - stopY, 2)) / 2;
                                canvas.drawCircle((startX + stopX) / 2,
                                        (startY + stopY) / 2, radius, mPaint);
                                break;
                            case SHAPE_SQUARE:
                                RectF rect = new RectF(Math.min(startX, stopX), Math.min(startY, stopY),
                                        Math.max(startX, stopX), Math.max(startY, stopY));
                                canvas.drawRect(rect, mPaint);
                                break;
                            case SHAPE_OVAL:
                                RectF oval = new RectF(Math.min(startX, stopX), Math.min(startY, stopY),
                                        Math.max(startX, stopX), Math.max(startY, stopY));
                                canvas.drawOval(oval, mPaint);
                                break;
                        }

                        if (isClear) {
                            Paint paint = new Paint();
                            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                            canvas.drawPaint(paint);
//                          用于清空画布上的内容
                        }
                    }
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
*/