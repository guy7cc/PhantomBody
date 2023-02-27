#version 150

uniform sampler2D DiffuseSampler;

uniform vec4 ColorModulate;
uniform float DeltaTexel;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main(){
    vec2 delta = DeltaTexel * oneTexel;
    fragColor = (texture(DiffuseSampler, texCoord + delta) + texture(DiffuseSampler, texCoord - delta) + texture(DiffuseSampler, texCoord + vec2(-delta.x, delta.y)) + texture(DiffuseSampler, texCoord + vec2(delta.x, -delta.y))) * ColorModulate;
}