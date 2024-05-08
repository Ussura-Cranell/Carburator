package com.carbonara.game.object.TESTINGACTION;

import com.carbonara.game.object.player.general.InteractionControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.logging.Logger;

public class UniversalObject implements Control {

    Logger logger = Logger.getLogger(UniversalObject.class.getName());

    Spatial spatial;
    InteractionControl interactionControl;

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

    private boolean someting1 = false;
    private boolean someting2 = false;
    private boolean someting3 = false;
    private boolean someting4 = false;
    private boolean someting5 = false;
    private boolean someting6 = false;

    public void changeSomething1(){
        someting1 = !someting1;
        logger.info("someting1 changed!");
    }
    public void changeSomething2(){
        someting2 = !someting2;
        logger.info("someting2 changed!");
    }
    public void changeSomething3(){
        someting3 = !someting3;
        logger.info("someting3 changed!");
    }
    public void changeSomething4(){
        someting4 = !someting4;
        logger.info("someting4 changed!");
    }
    public void changeSomething5(){
        someting5 = !someting5;
        logger.info("someting5 changed!");
    }
    public void changeSomething6(){
        someting6 = !someting6;
        logger.info("someting6 changed!");
    }

}
