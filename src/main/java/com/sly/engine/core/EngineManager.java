package com.sly.engine.core;

import com.sly.engine.Launcher;
import com.sly.engine.interfaces.ILogic;
import com.sly.engine.utils.Strings;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class EngineManager {

    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private WindowManager window;
    private GLFWErrorCallback errorCallback;
    private ILogic logic;

    private void init() throws Exception {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Launcher.getWindowManager();
        logic = Launcher.getGame();
        window.init();
        logic.init();
    }

    public void start() throws Exception {
        init();

        if(isRunning)
            return;
        run();
    }

    public void run() throws Exception {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while(isRunning){
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while(unprocessedTime > frameTime){
                render = true;
                unprocessedTime -= frameTime;

                if(window.windowShouldClose())
                    stop();

                if(frameCounter >= NANOSECOND){
                    setFps(frames);
                    window.setTitle(Strings.TITLE + " [FPS : "+ getFps() + "]");
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render) {
                update();
                render();
                frames++;
            }
        }
        cleanup();
    }

    private void stop() {
        if(!isRunning)
            return;
        isRunning = false;
    }

    private void input() throws Exception {
        logic.input();
    }

    private void render() {
        logic.render();
        window.update();
    }

    private void update() {
        logic.update();
    }

    private void cleanup() {
        window.cleanup();
        logic.cleanup();
        errorCallback.free();
        glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
