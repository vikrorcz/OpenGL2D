package com.bura.opengl.object;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import com.bura.opengl.engine.Engine;
import com.bura.opengl.util.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class Rectangle extends Shape {

    public Rectangle(Engine engine, float centerX, float centerY, float size) {
        super(engine, centerX, centerY);

        //square
        float[] rectangleVertices = {
                centerX - 0.2f * size, centerY + 0.2f * size, 0.0f,  // top left
                centerX - 0.2f * size, centerY - 0.2f * size, 0.0f,  // bottom left
                centerX + 0.2f * size, centerY - 0.2f * size, 0.0f,  // bottom right
                centerX + 0.2f * size, centerY + 0.2f * size, 0.0f   // top right
        };

        vertexCount = rectangleVertices.length / Constants.COORDS_PER_VERTEX;
        vertexData = ByteBuffer
                .allocateDirect(rectangleVertices.length * Constants.BYTES_PER_FLOAT)//not managed by garbage collector
                .order(ByteOrder.nativeOrder())//organize reading of numbers
                .asFloatBuffer();//work with floats
        vertexData.put(rectangleVertices);//copy Dalvik memory to native memory
        vertexData.position(0);

        short[] drawOrder = {0, 1, 2, 0, 2, 3};

        ShortBuffer drawListData = ByteBuffer
                .allocateDirect(drawOrder.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();

        drawListData.put(drawOrder);
        drawListData.position(0);
    }

    public void draw() {
        glUseProgram(engine.program);

        engine.aPositionLocation = glGetAttribLocation(engine.program, Constants.A_POSITION);
        glEnableVertexAttribArray(engine.aPositionLocation);
        glVertexAttribPointer(engine.aPositionLocation, Constants.COORDS_PER_VERTEX, GL_FLOAT,
                false, Constants.STRIDE, vertexData);

        engine.uColorLocation = glGetUniformLocation(engine.program, Constants.U_COLOR);
        glUniform4fv(engine.uColorLocation, 1, color, 0);

        engine.uMatrixLocation = glGetUniformLocation(engine.program, Constants.U_MATRIX);
        glUniformMatrix4fv(engine.uMatrixLocation, 1, false, engine.scratch, 0);

        glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

        glDisableVertexAttribArray(engine.aPositionLocation);
    }
}
