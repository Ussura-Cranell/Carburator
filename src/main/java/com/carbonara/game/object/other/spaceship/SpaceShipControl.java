package com.carbonara.game.object.other.spaceship;

import com.carbonara.game.managers.PauseGameManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class SpaceShipControl implements Control, Observer {
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


    private boolean flag_pause = false;

    @Override
    public void update(float v) {
        /* if (!flag_pause) {
            this.t -= v * 1.5f;
            if (this.t <= -FastMath.DEG_TO_RAD * 360) this.t = 0;

            float x = this.a + this.r * FastMath.cos(this.t);
            float z = this.b + this.r * FastMath.sin(this.t); // Используйте sin для координаты z

            float y = this.r * 0.5f * FastMath.sin(this.t);

            Vector3f difference = spaseShip.getLocalTranslation().add(new Vector3f(-x, -y, -z));
            Vector3f direction = spaseShip.getLocalTranslation().add(
                    difference.getX(),
                    difference.getY(),
                    difference.getZ());

            this.spaseShip.lookAt(direction, Vector3f.UNIT_Y);
            this.spaseShip.setLocalTranslation(x, y, z);
        } */

        if (!flag_pause) {
            this.t -= v * 0.5f;
            if (this.t <= -FastMath.DEG_TO_RAD * 720) this.t = 0; // Двойной оборот для восьмерки

            // Используйте параметрические уравнения лемнискаты для координат x и z
            float x = this.a + this.r * FastMath.cos(this.t) / (1 + FastMath.pow(FastMath.sin(this.t), 2));
            float z = this.b + this.r * FastMath.sin(this.t) * FastMath.cos(this.t) / (1 + FastMath.pow(FastMath.sin(this.t), 2));

            // Для координаты y можно использовать более простую функцию, чтобы добавить вертикальное движение
            float y = this.r * 0.5f * FastMath.sin(2 * this.t); // Удвоенная частота для синхронизации с восьмеркой

            Vector3f difference = spaseShip.getLocalTranslation().add(new Vector3f(-x, -y, -z));
            Vector3f direction = spaseShip.getLocalTranslation().add(
                    difference.getX(),
                    difference.getY(),
                    difference.getZ());

            this.spaseShip.lookAt(direction, Vector3f.UNIT_Y);
            this.spaseShip.setLocalTranslation(x, y, z);
        }
    }

    public void setFlag_pause(boolean flag_pause) {
        this.flag_pause = flag_pause;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PauseGameManager){
            setFlag_pause((boolean)arg);
        }
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
