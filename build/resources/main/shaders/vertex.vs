#version 400 core

in vec3 position;
in vec2 textureCords;

out vec2 fragTextureCords;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    gl_Position = projectionMatrix * viewMatrix * transformMatrix * vec4(position, 1.0);
    fragTextureCords = textureCords;
}