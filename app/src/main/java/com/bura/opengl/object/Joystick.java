package com.bura.opengl.object;

import android.util.Log;

import com.bura.opengl.R;
import com.bura.opengl.engine.Engine;
import com.bura.opengl.util.TextureUtil;

public class Joystick {
    private final Engine engine;
    private final int id;
    private float angle;
    private boolean isTouched = false;
    private float actuatorX;
    private float actuatorY;

    private Texture outerTexture;
    private Texture innerTexture;

    private float centerX;
    private float centerY;

    private float innerWidth = 1f;
    private float innerHeight = 1f;
    private float outerWidth = 2f;
    private float outerHeight = 2f;

    public Joystick(Engine engine, int id){
        this.engine = engine;
        this.id = id;

        if (id == 1) {
            outerTexture = new Texture(engine, -0.2f,0.2f, outerWidth, outerHeight, TextureUtil.joystickOuterTextureLocation);
            innerTexture = new Texture(engine, 0,0, innerWidth, innerHeight, TextureUtil.joystickInnerTextureLocation);
        }

        centerX = -2.5f;
        centerY = -1f;

        actuatorX = centerX;
        actuatorY = centerY;

    }

    public void draw(){
        if (!isTouched) {
            actuatorX = 0;
            actuatorY = 0;
        }

        engine.matrixUtil.translate(centerX, centerY);
        outerTexture.draw();
        engine.matrixUtil.translate(actuatorX, actuatorY);
        innerTexture.draw();
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

    public float getInnerWidth() {
        return innerWidth;
    }

    public void setInnerWidth(float innerWidth) {
        this.innerWidth = innerWidth;
    }

    public float getInnerHeight() {
        return innerHeight;
    }

    public void setInnerHeight(float innerHeight) {
        this.innerHeight = innerHeight;
    }

    public float getOuterWidth() {
        return outerWidth;
    }

    public void setOuterWidth(float outerWidth) {
        this.outerWidth = outerWidth;
    }

    public float getOuterHeight() {
        return outerHeight;
    }

    public void setOuterHeight(float outerHeight) {
        this.outerHeight = outerHeight;
    }
}
