precision mediump float;

uniform sampler2D u_TexCoord;
varying vec2 v_TexCoord;

void main() {
    gl_FragColor = texture2D(u_TexCoord, v_TexCoord);
}
