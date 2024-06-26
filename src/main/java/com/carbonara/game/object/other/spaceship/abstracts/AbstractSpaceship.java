package com.carbonara.game.object.other.spaceship.abstracts;

import com.carbonara.game.object.other.interfaces.ITakeDamage;
import com.carbonara.game.object.other.spaceship.components.weapon.AbstractWeapon;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;
import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.MainControlSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.logging.Logger;

public class AbstractSpaceship implements AppState, ITakeDamage {
    // двух подприложений AbstractSpaceShip не может быть в игре!
    protected static final Logger logger = Logger.getLogger(AbstractSpaceship.class.getName());
    protected Spatial SpaceShipSpatial;       // модель корабля
    protected Node spaceNode;                 // пространство в котором расположен корабль
    protected AppStateManager appStateManager;        // менеджер подприложений
    protected SimpleApplication app;          // ссылка на приложение

    protected final MainControlSystem mainControlSystem = new MainControlSystem();

    // protected HashMap <String, AbstractSystem> spaceShipSystems = new HashMap<>();
    protected boolean isInitialized = false;
    protected boolean isEnabled = false;
    protected final String Id = String.valueOf(AbstractSpaceship.class.hashCode());

    private static AbstractSpaceship abstractSpaceShip;

    public AbstractSpaceship(Spatial SpaceShipSpatial, Node spaceNode){

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
        abstractSpaceShip = null;
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
    public MainControlSystem getMainControlSystem() {
        return this.mainControlSystem;
    }

    public Spatial getSpaceShipSpatial() {
        return SpaceShipSpatial;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tSystems:\n");

        int index = 0;
        for (Class<? extends AbstractSystem> classSystem : this.mainControlSystem.getSystems().keySet())
            s.append("[%d] ".formatted(index++)).append(classSystem.getSimpleName()).append('\n');
        s.append("\tComponents:\n");

        index = 0;
        for (AbstractSystem system : this.mainControlSystem.getSystems().values())
            for (AbstractSystemComponent systemComponent : system.getSystemComponents())
                s.append("[%d] ".formatted(index++)).append(systemComponent.getClassSimpleName()).append('\n');
        return String.valueOf(s);
    }

    public static AbstractSpaceship getAbstractSpaceShip() {
        if (abstractSpaceShip == null) logger.warning("The class is not initialized!");
        return abstractSpaceShip;
    }

    private float ShipResources = 100;

    @Override
    public void takeDamage(AbstractWeapon weapon) {
        float damage = weapon.getDamage();
        /*String message = "";
        message += "weapon_id: " + weapon.hashCode() + " inflicted " + damage + " damage to the spaceship";

        System.out.println(message);*/
    }

    public void addShipResources(float shipResources) {
        ShipResources += shipResources;
    }
}
