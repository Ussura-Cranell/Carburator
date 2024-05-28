package com.carbonara.game.managers;

import com.carbonara.game.gui.pause.pages.PausePage;
import com.carbonara.game.logic.SceneGuardian;
import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.object.gameobjects.categories.player.controls.CameraInteraction;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;

import java.util.*;
import java.util.logging.Logger;

@Deprecated
public class PauseGameManager extends Observable implements AppState {

    private static Logger logger = Logger.getLogger(PauseGameManager.class.getName());
    private boolean flag_pause = false; // по-умолчанию игра не на паузе
    BaseAppState selectedPage = null;

    public void setStatus(boolean gamePause) {  // изменение состояния паузы
        flag_pause = gamePause;
        setChanged();                   // Отмечаем, что состояние изменилось
        notifyObservers(gamePause);     // Уведомляем наблюдателей об изменении

        if (gamePause) pause(); // ставим игру на паузу
        else unpause();         // возобновляем игру
    }

    private void pause(){
        // что происходит когда игра на паузе
    }

    private void unpause(){
        // что просиходит при возобновлении
    }

    private AppStateManager appStateManager;
    private Application application;
    private boolean isInitialized = false;
    private boolean isEnabled = false;

    private static PauseGameManager pauseGameManager;

    @Override
    public void initialize(AppStateManager appStateManager, Application application) {

        if (pauseGameManager == null) {

            logger.info("initialize");
            this.appStateManager = appStateManager;
            this.application = application;
            this.isInitialized = true;
            setEnabled(false); // включается в loadingPage
            pauseGameManager = this;

            addObserversFromQueue();
            // все слушатели которые были созданы раньше менеджера ждали своей очереди на поделючение
        } else {
            logger.warning("The pause handler is already connected!");
            throw new RuntimeException("The pause handler is already connected!");
        }
    }

    InputListener inputListener  = (ActionListener) (s, b, v) -> {
        if (s.equals("GamePause") && b){
            // временная реализация "ставим всю сцену на паузу, или наоборот"
            setStatus(!flag_pause);
            getApplication().getStateManager().getState(SceneGuardian.class).setEnabled(!flag_pause);
            if (flag_pause) getApplication().getStateManager().getState(CameraInteraction.class).defeatPurposeInteraction();
            switchPageLogic(flag_pause);
        }
    };

    InputListener exitListener  = (ActionListener) (s, b, v) -> {
        if (s.equals(SimpleApplication.INPUT_MAPPING_EXIT) && b){
            GameLauncher.getApp().stop();
        }
    };

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

    private void onEnable(){
        logger.info("onEnable");

        // старый код
        application.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        application.getInputManager().addMapping("GamePause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        application.getInputManager().addListener(inputListener, "GamePause");
    }

    private void onDisable(){
        logger.info("onDisable");

        // старый код
        application.getInputManager().addMapping(SimpleApplication.INPUT_MAPPING_EXIT, new KeyTrigger(KeyInput.KEY_ESCAPE));
        application.getInputManager().deleteMapping("GamePause");
        application.getInputManager().removeListener(inputListener);
    }

    @Override
    public void update(float v) {

    }

    @Override
    public void cleanup() {
        logger.info("Удаление обработчика паузы");

        // удаляем триггер клавиши паузы и непосредственно саму команду

        // это сделано чтобы невозможно было создавать несколько обработчиков паузы
        application.getInputManager().deleteMapping("GamePause");
        application.getInputManager().removeListener(inputListener);
        // если открыта страница в данный момен, то удаляем
        if (selectedPage != null) application.getStateManager().detach(selectedPage);
        selectedPage = null;

        pauseGameManager = null;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setEnabled(boolean b) {
        isEnabled = b;

        if (b) onEnable();
        else onDisable();
    }
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
    @Override
    public void stateAttached(AppStateManager appStateManager) {
        logger.info("stateAttached");
    }
    @Override
    public void stateDetached(AppStateManager appStateManager) {
        logger.info("stateDetached");
    }

    @Override
    public void render(RenderManager renderManager) {

    }
    @Override
    public void postRender() {
    }
    private Application getApplication() {
        return application;
    }

    private static ArrayList<Observer> observers = new ArrayList<>();

    public static void addObserverPause(Observer o){

        if (pauseGameManager!=null) pauseGameManager.addObserver(o);
        else {
            observers.add(o);
            // logger.warning("You cannot connect a pause handler listener if the pause handler is not initialized!");
            // throw new RuntimeException("You cannot connect a pause handler listener if the pause handler is not initialized!");
        }
    }

    private void addObserversFromQueue(){
        for (Observer observer : observers){
            pauseGameManager.addObserver(observer);
        }
        observers.clear();
    }
}
