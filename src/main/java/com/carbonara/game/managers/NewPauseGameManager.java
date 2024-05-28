package com.carbonara.game.managers;

import com.carbonara.game.gui.pause.pages.PausePage;
import com.carbonara.game.logic.NewSceneGuardian;
import com.carbonara.game.main.GameLauncher;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.simsilica.lemur.input.InputMapper;

import java.util.HashSet;
import java.util.Observable;
import java.util.logging.Logger;

public class NewPauseGameManager extends Observable {
    /*
    * Объект находится в ServiceLocatorManagers
    * */
    private static Logger logger = Logger.getLogger(NewPauseGameManager.class.getName());

    private boolean gamePause;
    private BaseAppState selectedPage = new PausePage();

    public void initialize(){
        logger.info("initialize");
        gamePause = true;
        // регистрация клавиш
        // инициализация параметров логики
        addListener();
    }

    HashSet<String> namesMapping = new HashSet<>();
    InputListener inputListener = (ActionListener) (s, b, v) -> {
        if (s.equals("Exit") && b){
            gamePause = !gamePause;
            CameraManager.cameraUnlock(gamePause);
            GUIManager.setCursorVisible(!gamePause);

            switchPageLogic(!gamePause);

            setChanged();
            notifyObservers(gamePause);
        }
    };
    private void addListener(){
        // отключаем стандартную клавишу вызода из приложения
        GameLauncher.getApp().getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        // добавление отслеживания ESC
        addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));

        GameLauncher.getApp().getInputManager().addListener(inputListener, "Exit");
    }
    private void delListener(){
        // удаление отслеживания ESC
        for (String name : namesMapping){
            GameLauncher.getApp().getInputManager().deleteMapping(name);
        }
        GameLauncher.getApp().getInputManager().removeListener(inputListener);

        // добавляем стандартную клавишу вызода из приложения
        GameLauncher.getApp().getInputManager().addMapping(SimpleApplication.INPUT_MAPPING_EXIT,
                new KeyTrigger(KeyInput.KEY_ESCAPE));
        GameLauncher.getApp().getInputManager().addListener((ActionListener) (s, b, v) -> {
            if (s.equals(SimpleApplication.INPUT_MAPPING_EXIT) && b) GameLauncher.getApp().stop();
        }, SimpleApplication.INPUT_MAPPING_EXIT);
    }

    private void addMapping(String name, Trigger trigger){
        GameLauncher.getApp().getInputManager().addMapping(name, trigger);
        namesMapping.add(name);
    }

    public void cleanup(){
        logger.info("cleanup");
        // очистка всего того, что изменил этот класс
        delListener();

        if (selectedPage != null) switchPageLogic(false);
        //logger.info("!!! GUI pause clearing");



    }

    public void update(float tpf){

    }

    public void switchPageLogic(boolean flag_pause){
        if (flag_pause){
            // на паузе
            //logger.info("На паузе");
            selectedPage = new PausePage();
            GameLauncher.getApp().getStateManager().attach(selectedPage);
        } else {
            // не на паузе
            //logger.info("Не на паузе");
            GameLauncher.getApp().getStateManager().detach(selectedPage);
            selectedPage = null;
        }
    }
}