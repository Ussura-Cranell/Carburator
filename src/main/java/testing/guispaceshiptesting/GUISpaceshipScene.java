package testing.guispaceshiptesting;

import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.gameobjects.categories.player.general.InteractionControl;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.UniversalObject;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.commands.*;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.Random;

public class GUISpaceshipScene {
    private static Node scene = null;
        public static Node createScene(){

            Node scene = new Node("GUISpaceshipScene");

            // начало создания сцены

            // конец создания сцены

            GUISpaceshipScene.scene = scene;
            return scene;


        }

        public static Node getScene(){
            return scene;
        }

    public static Spatial createTestingCube(){
        Box testingBoxShape = new Box(1, 1, 1);
        Geometry testingBoxGeometry = new Geometry("testingBox2Geometry", testingBoxShape);
        testingBoxGeometry.move(4, 10, 25);
        Material testingBoxMaterial = new Material(GUISpaceshipTest.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);
        return testingBoxGeometry;
    }
}
