package com.sly.engine.render;

import com.sly.engine.core.ObjectLoader;
import com.sly.engine.entity.Model;
import com.sly.engine.entity.Texture;

public class Block {

    private final ObjectLoader loader;
    private Model model;

    private static float[] positions = new float[] {
            // VO
            -0.5f,  0.5f,  0.5f,
            // V1
            -0.5f, -0.5f,  0.5f,
            // V2
            0.5f, -0.5f,  0.5f,
            // V3
            0.5f,  0.5f,  0.5f,
            // V4
            -0.5f,  0.5f, -0.5f,
            // V5
            0.5f,  0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
    };

    private static float[] colours = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,


    };

    private static int[] indices = new int[] {
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            4, 0, 3, 5, 4, 3,
            // Right face
            //3, 2, 7, 5, 3, 7,
            // Left face
            //6, 1, 0, 6, 0, 4,
            // Bottom face
            //2, 1, 6, 2, 6, 7,
            // Back face
            //7, 6, 4, 7, 4, 5,
    };

    public Block() {
        this.loader = new ObjectLoader();
    }

    public void loadBlock() throws Exception {
        model = loader.loadModel(Block.getPositions(), Block.getColours(), Block.getIndices());
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")));
    }

    public static float[] getPositions() {
        return positions;
    }

    public static float[] getColours() {
        return colours;
    }

    public static int[] getIndices() {
        return indices;
    }

    public Model getModel() {
        return model;
    }
}
