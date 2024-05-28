package com.carbonara.game.object.gameobjects.states;

import com.carbonara.game.object.gameobjects.abstracts.AbstractGameObject;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;

public abstract class AbstractGameObjectState implements IGameObjectState, Control {
    // контроллер вешается на игровой объект
    protected AbstractGameObject gameObject;
    @Override
    public void setSpatial(Spatial spatial) {
        // получаем AbstractGameObject на который повесили контроллер

        if (spatial instanceof AbstractGameObject) this.gameObject = (AbstractGameObject) spatial;
        else throw new RuntimeException("States are for game objects only!");

        // инициализируем объект
        initialize();
    }



    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void update(float v) {

    }

    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {

    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }
}
