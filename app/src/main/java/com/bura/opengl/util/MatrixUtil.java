package com.bura.opengl.util;

import android.opengl.Matrix;

import com.bura.opengl.engine.Engine;

public class MatrixUtil {

    private final Engine engine;

    public MatrixUtil(Engine engine)  {
        this.engine = engine;
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
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.rotationMatrix, 0);
    }

    public void restore() {
        Matrix.multiplyMM(engine.scratch, 0, engine.vPMatrix, 0, engine.viewMatrix, 0);
    }
}
