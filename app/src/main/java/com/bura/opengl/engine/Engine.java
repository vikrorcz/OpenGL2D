package com.bura.opengl.engine;

import android.content.Context;

import com.bura.opengl.util.LoggerConfig;
import com.bura.opengl.R;
import com.bura.opengl.util.ShaderHelper;
import com.bura.opengl.util.TextResourceReader;

public class Engine {

    private final Context context;

    public int program;
    public int uColorLocation;
    public int aPositionLocation;

    public int uMatrixLocation;
    public final float[] vPMatrix = new float[16];
    public final float[] projectionMatrix = new float[16];
    public final float[] viewMatrix = new float[16];

    public Engine(Context context) {
       this.context = context;
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
    }
}
