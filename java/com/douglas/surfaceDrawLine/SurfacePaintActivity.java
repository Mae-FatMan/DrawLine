package com.douglas.surfaceDrawLine;

/**
 * Created by ${doufals} on 1/27/16.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
/*
public class SurfacePaintActivity extends Activity implements OnClickListener {

    private static final int ADD_TOOLS = 0;
    private static final int REMOVE_TOOLS = 1;

    private SurfacePaintView mPaintView;
    private View mToolsView;
    private boolean isTools = false;

    private WindowManager mWM;
    private WindowManager.LayoutParams toolsParams;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_TOOLS:
                    if (!isTools) {
                        mWM.addView(mToolsView, toolsParams);
                        isTools = true;
                    }
                    break;
                case REMOVE_TOOLS:
                    if (isTools) {
                        mWM.removeView(mToolsView);
                        isTools = false;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPaintView = new SurfacePaintView(this);
        setContentView(mPaintView);

        initTools();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(ADD_TOOLS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.sendEmptyMessage(REMOVE_TOOLS);
    }

    private void initTools() {
        mToolsView = LayoutInflater.from(this).inflate(R.layout.surface_bottom_tools, null);

        mToolsView.findViewById(R.id.btn_surface_shape_curve).setOnClickListener(this);
        mToolsView.findViewById(R.id.btn_surface_shape_line).setOnClickListener(this);
        mToolsView.findViewById(R.id.btn_surface_shape_square).setOnClickListener(this);
        mToolsView.findViewById(R.id.btn_surface_shape_circle).setOnClickListener(this);
        mToolsView.findViewById(R.id.btn_surface_shape_oval).setOnClickListener(this);
        mToolsView.findViewById(R.id.btn_surface_clear).setOnClickListener(this);

        mWM = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        toolsParams = new LayoutParams();
        toolsParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        toolsParams.format = PixelFormat.RGBA_8888;
        toolsParams.flags = LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        toolsParams.width = LayoutParams.WRAP_CONTENT;
        toolsParams.height = LayoutParams.WRAP_CONTENT;
        toolsParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        mHandler.sendEmptyMessage(ADD_TOOLS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_surface_shape_curve:
                mPaintView.setShape(SurfacePaintView.SHAPE_CURVE);
                break;
            case R.id.btn_surface_shape_line:
                mPaintView.setShape(SurfacePaintView.SHAPE_LINE);
                break;
            case R.id.btn_surface_shape_square:
                mPaintView.setShape(SurfacePaintView.SHAPE_SQUARE);
                break;
            case R.id.btn_surface_shape_circle:
                mPaintView.setShape(SurfacePaintView.SHAPE_CIRCLE);
                break;
            case R.id.btn_surface_shape_oval:
                mPaintView.setShape(SurfacePaintView.SHAPE_OVAL);
                break;
            case R.id.btn_surface_clear:
                mPaintView.clear();
                break;
        }
    }

}
*/