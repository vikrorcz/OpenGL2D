package com.bura.opengl.object;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE1;
import static android.opengl.GLES20.GL_TEXTURE2;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.bura.opengl.R;
import com.bura.opengl.engine.Engine;
import com.bura.opengl.util.Constants;
import com.bura.opengl.util.TextureUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Texture extends Shape {
    private final FloatBuffer textureData;
    private final ShortBuffer drawListData;

    private final short[] drawOrder = {0, 1, 2, 0, 2, 3};

    private final int glTextureId;

    public Texture(Engine engine, float x, float y, float width, float height, int resourceId) {
        super(engine,x,y);
        this.engine = engine;

        float[] rectangleVertices = {
                x, y, 0.0f,// top left
                x, y - 0.4f * height, 0.0f,// bottom left
                x + 0.4f * width, y - 0.4f * height, 0.0f,// bottom right
                x + 0.4f *  width, y, 0.0f,// top right
        };

        vertexData = ByteBuffer
                .allocateDirect(rectangleVertices.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(rectangleVertices);
        vertexData.position(0);

        float[] textureVertices = {
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 0.0f
        };

        textureData = ByteBuffer
                .allocateDirect(textureVertices.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureData.put(textureVertices);
        textureData.position(0);

        short[] drawOrder = {0, 1, 2, 0, 2, 3};

        drawListData = ByteBuffer
                .allocateDirect(drawOrder.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();

        drawListData.put(drawOrder);
        drawListData.position(0);

        glTextureId = TextureUtil.getGLTextureFromResourceId(resourceId);
    }

    public void draw() {
        glUseProgram(engine.textureProgram);

        engine.uTextureLocation = GLES20.glGetUniformLocation(engine.textureProgram, Constants.U_TEXTURE);
        engine.aPositionLocation = GLES20.glGetAttribLocation(engine.textureProgram, Constants.A_POSITION);
        engine.aTextureLocation = GLES20.glGetAttribLocation(engine.textureProgram, Constants.A_TEXTURE);
        engine.uMatrixLocation = GLES20.glGetUniformLocation(engine.textureProgram, Constants.U_MATRIX);

        GLES20.glUniform1i(engine.uTextureLocation, glTextureId);

        GLES20.glUniformMatrix4fv(engine.uMatrixLocation, 1, false, engine.scratch, 0);

        GLES20.glVertexAttribPointer(engine.aPositionLocation, Constants.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, Constants.STRIDE, vertexData);

        GLES20.glVertexAttribPointer(engine.aTextureLocation, Constants.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, Constants.STRIDE, textureData);

        GLES20.glEnableVertexAttribArray(engine.aPositionLocation);
        GLES20.glEnableVertexAttribArray(engine.aTextureLocation);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListData);

        glDisableVertexAttribArray(engine.aPositionLocation);
        glDisableVertexAttribArray(engine.aTextureLocation);
    }
}
