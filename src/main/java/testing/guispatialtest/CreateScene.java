package testing.guispatialtest;

import com.carbonara.game.managers.CameraManager;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.ElementId;

public class CreateScene extends BaseAppState {

    private SimpleApplication app;
    private Node cubeScene = new Node("cubeScene");

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;

        CameraManager.cameraUnlock(true);
        app.getInputManager().setCursorVisible(false);

        createCube(cubeScene);
        app.getRootNode().attachChild(cubeScene);

        // создание кнопки прикрепленной к boxGeometry
        createButtonClickMe();

        app.getInputManager().addMapping("Interaction", new KeyTrigger(KeyInput.KEY_I));
        app.getInputManager().addListener(spaceListener, "Interaction");
    }

    @Override
    protected void cleanup(Application application) {
        app.getRootNode().detachChild(cubeScene);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
    private Geometry boxGeometry;
    private void createCube(Node attachNode){
        Box boxShape = new Box(1,1,1);
        boxGeometry = new Geometry("boxGeometry", boxShape);

        boxGeometry.setLocalTranslation(0, 0, 0);

        Material boxMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        boxGeometry.setMaterial(boxMaterial);

        attachNode.attachChild(boxGeometry);
    }
    private static boolean cameraUnlock = true;
    private static boolean cursorVisible = false;
    InputListener spaceListener = (ActionListener) (s, b, v) -> {

        if (s.equals("Interaction")&&!b){

            cameraUnlock = !cameraUnlock;
            cursorVisible = !cursorVisible;

            CameraManager.cameraUnlock(cameraUnlock);
            app.getInputManager().setCursorVisible(cursorVisible);
        }
    };

    private static int countClicked = 0;

    private void createButtonClickMe(){
        Container myWindow = new Container();
        myWindow.addChild(new Label("Button test#1", new ElementId("window.title")));
        cubeScene.attachChild(myWindow);
        // myWindow.setLocalTranslation(300, 300, 0);
        myWindow.scale(0.015f);
        // myWindow.setPreferredSize(new Vector3f(10, 10, 10));

        Button clickMeButton = myWindow.addChild(new Button("Click Me"));

        clickMeButton.addClickCommands(source -> {

            System.out.println("[%d] Button clicked!".formatted(++countClicked));
            GuiGlobals.getInstance().getFocusManagerState().setEnabled(false);
        });

        myWindow.setLocalTranslation(boxGeometry.getLocalTranslation().add(new Vector3f(-1.0f, 1.0f, 1.0f)));
    }


    @Override
    public void update(float tpf) {
        cubeScene.rotate(0, tpf, 0);
    }
}
