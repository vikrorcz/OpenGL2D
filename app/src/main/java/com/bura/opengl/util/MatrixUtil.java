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

    public void cameraTranslate(double directionAngle) {
        if (engine.isTouched) {
            velocityX += (float) (Math.cos(directionAngle + Math.PI * 2)) * 0.02f;
            velocityY += (float) (Math.sin(directionAngle + Math.PI)) * 0.02f;
        }

        Matrix.translateM(engine.viewMatrix, 0, velocityX, velocityY, 0);
        engine.cameraCenterX = -velocityX;
        engine.cameraCenterY = -velocityY;
        Matrix.multiplyMM(engine.vPMatrix, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
        //System.out.println("Camera centerX= " + cameraCenterX + "Camera centerY= " + cameraCenterY);
    }

    public void withCameraTranslate() {
        Matrix.translateM(engine.viewMatrix, 0, engine.cameraCenterX, engine.cameraCenterY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.projectionMatrix, 0, engine.viewMatrix, 0);
        Matrix.scaleM(engine.scratch, 0, 0.5f,0.5f,0);//Fix scaling
    }

    public void translate(float posX, float  posY) {
        //version 1
        //Matrix.setIdentityM(engine.translationMatrix,0);
        //Matrix.translateM(engine.translationMatrix, 0, posX, posY, 0);
        //Matrix.multiplyMM(engine.scratch, 0, engine.scratch,0, engine.translationMatrix, 0);

         //version 2
         Matrix.translateM(engine.scratch, 0, posX, posY, 0f);
    }

    public void rotate(float angle, float centerX, float centerY) {
        Matrix.setIdentityM(engine.rotationMatrix, 0);
        Matrix.scaleM(engine.rotationMatrix, 0, 0.5f,0.5f,0);//Fix scaling
        Matrix.translateM(engine.rotationMatrix, 0, centerX, centerY, 0);
        Matrix.rotateM(engine.rotationMatrix, 0, angle, 0, 0, 1f);
        Matrix.translateM(engine.rotationMatrix, 0, -centerX, -centerY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);
    }

    public void translateAndRotate(float angle, float centerX, float centerY, float posX, float posY) {
        Matrix.setIdentityM(engine.rotationMatrix, 0);
        Matrix.scaleM(engine.rotationMatrix, 0, 0.5f,0.5f,0);//Fix scaling
        Matrix.translateM(engine.rotationMatrix, 0, posX, posY, 0);
        Matrix.translateM(engine.rotationMatrix, 0, centerX, centerY, 0);
        Matrix.rotateM(engine.rotationMatrix, 0, angle, 0, 0, 1f);
        Matrix.translateM(engine.rotationMatrix, 0, -centerX, -centerY, 0);
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);;
    }

    public void restore() {
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);
    }
}
