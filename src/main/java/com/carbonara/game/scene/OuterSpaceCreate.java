package com.carbonara.game.scene;

import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.PauseGameManager;
import com.carbonara.game.object.other.spaceship.SpaceShipControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

@Deprecated
public class OuterSpaceCreate {
    // модель перевернута, поэтому летает задом наперёд
    Node outerSpace;
    public OuterSpaceCreate(Node outerSpace){
        this.outerSpace = outerSpace;

        createPlayerSpaceship();
    }

    private void createPlayerSpaceship(){

        Node spaceShipNode = (Node) GameLauncher.getApp().getAssetManager().loadModel("Models/Testing/test1/spaceship_pack_3.j3o");
        Spatial spaceShipSpatial = spaceShipNode.getChild(0);
        spaceShipSpatial.setLocalScale(1);
        spaceShipSpatial.setLocalTranslation(25, 25, 25);

        Material material = new Material(GameLauncher.getApp().getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");

        spaceShipSpatial.setMaterial(material);

        this.outerSpace.attachChild(spaceShipSpatial);

        SpaceShipControl spaceShipControl = new SpaceShipControl(new Vector3f(25, -25, 100));
        spaceShipSpatial.addControl(spaceShipControl);
        PauseGameManager.addObserverPause(spaceShipControl);
        // PauseGameManager.
    }

    public Node getOuterSpace(){
        return this.outerSpace;
    }
}
