package com.carbonara.game.gui.pause.managers;

import com.carbonara.game.gui.pause.pages.PausePage;
import com.carbonara.game.logic.SceneGuardian;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;

import java.util.logging.Logger;

public class PauseGameManager extends BaseAppState {

    // реализация игровой паузы
    Logger logger = Logger.getLogger(PauseGameManager.class.getName());
    private boolean flag_pause = false; // по-умолчанию игра не на паузе
    BaseAppState selectedPage = null;

    @Override
    protected void initialize(Application application) {
        // блокируем клавишу выхода (на неё будет открываться меню
        application.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        application.getInputManager().addMapping("GamePause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        application.getInputManager().addListener(inputListener, "GamePause");
    }

    InputListener inputListener  = (ActionListener) (s, b, v) -> {
        if (s.equals("GamePause") && b){
            // временная реализация "ставим всю сцену на паузу, или наоборот"
            flag_pause = !flag_pause;
            getApplication().getStateManager().getState(SceneGuardian.class).setEnabled(!flag_pause);
            switchPageLogic(flag_pause);
        }
    };

    @Override
    protected void cleanup(Application application) {

        logger.info("Удаление обработчика паузы");

        // удаляем триггер клавиши паузвы и непосредственно саму команду
        application.getInputManager().deleteMapping("GamePause");
        application.getInputManager().removeListener(inputListener);

        // если открыта страница в данный момен, то удаляем
        if (selectedPage!=null) application.getStateManager().detach(selectedPage);
        selectedPage = null;
    }

    @Override
    protected void onEnable() {
         // добавляем слушателя
    }

    @Override
    protected void onDisable() {
        // удаляем слушателя
    }

    @Override
    public void update(float tpf) {
        // логика отображения страниц паузы

        // например приостановка текущего менеджера и включение соответствующей страницы.
        // отключение можно реализовать через setEnable
    }

    public void switchPageLogic(boolean flag_pause){
        if (flag_pause){
            // на паузе
            logger.info("На паузе");
            selectedPage = new PausePage();
            getApplication().getStateManager().attach(selectedPage);
        } else {
            // не на паузе
            logger.info("Не на паузе");
            getApplication().getStateManager().detach(selectedPage);
            selectedPage = null;
        }
    }
}
