package com.bura.opengl.object;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.Matrix.orthoM;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLES20;

import com.bura.opengl.engine.Engine;
import com.bura.opengl.util.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;

public class Triangle {
    private final Engine engine;

    private final FloatBuffer vertexData;
    private final int vertexCount;

    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Triangle(Engine engine) {
        this.engine = engine;

        float[] rectVertices = {
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };

        vertexCount = rectVertices.length / Constants.COORDS_PER_VERTEX;
        vertexData = ByteBuffer
                .allocateDirect(rectVertices.length * Constants.BYTES_PER_FLOAT)//not managed by garbage collector
                .order(ByteOrder.nativeOrder())//organize reading of numbers
                .asFloatBuffer();//work with floats
        vertexData.put(rectVertices);//copy Dalvik memory to native memory
        vertexData.position(0);
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
        glUniformMatrix4fv(engine.uMatrixLocation, 1, false, engine.vPMatrix, 0);

        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glDisableVertexAttribArray(engine.aPositionLocation);
    }
}
