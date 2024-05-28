package com.carbonara.game.object.gameobjects.abstracts;


// import com.carbonara.game.object.gameobjects.categories.player.general.InteractionControl;
import com.carbonara.game.object.gameobjects.interfaces.IGameObject;
import com.carbonara.game.object.gameobjects.states.AbstractGameObjectState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class AbstractGameObject extends Node implements IGameObject {
    // protected InteractionControl interactionControl;
    protected Spatial gameObjectSpatial;
    /*protected Node space;*/
    public AbstractGameObject(/*Node space*/) { // пространство в которое добавляется объект
        // this.interactionControl = new InteractionControl();
        // this.space = space;
        // this.addControl(interactionControl);

        //space.attachChild(this);            // прикпрепляем gameObject (Node) к сцене

        // Инициализация общих свойств объекта
        initialize();
    }

    @Override
    public void setGameObjectSpatial(Spatial gameObjectSpatial) {
        if (this.gameObjectSpatial != null) this.attachChild(this.gameObjectSpatial);
        this.gameObjectSpatial = gameObjectSpatial;
        this.attachChild(gameObjectSpatial);
    }

    @Override
    public Spatial getGameObjectSpatial() {
        return this.gameObjectSpatial;
    }

}
