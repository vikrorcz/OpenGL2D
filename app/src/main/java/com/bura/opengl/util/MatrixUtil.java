package com.bura.opengl.util;

import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.bura.opengl.engine.Engine;

public class MatrixUtil {

    private final Engine engine;

    public MatrixUtil(Engine engine)  {
        this.engine = engine;
    }

    public void cameraTranslate(float posX, float posY) {
        Matrix.translateM(engine.viewMatrix, 0, posX, posY, 0);
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
    }

    private float velocityX = 0;
    private float velocityY = 0;

    private float seekerVelocityX = 0;
    private float seekerVelocityY = 0;

    public void cameraTranslate(double directionAngle) {
        if (engine.joystick.isTouched()) {
            velocityX += (float) (Math.cos(directionAngle + Math.PI * 2)) * 0.02f;
            velocityY += (float) (Math.sin(directionAngle + Math.PI * 2)) * 0.02f;
        }

        Matrix.translateM(engine.viewMatrix, 0, velocityX, velocityY, 0);
        engine.cameraCenterX = -velocityX;
        engine.cameraCenterY = -velocityY;
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
    }

    public void withCameraTranslate() {
        Matrix.translateM(engine.viewMatrix, 0, engine.cameraCenterX, engine.cameraCenterY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
    }

    public void translate(float posX, float  posY) {
        //version 1
        //Matrix.setIdentityM(engine.translationMatrix,0);
        //Matrix.translateM(engine.translationMatrix, 0, posX, posY, 0);
        //Matrix.multiplyMM(engine.scratch, 0, engine.scratch,0, engine.translationMatrix, 0);

         //version 2
         Matrix.translateM(engine.scratch, 0, posX, posY, 0f);
    }

    private float velSeekerX = 0;
    private float velSeekerY = 0;

    public void translateByAngle(float angle) {
        velSeekerX -= (float) (Math.cos(angle + Math.PI * 2)) * 0.01f;
        velSeekerY -= (float) (Math.sin(angle + Math.PI * 2)) * 0.01f;

        Matrix.translateM(engine.scratch, 0, velSeekerX, velSeekerY, 0f);
    }

    public void rotate(float angle, float centerX, float centerY) {
        Matrix.setIdentityM(engine.rotationMatrix, 0);
        //Matrix.scaleM(engine.rotationMatrix, 0, 0.5f,0.5f,0);//Fix scaling fixme
        Matrix.translateM(engine.rotationMatrix, 0, centerX, centerY, 0);
        Matrix.rotateM(engine.rotationMatrix, 0, angle, 0, 0, 1f);
        Matrix.translateM(engine.rotationMatrix, 0, -centerX, -centerY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);
        //Matrix.scaleM(engine.rotationMatrix, 0, 1f,1f,0);//Fix scaling fixme
    }

    public void translateAndRotate(float angle, float centerX, float centerY, float posX, float posY) {
        Matrix.setIdentityM(engine.rotationMatrix, 0);
        Matrix.translateM(engine.rotationMatrix, 0, posX, posY, 0);
        Matrix.rotateM(engine.rotationMatrix, 0, angle, 0, 0, 1f);
        Matrix.translateM(engine.rotationMatrix, 0, -centerX, -centerY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);
    }

    public void restore() {
        //Stop any transformation for further objects to take effect
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);
    }

    public void cameraRestore() {
        //Matrix.translateM(engine.viewMatrix, 0, 0, 0, 0);
        //Matrix.multiplyMM(engine.scratch, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
    }
}
