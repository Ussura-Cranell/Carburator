package com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage;

import com.carbonara.game.gui.spaceship.systems.AbstractSpaceshipSystemPage;
import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.enemy.abstracts.VaultEnemies;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.Random;

public class ScanningControlSystemPage extends AbstractSpaceshipSystemPage {
    private Radar radar;
    private TargetPanel targetPanel;

    public ScanningControlSystemPage(Node point, float scale) { super(point, scale); }

    private VaultEnemies vaultEnemies;
    private AbstractSpaceship spaceship;

    @Override
    public void initialize() {

        vaultEnemies = EvilClass.getVaultEnemies();
        spaceship = AbstractSpaceship.getAbstractSpaceShip();

        sizeX = 0.797655f * mul;
        sizeY = 0.563830f * mul;

        this.screen = new Container();
        this.screen.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));

        Container labelContainer = new Container();
        labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label(getClass().getSimpleName());
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 15);

        labelContainer.addChild(label);

        this.screen.addChild(labelContainer);

        radar = new Radar(new Vector2f(sizeX, sizeY));
        this.screen.attachChild(radar.getRadarContainer());

        targetPanel = new TargetPanel(new Vector2f(sizeX, sizeY));
        this.screen.attachChild(targetPanel.getTargetPanelContainer());
        // createRadar(screen);
    }

    private Random random = new Random();

    public Radar getRadar() {
        return radar;
    }

    public TargetPanel getTargetPanel() {
        // System.out.println("get target panel");
        return targetPanel;
    }

    private float timer  = 0.0f;
   @Override
    public void update(float tpf) {
       // System.out.println("update");
      //  System.out.println("update");

       timer += tpf;

        if (timer >= 0.25 && spaceship != null) {
            timer = 0.0f;
            // System.out.println("update 1");
            // System.out.println("not a null");
            radar.updateTargets(spaceship.getSpaceShipSpatial().getLocalTranslation(),
                    vaultEnemies.getEnemySet(),
                    250.0f,
                    1.0f
            );

            targetPanel.updateTargets(spaceship.getSpaceShipSpatial().getLocalTranslation(),
                    vaultEnemies.getEnemySet().stream().sorted((o1, o2) -> {
                        float o1dis = o1.getPosition().distance(spaceship.getSpaceShipSpatial().getLocalTranslation());
                        float o2dis = o1.getPosition().distance(spaceship.getSpaceShipSpatial().getLocalTranslation());
                        return (int) (o1dis-o2dis);
                    }).toList());

        } else
            // System.out.println("update 2");
            spaceship = AbstractSpaceship.getAbstractSpaceShip();
    }
}
