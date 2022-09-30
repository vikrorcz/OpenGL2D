package com.bura.opengl.engine;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class MyGLSurfaceView extends GLSurfaceView {

    private MyRenderer renderer;
    private boolean rendererSet = false;

    public MyGLSurfaceView(Context context) {
        super(context);
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000 ||
                        Build.FINGERPRINT.startsWith("generic") ||
                        Build.FINGERPRINT.startsWith("unknown") ||
                        Build.MODEL.contains("google_sdk") ||
                        Build.MODEL.contains("Emulator") ||
                        Build.MODEL.contains("Android SDK built for x86");

        if (supportsEs2) {
            renderer = new MyRenderer(context);

            this.setEGLContextClientVersion(2);
            this.setRenderer(renderer);
            rendererSet = true;
        } else {
            Log.e("ERROR", "DEVICE DOES NOT SUPPORT GLES20");
        }
    }

    //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TOUCH_SCALE_FACTOR = 180f / 360;
    private float previousX;
    private float previousY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (rendererSet) {
            float x = e.getX();
            float y = e.getY();
            renderer.x  = x;
            renderer.y = y;
            System.out.println("x= " + x + "y= " + y);

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    renderer.isTouching = true;

                    float dx = x - previousX;
                    float dy = y - previousY;

                    if (y > getHeight() / 2f) {
                        dx = dx * -1 ;
                    }

                    if (x < getWidth() / 2f) {
                        dy = dy * -1 ;
                    }

                    //renderer.setAngle(
                    //        renderer.getAngle() +
                    //                ((dx + dy) * TOUCH_SCALE_FACTOR));

                    requestRender();
                    break;

                default:
                    renderer.isTouching = false;
                    break;
            }

            previousX = x;
            previousY = y;
        }
        return true;
    }
}
