package testing.soundtest;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

public class SoundTest extends SimpleApplication {
    GUIManager guiManager;
    CameraManager cameraManager;

    public static void main(String[] args) {
        SoundTest app = new SoundTest();
        app.start();
        GlobalSimpleApplication.setApp(app);
    }

    @Override
    public void simpleInitApp() {
        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        GUIDebugManager.init(guiNode);


        AudioNode boom = new AudioNode(assetManager, "Media/Sound/button_clicked.wav", AudioData.DataType.Buffer);
        boom.setPositional(false);
        boom.play();
    }
}
