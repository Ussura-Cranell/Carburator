package com.carbonara.game.object.gameobjects.interfaces;

// import com.carbonara.game.object.general.InteractionControl;
// import com.carbonara.game.object.states.AbstractGameObjectState;
import com.jme3.scene.Spatial;

public interface IGameObject {

    void initialize(); // инициализация объекта
    // InteractionControl getInteractionControl();
    void setGameObjectSpatial(Spatial gameObjectSpatial);
    Spatial getGameObjectSpatial();
    // void addGameObjectState(AbstractGameObjectState gameObjectState);
}
