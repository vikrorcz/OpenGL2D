package com.bura.opengl.object;

import com.bura.opengl.engine.Engine;

import java.nio.FloatBuffer;

public abstract class Shape {

    protected Engine engine;
    protected float centerX;
    protected float centerY;
    protected float width;
    protected float height;
    protected FloatBuffer vertexData;
    protected int vertexCount;
    protected float[] color = { 1.0f, 1.0f, 1.0f, 1.0f };

    public Shape(Engine engine, float centerX, float centerY) {
        this.engine = engine;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public boolean isOnScreen() {
        return true;
    }


    abstract void draw();

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public FloatBuffer getVertexData() {
        return vertexData;
    }

    public void setVertexData(FloatBuffer vertexData) {
        this.vertexData = vertexData;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
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

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
