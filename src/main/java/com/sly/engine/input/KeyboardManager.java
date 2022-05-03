package com.sly.engine.input;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardManager {

    private static KeyboardManager INSTANCE;

    private boolean keyPressed[] = new boolean[GLFW_KEY_LAST+1];
    private boolean keysDown[] = new boolean[GLFW_KEY_LAST+1];
    private boolean keysUp[] = new boolean[GLFW_KEY_LAST+1];

    private KeyboardManager() {

    }

    public static KeyboardManager getInstance(){
        if(KeyboardManager.INSTANCE == null)
            KeyboardManager.INSTANCE = new KeyboardManager();

        return KeyboardManager.INSTANCE;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS) {
            getInstance().keyPressed[key] = true;
        } else if(action == GLFW_RELEASE) {
            getInstance().keyPressed[key] = false;
            getInstance().keysDown[key] = false;
            getInstance().keysUp[key] = true;
        }
    }

    public static boolean isKeyDown(int keyCode) {
        return getInstance().keyPressed[keyCode];
    }

    public static boolean isKeyPressed(int keyCode) {
        if(getInstance().keyPressed[keyCode] == true && getInstance().keysDown[keyCode] == false) {
            getInstance().keysDown[keyCode] = true;
            return true;
        }
        return false;
    }

    public static boolean isKeyReleased(int keyCode) {
        if(getInstance().keyPressed[keyCode] == false && getInstance().keysUp[keyCode] == true) {
            getInstance().keysUp[keyCode] = false;
            return true;
        }
        return false;
    }
}
