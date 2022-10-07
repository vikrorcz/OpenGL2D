precision mediump float;

attribute vec4 a_Position;
attribute vec2 a_TexCoord;
varying vec2 v_TexCoord;
uniform mat4 u_Matrix;

void main()
{
    gl_Position = u_Matrix * a_Position;
    v_TexCoord = a_TexCoord.xy;
    //v_TexCoord = vec2(a_TexCoord.x, (1.0 - (a_TexCoord.y)));
}
