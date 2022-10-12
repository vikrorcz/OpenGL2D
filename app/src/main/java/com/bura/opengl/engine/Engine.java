package com.bura.opengl.engine;

import android.content.Context;

import com.bura.opengl.object.Joystick;
import com.bura.opengl.object.Texture;
import com.bura.opengl.util.FPSCounter;
import com.bura.opengl.util.LoggerConfig;
import com.bura.opengl.R;
import com.bura.opengl.util.MatrixUtil;
import com.bura.opengl.util.ShaderHelper;
import com.bura.opengl.util.TextResourceReader;
import com.bura.opengl.util.TextureUtil;

public class Engine {

    public final Context context;
    public MatrixUtil matrixUtil;

    public int program = 0;//shapeProgram
    public int textureProgram = 1;
    public int uColorLocation;
    public int aPositionLocation;
    public int aTextureLocation;
    public int uTextureLocation;

    public int mTextureDataHandle;

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

    public Joystick joystick;
    public final FPSCounter fpsCounter;
    public Texture texture;
    public TextureUtil textureUtil;

    public Engine(Context context) {
       this.context = context;

        fpsCounter = new FPSCounter();
        textureUtil = new TextureUtil(this);

    }

    public void createShaders() {
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);

        String textureVertexSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.texture_vertex_shader);
        String textureFragmentSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.texture_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        int textureFragmentShader = ShaderHelper.compileFragmentShader(textureFragmentSource);
        int textureVertexShader = ShaderHelper.compileVertexShader(textureVertexSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        textureProgram = ShaderHelper.linkProgram(textureVertexShader, textureFragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
            ShaderHelper.validateProgram(textureProgram);
        }


    }

    public void createObjects() {
        matrixUtil = new MatrixUtil(this);
        textureUtil.createTextures();

        texture = new Texture(this,-0.2f, 0.2f, 1f, 1f, R.drawable.player);
        joystick = new Joystick(this, 1);
    }
}
