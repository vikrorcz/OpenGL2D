#version 100
precision mediump float;


void main() {
    vec2 uv = vec2(0,0);
    vec2 center = vec2(0,0) * 0.5;
    float radius = 0.30 * 1;
    float d = length(center - uv) - radius;
    float t = clamp(d, 0.4, 1.0);
    gl_FragColor = vec4(t, center, (120,230,0));
    //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
