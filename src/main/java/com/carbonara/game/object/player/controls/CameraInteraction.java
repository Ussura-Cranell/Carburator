package com.carbonara.game.object.player.controls;

import com.carbonara.game.logic.interaction.AbstractInteraction;
import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.logic.interaction.interfaces.IInteraction;
import com.carbonara.game.object.player.general.InteractionControl;
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
import com.simsilica.lemur.Container;

import java.util.Scanner;
import java.util.logging.Logger;

public class CameraInteraction extends AbstractInteraction {

    // подприложение отвечающее за взаимодействие
    // летающа камера может взаимодействовать с выбранным подпростанством

    Logger logger = Logger.getLogger(CameraInteraction.class.getName());
    Node space;
    Container cameraDirectionPoint;
    CameraInteraction(Node space){
        this.space = space;
    }

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
        GameLauncher.getApp().getGuiNode().attachChild(cameraDirectionPoint);
    }

    private InputListener inputListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            if (s.equals("InteractionButton") && b){
                testing();
            }
        }
    };

    @Override
    protected void cleanup(Application application) {
        GameLauncher.getApp().getGuiNode().detachChild(cameraDirectionPoint);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

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
            System.out.println("\nКоманды доступные для выполнения:");
            for (String nameCommand : interactionControl.getAvailableActionsDescriptions()) {
                System.out.println(nameCommand);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите команду для выполнения: ");
            String nameCommandForExecute = scanner.nextLine();
            interactionControl.interact(nameCommandForExecute);
        }
    }

    @Override
    public BaseAppState getInteraction() {
        return this;
    }
}
