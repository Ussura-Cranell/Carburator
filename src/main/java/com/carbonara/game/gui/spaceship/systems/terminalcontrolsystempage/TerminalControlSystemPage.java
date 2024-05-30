package com.carbonara.game.gui.spaceship.systems.terminalcontrolsystempage;

import com.carbonara.game.gui.spaceship.systems.AbstractSpaceshipSystemPage;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

public class TerminalControlSystemPage extends AbstractSpaceshipSystemPage {

    Terminal terminal;

    public TerminalControlSystemPage(Node point, float scale) {
        super(point, scale);
    }

    @Override
    public void initialize() {
        sizeX = 0.350185f * mul;
        sizeY = 0.317938f * mul;

        this.screen = new Container();
        this.screen.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));

        Container labelContainer = new Container();
        labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label(getClass().getSimpleName());
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 8);

        labelContainer.addChild(label);
        this.screen.addChild(labelContainer);

        terminal = new Terminal(new Vector2f(sizeX, sizeY));
        this.screen.attachChild(terminal.getTerminalContainer());
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
