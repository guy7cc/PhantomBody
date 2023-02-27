#version 150

uniform sampler2D DiffuseSampler;

uniform vec4 ColorModulate;

uniform float Threshold;
uniform vec4 BloomColor;

in vec2 texCoord;

out vec4 fragColor;

void main(){
    vec4 color = texture(DiffuseSampler, texCoord) * ColorModulate;
    float mean = (color.r + color.g + color.b) * 0.333;
    fragColor = color * step(Threshold, mean) * BloomColor;
}