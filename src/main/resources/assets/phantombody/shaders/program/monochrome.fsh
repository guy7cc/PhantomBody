#version 150

uniform sampler2D DiffuseSampler;

uniform vec4 ColorModulate;

uniform float Lerp;
uniform float Strength;

in vec2 texCoord;

out vec4 fragColor;

const float redScale   = 0.298912;
const float greenScale = 0.586611;
const float blueScale  = 0.114478;
const vec3  monochromeScale = vec3(redScale, greenScale, blueScale);

void main(){
    vec4 color = texture(DiffuseSampler, texCoord) * ColorModulate;
    float grayColor = clamp(dot(color.rgb, monochromeScale) * Strength, 0.0, 1.0);
    fragColor = color * (1 - Lerp) + vec4(vec3(grayColor), 1.0) * Lerp;
}
