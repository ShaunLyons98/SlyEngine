package com.sly.engine.input;

import static org.lwjgl.glfw.GLFW.*;

public class MouseManager {

    private static MouseManager INSTANCE;

    private double xScroll, yScroll;
    private double xPos, yPos, xLast, yLast;
    private boolean mouseButtonPressed[] = new boolean[GLFW_MOUSE_BUTTON_LAST+1];
    private boolean mouseButtonDown[] = new boolean[GLFW_MOUSE_BUTTON_LAST+1];
    private boolean mouseButtonUp[] = new boolean[GLFW_MOUSE_BUTTON_LAST+1];
    private boolean isDragging;

    private MouseManager() {
        this.xScroll = 0.0;
        this.yScroll = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.xLast = 0.0;
        this.yLast = 0.0;

    }

    public static MouseManager getInstance(){
        if(MouseManager.INSTANCE == null)
            MouseManager.INSTANCE = new MouseManager();

        return MouseManager.INSTANCE;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        getInstance().xLast = getInstance().xPos;
        getInstance().yLast = getInstance().yPos;
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
        getInstance().isDragging = getInstance().mouseButtonPressed[0] || getInstance().mouseButtonPressed[1] || getInstance().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if(action == GLFW_PRESS) {
            getInstance().mouseButtonPressed[button] = true;
        } else if(action == GLFW_RELEASE) {
            getInstance().mouseButtonPressed[button] = false;
            getInstance().mouseButtonDown[button] = false;
            getInstance().mouseButtonUp[button] = true;
            getInstance().isDragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance().xScroll = xOffset;
        getInstance().yScroll = yOffset;
    }

    public static void endFrame() {
        getInstance().xScroll = 0;
        getInstance().yScroll = 0;
        getInstance().xLast = getInstance().xPos;
        getInstance().yLast = getInstance().yPos;
    }

    public static float getX() {
        return (float)getInstance().xPos;
    }

    public static float getY() {
        return (float)getInstance().yPos;
    }

    public static float getDx() {
        float dx = (float)(getInstance().xLast - getInstance().xPos);
        if (dx == -1 || dx == 1)
            return 0;
        return dx;
    }

    public static float getDy() {
        float dy = (float)(getInstance().yLast - getInstance().yPos);
        if (dy == -1 || dy == 1)
            return 0;
        return dy;
    }

    public static float getScrollX() {
        return (float)getInstance().xScroll;
    }

    public static float getScrollY() {
        return (float)getInstance().yScroll;
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean isMouseButtonPressed(int button) {
        if(getInstance().mouseButtonPressed[button] == true && getInstance().mouseButtonDown[button] == false) {
            getInstance().mouseButtonDown[button] = true;
            return true;
        }
        return false;
    }

    public static boolean isMouseButtonDown(int button) {
        return getInstance().mouseButtonPressed[button];
    }

    public static boolean isMouseButtonReleased(int button) {
        if(getInstance().mouseButtonPressed[button] == false && getInstance().mouseButtonUp[button] == true) {
            getInstance().mouseButtonUp[button] = false;
            return true;
        }
        return false;
    }


}
