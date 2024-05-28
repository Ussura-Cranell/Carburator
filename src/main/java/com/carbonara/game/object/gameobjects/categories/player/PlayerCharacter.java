package com.carbonara.game.object.gameobjects.categories.player;

import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.BulletAppStateManager;
import com.carbonara.game.scene.SpaceshipScene;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class PlayerCharacter {
    public static final Vector3f PLAYER_INIT_LOCATION = new Vector3f(15.0f, 15.0f,15.0f);
    public static final float CHARACTER_WIDTH  = 1;
    public static final float CHARACTER_HEIGHT = 3;

    Node space;
    Spatial playerBoxGeometry;
    CharacterControl playerCharacterControl;
    public PlayerCharacter(Node space){

        this.space = space;
        this.playerBoxGeometry = createSpatialRepresentationPlayer(space);

        createPhysicalRepresentationPlayer(space, this.playerBoxGeometry);
    }
    private Spatial createSpatialRepresentationPlayer(Node space){
        // отображение игрока

        Box playerBoxShape = new Box(
                CHARACTER_WIDTH,
                CHARACTER_HEIGHT,
                CHARACTER_WIDTH);
        Geometry playerBoxGeometry = new Geometry("playerBoxGeometry", playerBoxShape);

        // устанавливаем начальное положение игрока
        playerBoxGeometry.setLocalTranslation(PlayerCharacter.PLAYER_INIT_LOCATION);

        Material wireframeMaterial = new Material(GameLauncher.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        wireframeMaterial.setColor("Color", ColorRGBA.Green);
        wireframeMaterial.getAdditionalRenderState().setWireframe(true);
        playerBoxGeometry.setMaterial(wireframeMaterial);

        // прикрепляем его к сцене
        space.attachChild(playerBoxGeometry);

        return playerBoxGeometry;
    }
    private void createPhysicalRepresentationPlayer(Node space, Spatial player){
        int axesIndex = 1;          // ось напраления капсулы

        float stepHeight = 0.25f;   // высота персонажа над полом

        // создаем капсульную форму персонажа
        CapsuleCollisionShape playerCapsuleCollisionShape = new CapsuleCollisionShape(
                CHARACTER_WIDTH,
                CHARACTER_HEIGHT+CHARACTER_WIDTH, // radius
                axesIndex);
        playerCharacterControl = new CharacterControl(playerCapsuleCollisionShape, stepHeight);

        // добавляем физические характеристики
        playerCharacterControl.setJumpSpeed(20);
        playerCharacterControl.setFallSpeed(30);
        playerCharacterControl.setGravity(30);

        // находим наше подприложение обработки физики сцены space

        // BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(space);
        BulletAppState bulletAppState = SpaceshipScene.getBulletAppState();

        // добавляем контроллер физики игрока в физику сцены
        bulletAppState.getPhysicsSpace().add(playerCharacterControl);

        player.addControl(playerCharacterControl);
    }
    public void deletePlayerCharacter(){
        //BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(space);
        BulletAppState bulletAppState = SpaceshipScene.getBulletAppState();
        if (bulletAppState != null && bulletAppState.getPhysicsSpace() != null) {
            bulletAppState.getPhysicsSpace().remove(playerCharacterControl);
        }
        this.space.detachChild(playerBoxGeometry);
    }

    public Spatial getSpatial(){
        return this.playerBoxGeometry;
    }

    public CharacterControl getPlayerCharacterControl() {
        return playerCharacterControl;
    }
}
