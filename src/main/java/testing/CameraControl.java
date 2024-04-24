package testing;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;

public class CameraControl extends BaseAppState {
    Camera cam;
    @Override
    protected void initialize(Application application) {
        cam = application.getCamera();
        application.getInputManager().addListener(actionListener, "something");
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            System.out.println( s + " " + b + " " + v);
        }
    };

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
