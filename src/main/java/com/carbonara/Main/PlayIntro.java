package com.carbonara.Main;

import com.carbonara.GUI.MainMenu.PageManager;
import com.carbonara.Settings.MainSettings;
import com.carbonara.jme.video.player.MovieSettings;
import com.carbonara.jme.video.player.MovieState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;

import java.util.logging.Logger;

public class PlayIntro extends BaseAppState {

    static {
        Logger.getLogger(PlayIntro.class.getName());
    }

    private MovieState movieState;


    @Override
    protected void initialize(Application application) {

        MainSettings.cameraUnlock(false);
        MainSettings.cursorVisible(false);

        try {
            String path = "src/main/resources/Media/Video/Intro.mp4";

            MovieSettings movie = new MovieSettings(path,
                    application.getCamera().getWidth(),
                    application.getCamera().getHeight(), 1.0f, true);
            movieState = new MovieState(movie);
            application.getStateManager().attach(movieState);

            ((SimpleApplication) application).getFlyByCamera().setEnabled(false);
            application.getInputManager().setCursorVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Application application) {
        // application.getStateManager().attach(new MainMenu(app));
        // logger.log(Level.INFO, "cleanup");
    }

    @Override
    protected void onEnable() {
        //logger.log(Level.INFO, "onEnable");
    }

    @Override
    protected void onDisable() {
        //logger.log(Level.INFO, "onDisable");


    }

    @Override
    public void update(float tpf) {
        if (movieState.isInitialized() && movieState.isStopped()){
            this.getApplication().getStateManager().detach(movieState);
            this.getApplication().getStateManager().detach(this);
            this.getApplication().getStateManager().attach(new PageManager());
        }
    }
}
