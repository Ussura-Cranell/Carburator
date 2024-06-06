package testing.completespaceshipassemblytest;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class CSAScene {
    private static Node scene = null;
    public static Node createScene(){

        Node scene = new Node("GUISpaceshipScene");
        // начало создания сцены

        // конец создания сцены
        CSAScene.scene = scene;
        return scene;
    }

    public static Node getScene(){
        return scene;
    }


}
