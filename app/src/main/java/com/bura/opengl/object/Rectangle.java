package com.bura.opengl.object;

import android.graphics.Color;

import com.bura.opengl.util.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Rectangle {

    private FloatBuffer vertexData;
    private ShortBuffer drawListData;
    private final int vertexCount;

    float[] rectangleVertices = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f  // top right
    };

    private float[] color = { 1.0f, 1.0f, 1.0f, 1.0f };

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    public Rectangle() {

        vertexCount = rectangleVertices.length / Constants.COORDS_PER_VERTEX;
        vertexData = ByteBuffer
                .allocateDirect(rectangleVertices.length * Constants.BYTES_PER_FLOAT)//not managed by garbage collector
                .order(ByteOrder.nativeOrder())//organize reading of numbers
                .asFloatBuffer();//work with floats
        vertexData.put(rectangleVertices);//copy Dalvik memory to native memory
        vertexData.position(0);

        drawListData = ByteBuffer
                .allocateDirect(drawOrder.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();

        drawListData.put(drawOrder);
        drawListData.position(0);
    }

    public void draw() {

    }

    public void update() {

    }

}
