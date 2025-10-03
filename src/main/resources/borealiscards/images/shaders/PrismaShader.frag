#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_pos;

uniform sampler2D u_texture;
uniform vec2 u_resolution;

vec4 rgba(vec2 offset) {
    return v_color * texture2D(u_texture, v_texCoords + offset);
}

float NUMBER = 0.01;
float NOMBER = 0.001;
// idk just put numbers here and see what happens

void main()
{
    vec4 outputColor = rgba(vec2(0,0));
    vec2 colorOffset = vec2(NUMBER,NOMBER);

    outputColor.r = rgba(colorOffset).r;
    outputColor.g = rgba(colorOffset).g;
    outputColor.b = rgba(colorOffset).b;

    gl_FragColor = outputColor;
}