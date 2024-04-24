package testing;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;

public class PlayTest001 extends SimpleApplication {

    PlayTest001(){
        super(new StatsAppState(), new DebugKeysAppState());
    }

    public static void main(String[] args) {
        PlayTest001 app = new PlayTest001();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        inputManager.setCursorVisible(false);
        // super(new StatsAppState(), new DebugKeysAppState());
        this.getStateManager().attach(new CameraControl());
    }
}
