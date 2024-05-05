package com.carbonara.game.object.spaceship;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;

public class SpaceShipControl implements Control {
    private Spatial spaseShip;

    private float a, b; // center
    private float r; // radius
    private float t = FastMath.DEG_TO_RAD * 0; // value 0 - 360 radian

    public SpaceShipControl(Vector3f circle){
        // 0, 1 - center
        // 2 - radius
        a = circle.getX();
        b = circle.getY();
        r = circle.getZ();
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.spaseShip = spatial;
    }

    @Override
    public void update(float v) {
        this.t -= v * 1.5f;
        if (this.t <= -FastMath.DEG_TO_RAD*360) this.t = 0;

        float x = this.a + this.r * FastMath.cos(this.t);
        float z = this.b + this.r * FastMath.sin(this.t); // Используйте sin для координаты z

        float y = this.r * 0.25f * FastMath.sin(this.t);

        this.spaseShip.lookAt(new Vector3f(x, y, z), Vector3f.UNIT_Y);
        this.spaseShip.setLocalTranslation(x, y, z);
    }

    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {

    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }
}
