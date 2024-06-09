package com.carbonara.game.gui.spaceship.systems.commnads;

import com.carbonara.game.gui.spaceship.systems.AbstractSpaceshipSystemPage;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.managers.ServiceLocatorManagers;
import com.carbonara.game.object.gameobjects.categories.player.controls.PlayerStateManager;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;

public class DisplaySystemGUICommand implements IActionCommand {

    private final AbstractSpaceshipSystemPage systemPage;

    public DisplaySystemGUICommand(AbstractSpaceshipSystemPage systemPage){
        this.systemPage = systemPage;
    }

    @Override
    public void execute() {
        System.out.println("display GUI!");

        // типо ставим на паузу обработчик взаимодействия у персонажа
        GlobalSimpleApplication.getApp().getGuiNode().attachChild(systemPage.getScreenForGUI());
        GlobalSimpleApplication.getApp().getStateManager().getState(PlayerStateManager.class)
                .getCameraInteraction().setEnabled(false);

        // блокируем клавишу паузы
        // GlobalSimpleApplication.getApp().getInputManager().deleteMapping("Exit");

        // назначаем на эту клавишу закрытие интерфейса
        GlobalSimpleApplication.getApp().getInputManager().addMapping("ClosePage",
                new KeyTrigger(KeyInput.KEY_ESCAPE));

        // добавляем слушателя
        GlobalSimpleApplication.getApp().getInputManager().addListener(actionListener, "ClosePage");

        // включаем курсор и запрещаем двигаться
        GUIManager.setCursorVisible(true);
        CameraManager.cameraUnlock(false);

        GlobalSimpleApplication.getApp().getStateManager().getState(PlayerStateManager.class)
                .getPlayerCharacter().getPlayerCharacterControl().setEnabled(false);
    }
    private final InputListener actionListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            // возвращаем экран на сцену
            systemPage.pullScreenBack();

            // включаем взаимодействия с объектами
            GlobalSimpleApplication.getApp().getStateManager().getState(PlayerStateManager.class)
                    .getCameraInteraction().setEnabled(true);
            GlobalSimpleApplication.getApp().getInputManager().removeListener(actionListener);
            GlobalSimpleApplication.getApp().getInputManager().deleteMapping("ClosePage");

            // сообщаем менеджеру паузы, что мы закрывали страничку, а не вызывали меню паузы
            ServiceLocatorManagers.getNewPauseGameManager().reportManagerPauseCloseStoryGUITerminal(true);

            // включаем функционал паузы
            GlobalSimpleApplication.getApp().getInputManager()
                    .addListener(ServiceLocatorManagers.getNewPauseGameManager().getInputListener(), "Exit");

            // прячем курсор и разрешаем двигаться
            GUIManager.setCursorVisible(false);
            CameraManager.cameraUnlock(true);

            GlobalSimpleApplication.getApp().getStateManager().getState(PlayerStateManager.class)
                    .getPlayerCharacter().getPlayerCharacterControl().setEnabled(true);
        }
    };
}
