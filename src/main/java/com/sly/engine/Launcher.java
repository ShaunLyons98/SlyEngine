package com.sly.engine;

import com.sly.engine.core.EngineManager;
import com.sly.engine.core.WindowManager;
import com.sly.engine.utils.Strings;
import com.sly.test.Test;

public class Launcher {

    private static WindowManager windowManager;
    private static Test game;

    public static void main(String[] args) {
        windowManager = new WindowManager(Strings.TITLE, 1920, 1080, false);
        game = new Test();
        EngineManager engineManager = new EngineManager();

        try {
            engineManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindowManager() {
        return windowManager;
    }

    public static Test getGame() {
        return game;
    }
}
