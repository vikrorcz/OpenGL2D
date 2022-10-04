package com.bura.opengl.object;

import android.util.Log;

import com.bura.opengl.engine.Engine;
public class Joystick {
    private final Engine engine;
    private final int id;
    private float angle;
    private boolean isTouched = false;
    private float actuatorX;
    private float actuatorY;

    private Rectangle outerRect;
    private Rectangle innerRect;

    private float centerX;
    private float centerY;

    public Joystick(Engine engine, int id){
        this.engine = engine;
        this.id = id;

        if (id == 1) {
            outerRect = new Rectangle(engine, centerX, centerY, 2);
            outerRect.setColor(new float[]{0.63671875f, 0.76953125f, 0.82265625f, 1.0f});
            innerRect = new Rectangle(engine, centerX, centerY, 1);
            outerRect.setColor(new float[]{0.0875f, 0.06953125f, 0.82265625f, 1.0f});
        }

        centerX = engine.cameraCenterX - 2.5f;
        centerY = engine.cameraCenterY - 1f;

        actuatorX = centerX;
        actuatorY = centerY;
    }

    public void draw(){
        if (!isTouched) {
            actuatorX = 0;
            actuatorY = 0;
        }

        engine.matrixUtil.translate(centerX, centerY);
        outerRect.draw();
        engine.matrixUtil.translate(actuatorX, actuatorY);
        innerRect.draw();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public float getActuatorX() {
        return actuatorX;
    }

    public void setActuatorX(float actuatorX) {
        this.actuatorX = actuatorX;
    }

    public float getActuatorY() {
        return actuatorY;
    }

    public void setActuatorY(float actuatorY) {
        this.actuatorY = actuatorY;
    }

    public Engine getEngine() {
        return engine;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}
