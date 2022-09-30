package com.bura.opengl.engine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
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
import com.bura.opengl.object.Triangle;


public class MyRenderer implements Renderer {

    private final Engine engine;

    private final Triangle triangle;
    private final Triangle triangle2;
    private final Triangle triangle3;

    public MyRenderer(Context context){
        engine = new Engine(context);

        triangle = new Triangle(engine, 0f,0f, 2);
        triangle.setColor(new float[]{0.63671875f, 0.76953125f, 0.22265625f, 1.0f});

        triangle2 = new Triangle(engine, 3f,0f, 1);
        triangle2.setColor(new float[]{0.13671875f, 0.46953125f, 0.22265625f, 1.0f});

        triangle3 = new Triangle(engine, -3f,0f, 1);
        triangle3.setColor(new float[]{0.63671875f, 0.36953125f, 0.82265625f, 1.0f});
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
        //Matrix.frustumM(engine.projectionMatrix, 0, -1f, 1f, -1, 1, 3, 7);
        Matrix.frustumM(engine.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    private float posY = 0f;
    private boolean reverseDirection = false;

    @Override
    public void onDrawFrame(GL10 unused) {
        glClear(GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(engine.viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

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


        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);
        Matrix.setRotateM(engine.rotationMatrix, 0, angle, 0, 0, -1.0f);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);
        triangle.draw();
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);

        Matrix.translateM(engine.scratch, 0, 0f, posY, 0f);
        triangle2.draw();
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);

        Matrix.translateM(engine.scratch, 0, 0f, -posY, 0f);
        triangle3.draw();
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);
    }



    public volatile float angle;
    public volatile boolean isTouching;
    public volatile float x;
    public volatile float y;
}
