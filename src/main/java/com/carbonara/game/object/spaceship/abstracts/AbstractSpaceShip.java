package com.carbonara.game.object.spaceship.abstracts;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.MainControlSystem;
import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.logging.Logger;

public class AbstractSpaceShip implements AppState {
    // двух подприложений AbstractSpaceShip не может быть в игре!
    protected static final Logger logger = Logger.getLogger(AbstractSpaceShip.class.getName());
    protected Spatial SpaceShipSpatial;       // модель корабля
    protected Node spaceNode;                 // пространство в котором расположен корабль
    protected AppStateManager appStateManager;        // менеджер подприложений
    protected SimpleApplication app;          // ссылка на приложение

    protected final MainControlSystem mainControlSystem = new MainControlSystem();

    // protected HashMap <String, AbstractSystem> spaceShipSystems = new HashMap<>();
    protected boolean isInitialized = false;
    protected boolean isEnabled = false;
    protected final String Id = String.valueOf(AbstractSpaceShip.class.hashCode());

    private static AbstractSpaceShip abstractSpaceShip;

    public AbstractSpaceShip(Spatial SpaceShipSpatial, Node spaceNode){

        if (abstractSpaceShip!=null) throw new RuntimeException("You can't create a second player ship!");

        this.SpaceShipSpatial = SpaceShipSpatial;
        this.spaceNode = spaceNode;

        // при инициализации автоматически добавляется главная система управления
        this.SpaceShipSpatial.addControl(mainControlSystem);

        // прикрепляем модель к сцене
        spaceNode.attachChild(SpaceShipSpatial);

        // нужно для того, чтобы нельзя было создать второй класс AbstractSpaceShip!
        abstractSpaceShip = this;
    }

    @Override
    public void initialize(AppStateManager appStateManager, Application application) {
        // инициализация подприложения

        this.app = (SimpleApplication) application;
        this.appStateManager = appStateManager;

        this.isInitialized = true; // инициализация завершена
    }

    @Override
    public void update(float v) {
        // игровой цикл
    }

    protected void onEnable() {
        // действия при включении
    }

    protected void onDisable() {
        // действия при выключении
    }

    @Override
    public void cleanup() {
        // очистка ресурсов при выходе
    }

    @Override
    public void stateAttached(AppStateManager appStateManager) {
        // присоединение к приложению
    }

    @Override
    public void stateDetached(AppStateManager appStateManager) {
        // отсоединение от приложения
    }

    @Override
    public String getId() {
        return this.Id;
    }
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
    @Override
    public boolean isInitialized() {
        return isInitialized;
    }
    @Override
    public void render(RenderManager renderManager) {

    }
    @Override
    public void postRender() {

    }
    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
        if (b) onEnable();
        else onDisable();
    }
    public MainControlSystem getMainControlSystem(){
        return this.mainControlSystem;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tSystems:\n");

        int index = 0;
        for (String systemName : this.mainControlSystem.getSystems().keySet())
            s.append("[%d] ".formatted(index++)).append(systemName).append('\n');
        s.append("\tComponents:\n");

        index = 0;
        for (AbstractSystem system : this.mainControlSystem.getSystems().values())
            for (AbstractSystemComponent systemComponent : system.getSystemComponents())
                s.append("[%d] ".formatted(index++)).append(systemComponent.getName()).append('\n');
        return String.valueOf(s);
    }
}
