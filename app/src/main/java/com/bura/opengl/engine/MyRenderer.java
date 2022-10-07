package com.bura.opengl.engine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glColorMask;
import static android.opengl.GLES20.glViewport;

import com.bura.opengl.engine.Engine;
import com.bura.opengl.object.Joystick;
import com.bura.opengl.object.Rectangle;
import com.bura.opengl.object.Texture;
import com.bura.opengl.object.Triangle;
import com.bura.opengl.util.MathUtils;


public class MyRenderer implements Renderer {

    private final Engine engine;

    public volatile float angle;
    public volatile boolean isTouching;
    public volatile float touchX;
    public volatile float touchY;
    public volatile int currentMotionEvent;

    private final Rectangle rectangle;
    private final Triangle triangle;
    private final Rectangle seeker;
    //private final Texture texture;

    public MyRenderer(Context context){
        engine = new Engine(context);

        rectangle = new Rectangle(engine, 0f,0f, 2);
        rectangle.setColor(new float[]{0.63671875f, 0.76953125f, 0.22265625f, 1.0f});

        seeker = new Rectangle(engine, 0f,0f, 2);
        seeker.setColor(new float[]{0.4453671875f, 0.556953125f, 0.8742265625f, 1.0f});

        triangle = new Triangle(engine, 0f,0f, 1);
        triangle.setColor(new float[]{0.83671875f, 0.26953125f, 0.22265625f, 1.0f});

        //texture = new Texture(engine, 0,0);
    }


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        engine.createShaders();
        engine.createObjects();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        System.out.println("ratio= " + ratio);
        engine.screenWidthPixel = width;
        engine.screenHeightPixel = height;
        engine.screenWidth = ratio * 2;
        engine.screenHeight = ratio;
        System.out.println("ScreenWidth: " + engine.screenWidth + "ScreenHeight: " + engine.screenHeight);
        Matrix.frustumM(engine.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        engine.fpsCounter.logFrame();
        inputUpdate();

        glClear(GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(engine.viewMatrix, 0, 0, 0f, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);


        engine.matrixUtil.cameraTranslate(engine.joystick.getAngle());//->

        triangle.draw();

        engine.matrixUtil.rotate(angle, rectangle.getCenterX(), rectangle.getCenterY());
        rectangle.draw();
        engine.matrixUtil.restore();

        engine.matrixUtil.translateByAngle((float) MathUtils.getAngle(seeker.getCenterX(), seeker.getCenterY(), engine.cameraCenterX,  engine.cameraCenterY));
        seeker.draw();
        engine.matrixUtil.restore();
        //<-

        engine.matrixUtil.withCameraTranslate();//->
        engine.matrixUtil.translateAndRotate((float) Math.toDegrees(engine.joystick.getAngle()) + 90, 0,0, triangle.getCenterX(),triangle.getCenterY());
        engine.texture.draw();
        engine.matrixUtil.restore();

        engine.matrixUtil.translate(engine.cameraCenterX, engine.cameraCenterY);
        engine.joystick.draw();
        engine.matrixUtil.restore();
        //<-


    }

    private void inputUpdate() {
        switch (currentMotionEvent) {
            case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_DOWN:
                    engine.screenTouchX = (touchX / engine.screenWidthPixel) * 8;
                    engine.screenTouchX -= 4;
                    engine.screenTouchY = (touchY / engine.screenHeightPixel) * 4;
                    engine.screenTouchY = -engine.screenTouchY + 2;

                    float cx = -2.5f;
                    float cy = -1f;
                    float deltaX = engine.screenTouchX - cx;
                    float deltaY = engine.screenTouchY - cy;
                    float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                    final float MAX_DISTANCE = 0.6f;

                    double angle = MathUtils.getAngle(cx, cy, engine.screenTouchX, engine.screenTouchY);
                    engine.joystick.setAngle((float) angle);

                    if (distance <= MAX_DISTANCE) {
                        engine.joystick.setTouched(true);
                        engine.joystick.setActuatorX(deltaX);
                        engine.joystick.setActuatorY(deltaY);
                    } else {
                        //out of bounds
                        engine.joystick.setActuatorX(deltaX / distance * MAX_DISTANCE);
                        engine.joystick.setActuatorY(deltaY / distance * MAX_DISTANCE);
                    }

                    triangle.setCenterX(engine.cameraCenterX);
                    triangle.setCenterY(engine.cameraCenterY);
                    //Log.e("CenterX= ", String.valueOf(triangle.getCenterX()));
                    //Log.e("CenterY= ", String.valueOf(triangle.getCenterY()));

                break;
                case MotionEvent.ACTION_UP:
                    engine.joystick.setTouched(false);
                    //joystick.setActuatorX(0);
                    //joystick.setActuatorY(0);
                    //Log.e("ActuatorX= ", String.valueOf(joystick.getActuatorX()));
                    //Log.e("ActuatorY= ", String.valueOf(joystick.getActuatorY()));
                break;
        }

    }

    public boolean isTouching() {
        engine.isTouched = true;
        return isTouching;
    }

    public void setTouching(boolean touching) {
        engine.isTouched = touching;
        isTouching = touching;
    }
}
