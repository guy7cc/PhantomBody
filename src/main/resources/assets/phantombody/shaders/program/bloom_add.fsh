#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D BloomSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 mainColor = texture(DiffuseSampler, texCoord);
    vec4 bloomColor = texture(BloomSampler, texCoord);
    fragColor = vec4(mainColor.rgb + bloomColor.rgb, 1.0);
}
