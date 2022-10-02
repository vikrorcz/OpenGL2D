package com.bura.opengl.object;

import com.bura.opengl.engine.Engine;
public class Joystick {
    private final Engine engine;
    private float innerX;
    private float innerY;
    private float outerX;
    private float outerY;
    private final int id;
    private float angle;
    private boolean isTouched = false;
    private float actuatorX;
    private float actuatorY;

    private Rectangle outerRect;
    private Rectangle innerRect;

    public Joystick(Engine engine, int id){
        this.engine = engine;
        this.id = id;

        float centerX = -2.75f;
        float centerY = -1f;
        if (id == 1) {
            outerRect = new Rectangle(engine, centerX, centerY, 2);
            outerRect.setColor(new float[]{0.63671875f, 0.76953125f, 0.82265625f, 1.0f});
            innerRect = new Rectangle(engine, centerX, centerY, 1);
            outerRect.setColor(new float[]{0.0875f, 0.06953125f, 0.82265625f, 1.0f});
        }

        innerX = centerX;
        innerY = centerY;
        outerX = centerX;
        outerY = centerY;
    }

    public void draw(){

        outerRect.draw();
        innerRect.draw();
    }

    public void update(){
        //if (id == 1){
        //    outerX = (int) engine.camera.position.x - Constants.WORLD_WIDTH / 3;
        //}else {
        //    outerX = (int) engine.camera.position.x + Constants.WORLD_WIDTH / 3;
        //}
        //outerY = (int) engine.camera.position.y - Constants.WORLD_HEIGHT / 3;

        if (!isTouched){
            innerX = outerX;
            innerY = outerY;
        }else {
            innerX = ((int) (outerX + actuatorX * 100));
            innerY = ((int) (outerY + actuatorY * 100));
        }
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

    public float getInnerX() {
        return innerX;
    }

    public void setInnerX(float innerX) {
        this.innerX = innerX;
    }

    public float getInnerY() {
        return innerY;
    }

    public void setInnerY(float innerY) {
        this.innerY = innerY;
    }

    public float getOuterX() {
        return outerX;
    }

    public void setOuterX(float outerX) {
        this.outerX = outerX;
    }

    public float getOuterY() {
        return outerY;
    }

    public void setOuterY(float outerY) {
        this.outerY = outerY;
    }
}
