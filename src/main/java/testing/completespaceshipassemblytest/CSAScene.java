package testing.completespaceshipassemblytest;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class CSAScene {
    private static Node scene = null;
    private static Spatial spacehipSpatial = null;
    public static Spatial getSpaceshipSpatial(){
        return spacehipSpatial;
    }
    public static Node createScene(){

        Node scene = new Node("GUISpaceshipScene");
        // начало создания сцены

        spacehipSpatial = createTestingCube();

        // конец создания сцены
        CSAScene.scene = scene;
        return scene;
    }

    public static Node getScene(){
        return scene;
    }

    public static Spatial createTestingCube(){
        Box testingBoxShape = new Box(1, 1, 1);
        Geometry testingBoxGeometry = new Geometry("testingBox2Geometry", testingBoxShape);
        testingBoxGeometry.move(4, 10, 25);
        Material testingBoxMaterial = new Material(GlobalSimpleApplication.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);
        return testingBoxGeometry;
    }
}
