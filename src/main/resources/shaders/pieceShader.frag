#version 460

uniform sampler2D texSampler;
uniform bool isWhite;
in vec2 tex_coords;
out vec4 fragColor;

void main() {
    vec4 color = texture(texSampler, tex_coords);

    if (!isWhite) {
        color.xyz = 1 - color.xyz;
    }

    fragColor = color;
}