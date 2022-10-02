package com.bura.opengl.engine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import com.bura.opengl.engine.Engine;
import com.bura.opengl.object.Joystick;
import com.bura.opengl.object.Rectangle;
import com.bura.opengl.object.Triangle;
import com.bura.opengl.util.MathUtils;


public class MyRenderer implements Renderer {

    private final Engine engine;

    public volatile float angle;
    public volatile boolean isTouching;
    public volatile float touchX;
    public volatile float touchY;

    private final Rectangle rectangle;
    private final Joystick joystick;
    private final Triangle triangle;

    public MyRenderer(Context context){
        engine = new Engine(context);

        rectangle = new Rectangle(engine, 0f,0f, 5);
        rectangle.setColor(new float[]{0.63671875f, 0.76953125f, 0.22265625f, 1.0f});

        triangle = new Triangle(engine, 0f,0f, 1);
        triangle.setColor(new float[]{0.83671875f, 0.26953125f, 0.22265625f, 1.0f});

        joystick = new Joystick(engine, 1);
    }


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        engine.createShaders();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        engine.screenWidth = width;
        engine.screenHeight = height;
        //System.out.println("ScreenWidth: " + width + "ScreenHeight: " + height);
        Matrix.frustumM(engine.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    private float posY = 0f;
    private boolean reverseDirection = false;

    @Override
    public void onDrawFrame(GL10 unused) {
        inputUpdate();

        glClear(GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(engine.viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        double cameraAngle = MathUtils.getAngle((float) engine.screenWidth / 2f, (float)engine.screenHeight / 2f, touchX, touchY);

        engine.matrixUtil.cameraTranslate(cameraAngle);
        engine.matrixUtil.rotate(angle, rectangle.getCenterX(), rectangle.getCenterY());
        rectangle.draw();
        engine.matrixUtil.restore();

        engine.matrixUtil.translate(0f, -posY);
        triangle.draw();
        engine.matrixUtil.restore();

        engine.matrixUtil.withCameraTranslate();
        joystick.draw();
        engine.matrixUtil.restore();

        if (!reverseDirection) {
            posY = posY - 0.01f;
            if (posY <= -1.8f) {
                reverseDirection = true;
            }
        } else {
            posY = posY + 0.01f;
            if (posY >= 1.8f) {
                reverseDirection = false;
            }
        }
    }

    private void inputUpdate() {
        if (engine.isTouched) {
            float screenTouchX = 0;
            float screenTouchY = 0;

            if (touchX < engine.screenWidth / 2f) {
                screenTouchX = touchX / engine.screenWidth - 3;
            }

            if (touchX > engine.screenWidth / 2f) {
                screenTouchX = touchX / engine.screenWidth + 2;
            }

            if (touchY < engine.screenHeight / 2f) {
                screenTouchY = touchY / engine.screenHeight - 1;
            }

            if (touchY > engine.screenHeight / 2f) {
                screenTouchY = -touchY / engine.screenHeight + 2;
            }

            System.out.println("screenTouchX= "  + screenTouchX + "screenTouchY= "  + screenTouchY);

            //float distance = (float) MathUtils.getLength(touchX, touchY, joystick.getOuterX(), joystick.getOuterY());
            float deltaX = screenTouchX - joystick.getOuterX();
            float deltaY = screenTouchY - joystick.getOuterY();
            float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            System.out.println("dist= " + distance);

            if (distance < 1) {
                joystick.setTouched(true);
                System.out.println("JOYSTICK TOUCHED");
            }

            if (distance < 1) {
                joystick.setActuatorX(deltaX / 0.3f);
                joystick.setActuatorY(deltaY / 0.3f);
            }
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
