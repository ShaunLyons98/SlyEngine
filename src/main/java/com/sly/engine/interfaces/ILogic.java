package com.sly.engine.interfaces;

public interface ILogic {

    void init() throws Exception;

    void input() throws Exception;

    void update();

    void render();

    void cleanup();

}
