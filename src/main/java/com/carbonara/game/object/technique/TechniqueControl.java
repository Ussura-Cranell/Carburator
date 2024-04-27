package com.carbonara.game.object.technique;

import com.carbonara.game.logic.interaction.AbstractInteraction;
import com.carbonara.game.object.player.general.InteractionControl;
import com.carbonara.game.object.technique.commands.TurnOffCommand;
import com.carbonara.game.object.technique.commands.TurnOnCommand;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.logging.Logger;

public class TechniqueControl implements Control {
    // контроллер который можно зарегистрировать с помщью InteractionControl на сущности
    Logger logger = Logger.getLogger(TechniqueControl.class.getName());
    private boolean techniqueEnabled;
    Spatial spatial;
    InteractionControl interactionControl;

    AbstractInteraction interactingObject;

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        InteractionControl interactionControl = spatial.getControl(InteractionControl.class);
        // если у объекта есть интерфейс для взаимодействия
        if (interactionControl != null){
            this.spatial = spatial;
            this.interactionControl = interactionControl;
            // this.interactingObject = interactionControl.getInteractionObject();
        } else {
            // если у объекта нет интерфейса для взаимодействия, удалить контроллер
            spatial.removeControl(this);
            logger.warning("The object" + spatial.getName() + " does not have an+" + InteractionControl.class.getName() + "!");
        }
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

    public void turnOff(){
        logger.info("turnOff");
        techniqueEnabled = false;

        this.interactionControl.deleteAction("turnOff");
        this.interactionControl.addAction("turnOn", new TurnOnCommand(this));
    }

    public void turnOn(){
        logger.info("turnOn");
        techniqueEnabled = true;

        this.interactionControl.deleteAction("turnOn");
        this.interactionControl.addAction("turnOff", new TurnOffCommand(this));
    }

    public void getIntegration(){

    }
}
