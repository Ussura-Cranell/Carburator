package com.carbonara.game.object.gameobjects.categories.player.controls;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.AbstractInteraction;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.PauseGameManager;
import com.carbonara.game.object.gameobjects.categories.player.general.InteractionControl;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class CameraInteraction extends AbstractInteraction implements Observer {

    // подприложение отвечающее за взаимодействие
    // летающа камера может взаимодействовать с выбранным подпростанством

    private final Logger logger = Logger.getLogger(CameraInteraction.class.getName());
    private Node space;
    private Container cameraDirectionPoint;

    // отображение панели выбора
    private Container objectActionPanel; // панель с выбором возможных действий
    private InteractionControl interactionControlObject; // объект с которым взаимодействует игрок
    private boolean flag_interactOnObject = false; // игрок сейчас взаимодействует с объектом?

    private boolean flag_gamePause = false;
    private void setFlagGamePause(boolean flag){
        flag_gamePause = flag;

        if (flag_gamePause && flag_interactOnObject) {
            defeatPurposeInteraction(); // если игра на паузе, скрыть панель взаимодействия
        }
    }

    public CameraInteraction(Node space){
        this.space = space;
    }

    private ArrayList<String> availableActionsDescriptions;

    @Override
    protected void initialize(Application application) {
        application.getInputManager().addMapping("InteractionButton", new KeyTrigger(KeyInput.KEY_E));
        application.getInputManager().addListener(inputListener, "InteractionButton");

        cameraDirectionPoint = new Container();

        float size = 7;

        cameraDirectionPoint.setPreferredSize(new Vector3f(size, size, 1));
        cameraDirectionPoint.setLocalTranslation(
                (float) GameSettings.getAppSettings().getWidth() /2 - size /2,
                (float) GameSettings.getAppSettings().getHeight() /2 + size /2,
                1);
        GlobalSimpleApplication.getApp().getGuiNode().attachChild(cameraDirectionPoint);

        // панель доступных действиий для игрока
        objectActionPanel = new Container();
        // objectActionPanel.setLocalTranslation(new Vector3f(500, 500, 1));
        objectActionPanel.setPreferredSize(new Vector3f(300, 300, 1));
        // objectActionPanel.setSize(new Vector3f(300, 20, 1));
        GlobalSimpleApplication.getApp().getGuiNode().attachChild(objectActionPanel);

        application.getInputManager().addMapping("InteractionWithObject1", new KeyTrigger(KeyInput.KEY_1));
        application.getInputManager().addMapping("InteractionWithObject2", new KeyTrigger(KeyInput.KEY_2));
        application.getInputManager().addMapping("InteractionWithObject3", new KeyTrigger(KeyInput.KEY_3));
        application.getInputManager().addMapping("InteractionWithObject4", new KeyTrigger(KeyInput.KEY_4));
        application.getInputManager().addMapping("InteractionWithObject5", new KeyTrigger(KeyInput.KEY_5));
        application.getInputManager().addMapping("InteractionWithObject6", new KeyTrigger(KeyInput.KEY_6));
        application.getInputManager().addMapping("InteractionWithObject7", new KeyTrigger(KeyInput.KEY_7));
        application.getInputManager().addMapping("InteractionWithObject8", new KeyTrigger(KeyInput.KEY_8));
        application.getInputManager().addMapping("InteractionWithObject9", new KeyTrigger(KeyInput.KEY_9));

        application.getInputManager().addListener(interactionWithObjectListener,
                "InteractionWithObject1",
                "InteractionWithObject2",
                "InteractionWithObject3",
                "InteractionWithObject4",
                "InteractionWithObject5",
                "InteractionWithObject6",
                "InteractionWithObject7",
                "InteractionWithObject8",
                "InteractionWithObject9");
    }

    private final InputListener inputListener = (ActionListener) (s, b, v) -> {
        if (s.equals("InteractionButton") && b){
            // если игрок нажмён на взаимодействие когда меню открыто, оно закроется

            if (!flag_gamePause) // проверка на паузу (на паузе нельзя взаимодействовать
                if (flag_interactOnObject) defeatPurposeInteraction();
                else testing();
        }
    };

    public InputListener getInputListener() {
        return inputListener;
    }

    private final InputListener interactionWithObjectListener = (ActionListener) (s, b, v) -> {
        // по последней цифре в названии
        if (flag_interactOnObject && !b) { // есть активный объект и кнопка отжата
            int i = Character.getNumericValue(s.charAt(s.length()-1));
            if (i <= availableActionsDescriptions.size()) {
                interactionControlObject.interact(availableActionsDescriptions.get(i - 1));
                defeatPurposeInteraction();
            }
        }
    };

    @Override
    protected void cleanup(Application application) {
        GlobalSimpleApplication.getApp().getGuiNode().detachChild(cameraDirectionPoint);
        GlobalSimpleApplication.getApp().getGuiNode().detachChild(objectActionPanel);
    }

    @Override
    protected void onEnable() {
        // временная реализация
        setFlagGamePause(false);
    }

    @Override
    protected void onDisable() {
        // временная реализация
        setFlagGamePause(true);
    }

    private void testing(){
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(getApplication().getCamera().getLocation(), getApplication().getCamera().getDirection());
        space.collideWith(ray, results);

        // Луч проходит через колайдер игрока, начинается с 2, чтобы обойти игрока и его точку крепления камеры
        InteractionControl interactionControl = null;

        for (int i = 2; i < results.size(); i++) {
            CollisionResult collisionResult = results.getCollision(i);
            interactionControl = collisionResult.getGeometry().getControl(InteractionControl.class);
            if (interactionControl != null) {
                break;
            }
        }

        // Проверка, висит ли контроллер взаимодействия и можно ли вообще взаимодействовать с ним сейчас
        if (interactionControl != null && interactionControl.isCanInteract()) {

            // если объект имеет моментальную команду "execute"
            if (interactionControl.getAvailableActionsDescriptions().contains("execute")){
                interactionControl.interact("execute");
                return;
            }

            // иначе ...

            interactionControlObject = interactionControl; // класс управления действиями
            flag_interactOnObject = true; // игрок в данный момент взаимодействует с этим объектом

            logger.info("InteractionControlObject: " + interactionControl.getInteractionObject().getName());
            logger.info("flag_interactOnObject: " + flag_interactOnObject);

            // objectActionPanel = new Container();
            // objectActionPanel.clearChildren();   !!!

            availableActionsDescriptions = new ArrayList<>(interactionControl.getAvailableActionsDescriptions().stream().toList());
            Collections.sort(availableActionsDescriptions);

            Optional<Integer> maxSizeStringOptional = availableActionsDescriptions.stream().map(String::length).max((o1, o2) -> (o1 > o2) ? o1 : o2);

            int SIZE = 10;

            int maxSizeStringInt = SIZE;

            if (maxSizeStringOptional.isPresent())
                if (maxSizeStringOptional.get() > SIZE)
                    maxSizeStringInt = maxSizeStringOptional.get();

            //logger.info("max size: " + maxSizeStringInt);

            objectActionPanel.setPreferredSize(new Vector3f(
                    maxSizeStringInt * 14,
                    availableActionsDescriptions.size() * 40, 1));

            int sequenceNumber = 1;
            for (String nameCommand : availableActionsDescriptions){
                Container labelContainer = new Container();
                labelContainer.setPreferredSize(new Vector3f(objectActionPanel.getPreferredSize().getX(), 20, 1));

                Label actionLabel = new Button("    [%d] %s".formatted(sequenceNumber++, nameCommand));
                actionLabel.setTextHAlignment(HAlignment.Left);
                actionLabel.setTextVAlignment(VAlignment.Center);
                labelContainer.addChild(actionLabel);

                objectActionPanel.addChild(labelContainer);
            }
        }
    }

    @Override
    public BaseAppState getInteraction() {
        return this;
    }



    @Override
    public void update(float tpf) {

        if (!flag_gamePause) {

            if (flag_interactOnObject) {

                objectActionPanel.setAlpha(1.0f); // показать панель действий

                // objectActionPanel.setLocalTranslation(interactionControlObject.getInteractionObject().get);
                Vector3f objectPositionOnScreen = CameraManager.getApp().getCamera().getScreenCoordinates(
                        interactionControlObject.getInteractionObject().getWorldTranslation());
                objectActionPanel.setLocalTranslation(objectPositionOnScreen.add(new Vector3f(0, objectActionPanel.getPreferredSize().getY() / 2, 0)));
                // если игрок взаимодействует с объектом

                //if (CameraManager.getApp().getCamera().get)

                // logger.info("distance: " + CameraManager.getApp().getCamera().getLocation().distance(
                //         interactionControlObject.getInteractionObject().getWorldTranslation()));
                if (CameraManager.getApp().getCamera().getLocation().distance(
                        interactionControlObject.getInteractionObject().getWorldTranslation()) > 10) {

                    // проверка на дистанцию

                    defeatPurposeInteraction();
                }

                // logger.info("LocalTranslation: " + objectActionPanel.getLocalTranslation());
                if (objectActionPanel.getLocalTranslation().getX() < 0 || objectActionPanel.getLocalTranslation().getX() > GameSettings.getAppSettings().getWidth() ||
                        objectActionPanel.getLocalTranslation().getY() < 0 || objectActionPanel.getLocalTranslation().getY() > GameSettings.getAppSettings().getHeight() ||
                        objectActionPanel.getLocalTranslation().getZ() > 1) {
                    // вышел за пределы экрана

                    defeatPurposeInteraction();
                }
            }
        }
    }

    public void defeatPurposeInteraction(){
        flag_interactOnObject = false; // не обрабатывать
        objectActionPanel.clearChildren(); // удалить все возможные действия
        objectActionPanel.setAlpha(0.0f); // скрыть панель действий

        interactionControlObject = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PauseGameManager) setFlagGamePause((boolean) arg);
        // else if (o instanceof PauseGameManager) setFlagGamePause((boolean) arg);
    }
}
