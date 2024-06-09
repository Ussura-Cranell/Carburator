package com.carbonara.game.gui.spaceship.systems;

import com.carbonara.game.settings.GameSettings;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Panel;

import java.util.logging.Logger;

public abstract class AbstractSpaceshipSystemPage implements ISpaceshipSystemsPage {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected Container screen; // контейнер для GUI
    protected Node point;       // точка к которой прикпепляется контейнер
    protected float scale;
    protected float mul = 1000f;
    protected float sizeX;
    protected float sizeY;
    protected Vector2f sizePage;

    public AbstractSpaceshipSystemPage(Node point, float scale){
        this.point = point;
        this.scale = scale;

        initialize();
        // addScreenToNode();
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
    private boolean isGUI = false;
    public Container getScreenForGUI(){
        return getScreenForGUI(Vector3f.ZERO);
    }

    public Container getScreenForGUI(Vector3f position){
        if (!isGUI) {
            isGUI = true;
            // добавляет интерфейс на экран игрока

            float width2Window = (float) GameSettings.getAppSettings().getWindowWidth() / 2;
            float height2Window = (float) GameSettings.getAppSettings().getWindowHeight() / 2;

            float width2Page = sizeX / 2;
            float height2Page = sizeY / 2;

            this.screen.setLocalTranslation(new Vector3f(
                    width2Window - width2Page,
                    height2Window + height2Page,1f).add(position));
            return this.screen;
        }
        logger.warning("Already taken for the GUI");
        return null;
    }

    public void pullScreenBack(){
        // предпологается что экран сразу будет расположен на сцене, поэтому после
        // getScreenForGUI он пропадёт со сцены и его нужно будет прикрепить обратно
        if (isGUI) {
            isGUI = false;
            // добавляет интерфейс на экран игрока
            this.screen.setLocalTranslation(
                    0f,
                    0f,
                    1f
            );
            point.attachChild(screen);
        } else logger.warning("The screen is already in position");
    }

    @Override
    public void cleanup() {
        pullScreenBack();
        point.detachChild(this.screen);
    }

    @Override
    public void update() {

    }
}
