package com.carbonara.game.gui.spaceship.systems;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

public class ReactorControlSystemPage extends AbstractSpaceshipSystemPage{


    public ReactorControlSystemPage(Node point, float scale) {
        super(point, scale);
    }

    @Override
    public void initialize() {
        sizeX = 0.563868f * mul;
        sizeY = 0.250746f * mul;

        this.screen = new Container();
        this.screen.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));

        Container labelContainer = new Container();
        labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label(getClass().getSimpleName());
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 11);

        labelContainer.addChild(label);
        this.screen.addChild(labelContainer);
    }
}
