package testing.completespaceshipassemblytest;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import jme3utilities.SimpleAppState;

import java.util.logging.Logger;

public class CSASGameGuardian extends BaseAppState {

    private static final Logger logger = Logger.getLogger(com.carbonara.game.logic.NewSceneGuardian.class.getName());
    private SimpleAppState app;

    @Override
    protected void initialize(Application application) {
        app = (SimpleAppState) application;
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    //cprivate void
}