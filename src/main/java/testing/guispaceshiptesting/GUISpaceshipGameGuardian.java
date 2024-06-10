package testing.guispaceshiptesting;

import com.carbonara.game.gui.spaceship.systems.flightcontrolsystempage.FlightControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage.ScanningControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.ScreenPageKeeper;
import com.carbonara.game.gui.spaceship.systems.terminalcontrolsystempage.TerminalControlSystemPage;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.object.other.enemy.abstracts.AbstractEnemy;
import com.carbonara.game.object.other.enemy.abstracts.Enemy;
import com.carbonara.game.object.other.spaceship.CreateTestSpaceship;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.systems.FlightControlSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class GUISpaceshipGameGuardian extends BaseAppState {

    private static final Logger logger = Logger.getLogger(com.carbonara.game.logic.NewSceneGuardian.class.getName());
    private final Node outerSpaceScene = GUISpaceshipScene.createScene();
    private final Node screenNodes = new Node("screenNodes");
    private SimpleApplication app;
    private AbstractSpaceship spaceship;
    private ScreenPageKeeper screenPageKeeper; // отвечает за терминалы

    @Override
    protected void initialize(Application application) {

        logger.info("initialize");

        GUIManager.setCursorVisible(false);
        CameraManager.cameraUnlock(true);

        app = (SimpleApplication) application;
        app.getRootNode().attachChild(outerSpaceScene);
        app.getRootNode().attachChild(screenNodes);

        boolean debug = true;
        GUIDebugManager.setEnable(debug);
        debugLabelCamera(debug);

        cameraInit();

        spaceshipInit();
    }

    private void cameraInit(){
        app.getCamera().setLocation(new Vector3f(-0.694f, 14.902f, 19.810f));
        app.getCamera().lookAtDirection(new Vector3f(0.565f, -0.528f, 0.634f), Vector3f.UNIT_Y);
    }

    private void spaceshipInit(){
        // создание корабля
        spaceship = new CreateTestSpaceship(
                GUISpaceshipScene.createTestingCube(),
                outerSpaceScene);
        // добавляем корабль как отдельное подприложение
        app.getStateManager().attach(spaceship);

        // добавляем систему полёта на корабль
        spaceship.getMainControlSystem().registerSystem(new FlightControlSystem());

        // добавляем интерфейсы для взаимодействия
        float offset = 1.0f;

        // места расположения экранов
        Node node1 = new Node("node1"); node1.move(0.0f, - offset * 1, 0.0f);
        Node node2 = new Node("node2"); node1.move(0.0f, - offset * 2, 0.0f);
        Node node3 = new Node("node3"); node1.move(0.0f, - offset * 3, 0.0f);
        Node node4 = new Node("node4"); node1.move(0.0f, - offset * 4, 0.0f);
        Node node5 = new Node("node5"); node1.move(0.0f, - offset * 5, 0.0f);
        Node node6 = new Node("node6"); node1.move(0.0f, - offset * 6, 0.0f);
        Node node7 = new Node("node7"); node1.move(0.0f, - offset * 7, 0.0f);
        Node node8 = new Node("node8"); node1.move(0.0f, - offset * 8, 0.0f);

        // прикрепляем экраны к сцене
        app.getRootNode().attachChild(node1);
        app.getRootNode().attachChild(node2);
        app.getRootNode().attachChild(node3);
        app.getRootNode().attachChild(node4);
        app.getRootNode().attachChild(node5);
        app.getRootNode().attachChild(node6);
        app.getRootNode().attachChild(node7);
        app.getRootNode().attachChild(node8);

        // создаём хранилище экранов и инициализируем их
        screenPageKeeper = new ScreenPageKeeper(1.0f,
                node1, node2, node3, node4, node5, node6, node7, node8);

        // достаём графический интерфейс для системы управления полётом
        flightControlSystemPage = screenPageKeeper.getSpaceshipSystemPage(FlightControlSystemPage.class);
        flightControlSystemPageContainer = flightControlSystemPage.getScreenForGUI();

        terminalControlSystemPage = screenPageKeeper.getSpaceshipSystemPage(TerminalControlSystemPage.class);
        terminalControlSystemPageContainer = terminalControlSystemPage.getScreenForGUI();

        scanningControlSystemPage = screenPageKeeper.getSpaceshipSystemPage(ScanningControlSystemPage.class);
        scanningControlSystemPageContainer = scanningControlSystemPage.getScreenForGUI();

        app.getInputManager().addMapping("screen_1", new KeyTrigger(KeyInput.KEY_1));
        app.getInputManager().addMapping("screen_2", new KeyTrigger(KeyInput.KEY_2));
        app.getInputManager().addMapping("screen_3", new KeyTrigger(KeyInput.KEY_3));
        app.getInputManager().addMapping("screen_4", new KeyTrigger(KeyInput.KEY_4));
        app.getInputManager().addMapping("screen_5", new KeyTrigger(KeyInput.KEY_5));
        app.getInputManager().addMapping("screen_6", new KeyTrigger(KeyInput.KEY_6));
        app.getInputManager().addMapping("screen_7", new KeyTrigger(KeyInput.KEY_7));
        app.getInputManager().addMapping("screen_8", new KeyTrigger(KeyInput.KEY_8));

        app.getInputManager().addListener(actionListener,
                "screen_1", "screen_2", "screen_3", "screen_4",
                "screen_5", "screen_6", "screen_7", "screen_8");

        // создаём тестовых врагов
        enemySet  = new HashSet<>();
        for (int i = 0; i <= 25; i++) {
            Enemy enemy = new Enemy();
            //enemy.getModel().setLocalTranslation(0, 0,0);
            // app.getRootNode().attachChild(enemy.get);
            enemySet.add(enemy);
        }
    }
    private Set<AbstractEnemy> enemySet;
    private Random random = new Random();
    private Spatial createSpatial(){
        Spatial spatial = new Geometry("box", new Box(1.0f, 1.0f, 1.0f));
        Material mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat.getAdditionalRenderState().setWireframe(true);
        spatial.setMaterial(mat);
        spatial.setLocalTranslation(
                random.nextFloat(-100f, 100f),
                random.nextFloat(-100f, 100f),
                random.nextFloat(-100f, 100f));

        return spatial;
    }


    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            /*
             screen_8 : FlightControlSystemPage
             screen_7 : Notification ...
             ...
             screen_3 : ScanningControlSystemPage
             ...
            */
            if (b) switch (s){
                case "screen_8": { interfaceMappingLogic(flightControlSystemPageContainer, true); } break;
                case "screen_7": { interfaceMappingLogic(terminalControlSystemPageContainer, true); } break;
                case "screen_3": {
                    interfaceMappingLogic(scanningControlSystemPageContainer, false);
                    GUIManager.setCursorVisible(false);
                    CameraManager.cameraUnlock(true);
                } break;
                default: {logger.warning("\"%s\" is not yet provided.".formatted(s));} break;
            }
        }
    };
    private void interfaceMappingLogic(Container page, boolean cursorChange){
        if (pageOn) {
            // уже есть страница
            // logger.info("Есть какой-то интерфейс");
            if (page == selectPage){
                // это та же самая страница - закрываем и передаём управление пользователю
                // logger.info("Это тот же интерфейс, закрываем");
                pageOn = false;
                app.getGuiNode().detachChild(selectPage);
                selectPage = null;

                if (cursorChange) {
                    GUIManager.setCursorVisible(false);
                    CameraManager.cameraUnlock(true);
                }

            } else {
                // это другая страница - меняем страницу
                // logger.info("Это другой интерфейс, меняем");
                app.getGuiNode().detachChild(selectPage);

                selectPage = page;
                app.getGuiNode().attachChild(selectPage);
            }
        } else {
            // новая страница - создаём новую и забираем контроль у пользователя
            // logger.info("Нет никакого интерфейса, создаём");
            pageOn = true;
            selectPage = page;
            app.getGuiNode().attachChild(selectPage);

            if (cursorChange) {
                GUIManager.setCursorVisible(true);
                CameraManager.cameraUnlock(false);
            }
        }
    }
    private FlightControlSystemPage flightControlSystemPage;
    private Container flightControlSystemPageContainer;

    private TerminalControlSystemPage terminalControlSystemPage;
    private Container terminalControlSystemPageContainer;

    private ScanningControlSystemPage scanningControlSystemPage;
    private Container scanningControlSystemPageContainer;

    private Container selectPage;
    boolean pageOn = false; // есть ли интерфейс на GUI

    @Override
    protected void cleanup(Application application) {

        logger.info("cleanup");

        app.getRootNode().detachChild(outerSpaceScene);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    private float timer = 0;

    @Override
    public void update(float tpf) {
        timer += tpf;
        if (pageOn && timer >= 0.25f ){
            timer = 0;
            scanningControlSystemPage.getRadar().updateTargets(
                    app.getCamera().getLocation(),
                    enemySet,
                    250.0f - 25.0f,
                    3.0f);
            /*List<AbstractEnemy> enemies = enemySet.stream().sorted((o1, o2) -> (int) (o1.getPosition().distance(app.getCamera().getLocation()) -
                                o2.getPosition().distance(app.getCamera().getLocation()))
                    ).toList();*/
            scanningControlSystemPage.getTargetPanel().updateTargets(
                    app.getCamera().getLocation(),
                    enemySet.stream().toList());
        }

        if (flag_debugLabelCamera){
            debugLabelCameraContainerLabel.setText(
                    """
                    Camera position:
                    (%.3f;%.3f;%.3f)
                    Camera direction:
                    (%.3f;%.3f;%.3f)
                    """.formatted(
                            getApplication().getCamera().getLocation().getX(),
                            getApplication().getCamera().getLocation().getY(),
                            getApplication().getCamera().getLocation().getZ(),

                            getApplication().getCamera().getDirection().getX(),
                            getApplication().getCamera().getDirection().getY(),
                            getApplication().getCamera().getDirection().getZ()));
        }
    }

    private Container debugLabelCameraContainer;
    private Label debugLabelCameraContainerLabel;
    private boolean flag_debugLabelCamera;
    private void debugLabelCamera(boolean flag_debugLabelCamera){

        this.flag_debugLabelCamera = flag_debugLabelCamera;

        if (flag_debugLabelCamera) {
            debugLabelCameraContainer = new Container();
            debugLabelCameraContainer.setName("debugLabelCamera");
            debugLabelCameraContainerLabel = new Label("debugLabelCamera");
            debugLabelCameraContainerLabel.setTextHAlignment(HAlignment.Center);
            debugLabelCameraContainerLabel.setTextVAlignment(VAlignment.Center);

            debugLabelCameraContainer.addChild(debugLabelCameraContainerLabel);
            // GUIDebugManager.getContainer().addChild(debugLabelCameraContainer);
        }
    }

}