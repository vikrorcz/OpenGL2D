package com.bura.opengl.engine;

import android.content.Context;

import com.bura.opengl.object.Joystick;
import com.bura.opengl.util.LoggerConfig;
import com.bura.opengl.R;
import com.bura.opengl.util.MatrixUtil;
import com.bura.opengl.util.ShaderHelper;
import com.bura.opengl.util.TextResourceReader;

public class Engine {

    private final Context context;
    public MatrixUtil matrixUtil;

    public int program;
    public int uColorLocation;
    public int aPositionLocation;

    public int uMatrixLocation;
    public final float[] vPMatrix = new float[16];
    public final float[] projectionMatrix = new float[16];
    public final float[] viewMatrix = new float[16];

    public final float[] rotationMatrix = new float[16];
    public final float[] translationMatrix = new float[16];
    public final float[] scratch = new float[16];

    public int screenWidthPixel;
    public int screenHeightPixel;

    public float screenWidth;//GL COORDS
    public float screenHeight;//GL COORDS

    public float screenTouchX;//GL COORDS
    public float screenTouchY;//GL COORDS

    public float cameraCenterX;
    public float cameraCenterY;

    public boolean isTouched = false;

    public final Joystick joystick;

    public Engine(Context context) {
       this.context = context;

        joystick = new Joystick(this, 1);
    }

    public void createShaders() {
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        matrixUtil = new MatrixUtil(this);
    }
}
