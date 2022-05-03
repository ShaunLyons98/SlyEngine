package com.sly.engine.render;

import com.sly.engine.Launcher;
import com.sly.engine.core.ShaderManager;
import com.sly.engine.core.WindowManager;
import com.sly.engine.entity.Entity;
import com.sly.engine.entity.Model;
import com.sly.engine.utils.Debug;
import com.sly.engine.utils.Transform;
import com.sly.engine.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    private final WindowManager window;
    private ShaderManager shaderManager;

    private Map<Model, List<Entity>> entities = new HashMap<>();

    public RenderManager() {
        window = Launcher.getWindowManager();
    }

    public void init() throws Exception {
        window.updateProjectionMatrix();
        shaderManager = new ShaderManager();
        shaderManager.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderManager.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderManager.link();
        shaderManager.createUniform("textureSampler");
        shaderManager.createUniform("transformMatrix");
        shaderManager.createUniform("projectionMatrix");
        shaderManager.createUniform("viewMatrix");
    }

    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
    }

    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void prepare(Entity entity, Camera camera) {
        shaderManager.setUniform("textureSampler", 0);
        shaderManager.setUniform("transformMatrix", Transform.createTransformMatrix(entity));
        shaderManager.setUniform("viewMatrix", Transform.getViewMatrix(camera));
    }

    public void renderLights(Camera camera /* ADD LIGHTS */) {
        /* Light Stuff */
    }

    public void render(Camera camera) {
        clear();
        shaderManager.bind();
        shaderManager.setUniform("projectionMatrix", window.getProjectionMatrix());

        if(Debug.getInstance().isWireframe()) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);else GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        //renderLights(camera); //TO DO

        for(Model model : entities.keySet()) {
            bind(model);
            List<Entity> entityList = entities.get(model);
            for(Entity entity : entityList) {
                prepare(entity, camera);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbind();
        }
        entities.clear();
        shaderManager.unbind();
    }

    public void processEntity(Entity entity) {
        List<Entity> entityList = entities.get(entity.getModel());
        if(entityList != null)
            entityList.add(entity);
        else {
            List<Entity> newEntityList = new ArrayList<>();
            newEntityList.add(entity);
            entities.put(entity.getModel(), newEntityList);
        }
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        shaderManager.cleanup();
    }
}
