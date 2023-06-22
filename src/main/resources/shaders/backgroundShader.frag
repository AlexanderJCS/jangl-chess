#version 460 core

uniform vec2 windowSize;
uniform vec2 boardDimensions;

out vec4 fragColor;

void main() {
    // Calculate the size of each square on the chessboard
    vec2 squareSize = windowSize / boardDimensions;

    // Calculate the grid position of the fragment
    ivec2 gridPosition = ivec2(gl_FragCoord.xy / squareSize);

    // Calculate the checkerboard pattern
    bool isWhite = (gridPosition.x + gridPosition.y) % 2 == 1;

    // Set the fragment color based on the checkerboard pattern
    fragColor = isWhite ? vec4(1, 0.76, 0.55, 1) : vec4(0.28, 0.07, 0.05, 1);
}