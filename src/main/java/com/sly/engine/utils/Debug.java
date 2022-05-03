package com.sly.engine.utils;

import org.lwjgl.system.CallbackI;

public class Debug {

    private static Debug INSTANCE;

    private boolean wireframe = false;

    private Debug() {}

    public static Debug getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Debug();
        }

        return INSTANCE;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
