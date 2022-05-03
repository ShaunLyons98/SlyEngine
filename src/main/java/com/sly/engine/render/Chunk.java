package com.sly.engine.render;

import com.sly.engine.core.ObjectLoader;
import com.sly.engine.entity.Entity;
import com.sly.engine.entity.Model;
import com.sly.engine.entity.Texture;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    private List<Entity> entities;


    public Chunk() {

    }

    public List generate() throws Exception {

        Block block = new Block();
        entities = new ArrayList<>();
        block.loadBlock();

        //for(int y = -64; y < 0; y++) {
        //    for(int x = 0; x < 15; x++) {
        //        for(int z = -32; z < -16; z++) {
                    entities.add(new Entity(block.getModel(), new Vector3f(0, 0, -4), new Vector3f(0, 0, 0), 1));
        //        }
       //    }
        //}

        return entities;
    }

}
