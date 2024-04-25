package com.carbonara.game.object.player.general;

import com.carbonara.game.object.interfaces.IActionCommand;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.Set;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class InteractionControl implements Control {
    Logger logger = Logger.getLogger(InteractionControl.class.getName());
    private Spatial interactionObject;
    private HashMap<String, IActionCommand> availableActions = new HashMap<>();

    private boolean CanInteract = true;

    public boolean isCanInteract() {
        return CanInteract;
    }

    public void setCanInteract(boolean canInteract) {
        CanInteract = canInteract;
    }

    // Метод для добавления действия с описанием
    public void addAction(String actionName, IActionCommand action) {
        availableActions.put(actionName, action);
    }

    public void deleteAction(String actionName) {
        availableActions.remove(actionName);
    }

    // Получение списка доступных действий
    public Set<String> getAvailableActionsDescriptions() {
        return availableActions.keySet();
    }

    // Вызов выбранного действия по названию
    public void interact(String actionName) {
        IActionCommand action = availableActions.get(actionName);
        if (action != null) {
            action.execute();
        } else logger.warning("Command " + actionName + " not found!");
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.interactionObject = spatial;
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

    public Spatial getInteractionObject() {
        return interactionObject;
    }
}

