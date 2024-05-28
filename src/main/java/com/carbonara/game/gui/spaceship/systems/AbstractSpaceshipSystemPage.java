package com.carbonara.game.gui.spaceship.systems;

import com.jme3.scene.Node;
import com.simsilica.lemur.Container;

public abstract class AbstractSpaceshipSystemPage implements ISpaceshipSystemsPage {
    protected Container screen; // контейнер для GUI
    protected Node point;       // точка к которой прикпепляется контейнер
    protected float scale;
    protected float mul = 1000f;
    protected float sizeX;
    protected float sizeY;

    public AbstractSpaceshipSystemPage(Node point, float scale){
        this.point = point;
        this.scale = scale;

        initialize();
    }

    @Override
    public Container getScreen() {
        return screen;
    }

    public void addScreenToNode(){
        // добавляет интерфейс на сцену
        point.attachChild(this.screen);
        // point.scale(scale * 0.0003f);
        point.scale(scale * 0.000285f);
    }
    public void addScreenToGUI(){
        // добавляет интерфейс на экран игрока
        this.screen.setLocalTranslation(
                0f,
                sizeY,
                1f
        );
        point.attachChild(this.screen);
    }

    @Override
    public void cleanup() {
        point.detachChild(this.screen);
    }
}
