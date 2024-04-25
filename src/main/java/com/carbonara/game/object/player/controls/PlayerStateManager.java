package com.carbonara.game.object.player.controls;

import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.object.player.PlayerCharacter;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.logging.Logger;

public class PlayerStateManager extends BaseAppState {
    Logger logger = Logger.getLogger(PlayerStateManager.class.getName());
    Node space;
    PlayerCharacter playerCharacter;
    PlayerMovingControl playerMovingControl;
    CameraInteraction cameraInteraction;

    public PlayerStateManager(Node space){
        this.space = space;
    }

    @Override
    protected void initialize(Application application) {
        this.playerCharacter = new PlayerCharacter(this.space);
        this.playerMovingControl = new PlayerMovingControl(application.getCamera());

        this.playerCharacter.getSpatial().addControl(this.playerMovingControl);

        addButtonControl();

        // debug
        addDebugPlayerInfo(); // панель если добавили, то надо удалить при очистке
        // шарик камеры персонажа
        this.positionPoinCameraSparial = createPointCameraSpatial();

        //
        this.cameraInteraction = new CameraInteraction(this.space);
        application.getStateManager().attach(this.cameraInteraction);
    }

    @Override
    protected void cleanup(Application application) {

        // взаимодействие камеры с другими объектами
        application.getStateManager().detach(this.cameraInteraction);

        // удаляем шарик представлющий собой положение камеры
        delDebugPlayerInfo();
        this.space.detachChild(this.positionPoinCameraSparial);
        this.playerCharacter.deletePlayerCharacter();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {
        followingCameraHead();
        debugPlayerInfo();
    }

    private boolean flag_FollowingCameraHead = true; // по-умолчанию камера сразу прикреплена к игроку

    private Container debugPlayerInfoContainer;
    private Label debugPlayerInfoLabel;
    private void addDebugPlayerInfo(){
        this.debugPlayerInfoContainer = new Container();
        this.debugPlayerInfoLabel = new Label("debugPlayerInfo");
        debugPlayerInfoLabel.setTextVAlignment(VAlignment.Center);
        debugPlayerInfoLabel.setTextHAlignment(HAlignment.Center);
        debugPlayerInfoContainer.addChild(debugPlayerInfoLabel);

        GUIDebugManager.getContainer().addChild(debugPlayerInfoContainer);
    }
    private void debugPlayerInfo(){
        if (this.debugPlayerInfoLabel != null) // если debug есть
        debugPlayerInfoLabel.setText(
                """
                Player position:
                (%.3f;%.3f;%.3f)
                """.formatted(
                        this.playerCharacter.getPlayerCharacterControl().getPhysicsLocation().getX(),
                        this.playerCharacter.getPlayerCharacterControl().getPhysicsLocation().getY(),
                        this.playerCharacter.getPlayerCharacterControl().getPhysicsLocation().getX()));
        else logger.warning("Use debug without initialization");
    }

    private void delDebugPlayerInfo(){
        if (debugPlayerInfoContainer != null) GUIDebugManager.getContainer().detachChild(debugPlayerInfoContainer);
    }

    private void addButtonControl(){
        // смена режима следования камерой на клавишу T
        getApplication().getInputManager().addMapping("flag_FollowingCameraHead",
                new KeyTrigger(KeyInput.KEY_T));
        getApplication().getInputManager().addMapping("setPlayerToStartingPosition",
                new KeyTrigger(KeyInput.KEY_R));

        getApplication().getInputManager().addListener(inputListener,
                "flag_FollowingCameraHead",
                "setPlayerToStartingPosition");
    }

    private final InputListener inputListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            if (s.equals("flag_FollowingCameraHead") && !b){
                flag_FollowingCameraHead = !flag_FollowingCameraHead;
                logger.info("Change camera tracking mode: " + flag_FollowingCameraHead);
            }
            if (s.equals("setPlayerToStartingPosition") && !b){
                playerCharacter.getPlayerCharacterControl().setPhysicsLocation(
                        PlayerCharacter.PLAYER_INIT_LOCATION);
                logger.info("The player showed up at the coordinates: " +
                        playerCharacter.getPlayerCharacterControl().getPhysicsLocation());
            }
        }
    };

    Spatial positionPoinCameraSparial;
    private Spatial createPointCameraSpatial(){
        Sphere sphere = new Sphere(5, 5, 0.05f);
        Spatial spatial = new Geometry("PointCameraSpatial", sphere);
        Material material = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Yellow);
        material.getAdditionalRenderState().setWireframe(true);
        spatial.setMaterial(material);
        this.space.attachChild(spatial);
        return spatial;
    }

    public static final Vector3f CAMERA_RELATIVE_CHARACTER = new Vector3f(0.0f, 2.0f, 0.0f);

    private void followingCameraHead(){
        // debug шарик камеры персонажа
        if (this.positionPoinCameraSparial != null) this.positionPoinCameraSparial.setLocalTranslation(
                this.playerCharacter.getPlayerCharacterControl().getPhysicsLocation().add(CAMERA_RELATIVE_CHARACTER));

        // передвижение камеры
        if (this.flag_FollowingCameraHead)
            getApplication().getCamera().setLocation(
                    this.playerCharacter.getPlayerCharacterControl().getPhysicsLocation().add(CAMERA_RELATIVE_CHARACTER));

        // теперь игрок будет двигаться только если в него "вселится камера"
        this.playerMovingControl.setFlag_movingControl(this.flag_FollowingCameraHead);
    }
}
