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
uniform vec2 u_screenSize; //width, height

void main()
{
    // Get Color
    vec2 uv = gl_FragCoord.xy / u_screenSize;
    vec4 texColor = texture(u_texture, uv);

    // Change Color, here Im changing alpha, you would want to change rbg by some amount to your liking
    texColor.r *= 1.35;
    texColor.g *= 1.15;
    texColor.b *= 0.05;
    texColor.a *= 1.0;

    // Set Color
    gl_FragColor = texColor;
}