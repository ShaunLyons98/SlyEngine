package com.sly.test;

import com.sly.engine.Launcher;
import com.sly.engine.core.ObjectLoader;
import com.sly.engine.core.WindowManager;
import com.sly.engine.entity.Entity;
import com.sly.engine.entity.Model;
import com.sly.engine.entity.Texture;
import com.sly.engine.input.KeyboardManager;
import com.sly.engine.input.MouseManager;
import com.sly.engine.interfaces.ILogic;
import com.sly.engine.render.*;
import com.sly.engine.utils.Debug;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.windows.MOUSEINPUT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Test implements ILogic {

    private static final float cameraSpeed = 0.05f;

    private final RenderManager renderManager;
    private final ObjectLoader loader;
    private final WindowManager windowManager;
    private final WorldGenerator worldGenerator;

    private List<Entity> entities;
    private Camera camera;

    Vector3f cameraInc;

    public Test() {
        renderManager = new RenderManager();
        windowManager = Launcher.getWindowManager();
        worldGenerator = new WorldGenerator();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0,0);
    }

    @Override
    public void init() throws Exception {
        renderManager.init();
        windowManager.updateProjectionMatrix();
        worldGenerator.init();

        Chunk chunk = new Chunk();
        entities = chunk.generate();

    }

    @Override
    public void input() throws Exception {
        cameraInc.set(0,0,0);

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_Q))
            cameraInc.y = -1;

        if(windowManager.isKeyPressed(GLFW.GLFW_KEY_E))
            cameraInc.y = 1;

        if(KeyboardManager.isKeyPressed(GLFW.GLFW_KEY_F3))
            Debug.getInstance().setWireframe(!Debug.getInstance().isWireframe());

    }

    @Override
    public void update() {
        camera.movePosition(cameraInc.x * cameraSpeed, cameraInc.y * cameraSpeed, cameraInc.z * cameraSpeed);

        if(MouseManager.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
            System.out.println(MouseManager.getDx() + " | " + MouseManager.getDy());
            camera.moveRotation(MouseManager.getDy() * -0.25f, MouseManager.getDx() * -0.25f, 0);
        }

        for(Entity entity : entities) {
            renderManager.processEntity(entity);
        }


        //entity.incRotation(0.0f, 0.5f, 0.0f);
    }

    @Override
    public void render() {
        if(windowManager.isResize()) {
            GL11.glViewport(0, 0, windowManager.getWidth(), windowManager.getHeight());
            windowManager.setResize(true);
        }

        windowManager.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderManager.render(camera);

    }

    @Override
    public void cleanup() {
        renderManager.cleanup();
        loader.cleanup();
    }
}
