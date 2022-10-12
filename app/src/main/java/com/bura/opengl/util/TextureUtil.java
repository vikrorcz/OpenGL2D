package com.bura.opengl.util;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE1;
import static android.opengl.GLES20.GL_TEXTURE2;
import static android.opengl.GLES20.glUseProgram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.bura.opengl.R;
import com.bura.opengl.engine.Engine;

public class TextureUtil {
    private final Engine engine;

    public final int[] textureHandle = new int[3];

    public TextureUtil(Engine engine) {
        this.engine = engine;
    }

    public static final int playerTextureLocation = R.drawable.player;
    public static final int joystickOuterTextureLocation = R.drawable.joystick_outer;
    public static final int joystickInnerTextureLocation = R.drawable.joystick_inner;

    public void createTextures() {

        textureHandle[0] = loadTexture(engine.context, R.drawable.player);
        textureHandle[1] = loadTexture(engine.context, R.drawable.joystick_outer);
        textureHandle[2] = loadTexture(engine.context, R.drawable.joystick_inner);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[1]);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[2]);
    }

   private int loadTexture(final Context context, final int resourceId) {
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        }

        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }

    public static int getGLTextureFromResourceId(int resourceId) {
        switch (resourceId) {
            case TextureUtil.playerTextureLocation:
                return 0;
            case TextureUtil.joystickInnerTextureLocation:
                return 1;
            case TextureUtil.joystickOuterTextureLocation:
                return 2;
        }
        return -1;
    }
}
