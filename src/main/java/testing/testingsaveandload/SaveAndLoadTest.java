package testing.testingsaveandload;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.managers.GameSavesManager;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.io.IOException;

public class SaveAndLoadTest extends SimpleApplication {
    GUIManager guiManager;
    CameraManager cameraManager;

    public static void main(String[] args) {
        SaveAndLoadTest app = new SaveAndLoadTest();
        app.start();
        GlobalSimpleApplication.setApp(app);
    }

    @Override
    public void simpleInitApp() {
        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        GUIDebugManager.init(guiNode);

        enum Choice{
            SAVE,
            LOAD
        }

        Choice choice = Choice.LOAD;

        if (choice == Choice.SAVE){
            // создаём node и загружаем необходимые данные
            Node saveData = new Node();
            saveData.setUserData("name", "Ussura");
            saveData.setUserData("position", new Vector3f(11.1f, 22.2f, 33.3f));
            saveData.setUserData("message", "You look great!");

            // пытаемся сохранить
            try {
                GameSavesManager.save(saveData);
                // System.out.println("save: " + saveName + ".j3o");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (choice.equals(Choice.LOAD)) {
            GameSavesManager.load(GameSavesManager.getLatestSaveFileName().orElse("null"));

            if (GameSavesManager.getDataLoaded().isPresent()){
                Node loadData = GameSavesManager.getDataLoaded().get();
                for (String key : loadData.getUserDataKeys()){
                    System.out.printf("key: %-8s --> value: %-25s\n", key, loadData.getUserData(key).toString());
                }
            }
        }
    }
}
