package testing.completespaceshipassemblytest;

import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.enemy.abstracts.Enemy;
import com.carbonara.game.object.other.spaceship.CreateTestSpaceship;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.components.reactor.Reactor;
import com.carbonara.game.object.other.spaceship.components.shield.Shield;
import com.carbonara.game.object.other.spaceship.components.weapon.Weapon;
import com.carbonara.game.object.other.spaceship.systems.ReactorControlSystem;
import com.carbonara.game.object.other.spaceship.systems.ShieldControlSystem;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class CSASGameGuardian extends BaseAppState {

    private static final Logger logger = Logger.getLogger(com.carbonara.game.logic.NewSceneGuardian.class.getName());
    private SimpleApplication app;
    private Node spaceNode;


    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;

        // EvilClass.initialize();

        spaceNode = CSAScene.createScene();

        loadTheShip();
        loadEnemy();
        registerButtonSendEmail();

        app.getRootNode().attachChild(spaceNode);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {
        EvilClass.getVaultEnemies().update(tpf);

    }

    private void loadEnemy(){
        // добавили 5 врагов на сцену
        EvilClass.getVaultEnemies().addEnemy(new Enemy());
        EvilClass.getVaultEnemies().addEnemy(new Enemy());
       //  EvilClass.getVaultEnemies().addEnemy(new Enemy());
       //  EvilClass.getVaultEnemies().addEnemy(new Enemy());
       //  EvilClass.getVaultEnemies().addEnemy(new Enemy());
    }

    private void loadTheShip(){

        Spatial spaceshipSpatial = CSAScene.getSpaceshipSpatial();

        // создали корабль
        AbstractSpaceship testSpaceShip = new CreateTestSpaceship(spaceshipSpatial, spaceNode);
        app.getStateManager().attach(testSpaceShip);

        // добавили системы вместе с компонентами
        addWeaponControlSystem(testSpaceShip);
        addShieldControlSystem(testSpaceShip);
        addReactorControlSystem(testSpaceShip);

        // в рамках тестирования, системы прекращают свою работу самостоятельно (будут на клавишу)
        // testSpaceShip.getMainControlSystem().setEnableSystems(true);

        // выводим то, из чего состоит наш корабль
        System.out.println(testSpaceShip);
    }
    private void sendEmail(){

        // EvilClass.getVaultEnemies().update();

        // все системы обнавляются вместе с отладкой
        //AbstractSpaceShip.getAbstractSpaceShip().getMainControlSystem().getSystems().values().forEach(AbstractSystem::updateSystem);

        /*/// closestEnemy
        var closestEnemy = EvilClass.getVaultEnemies().getClosestEnemy();
        System.out.print("closestEnemy: ");
        closestEnemy.ifPresentOrElse(System.out::print, () -> System.out.print("null"));
        System.out.println();*/

        System.out.println("\n"+LocalDateTime.now());

        // вывести все компонеты систем
        System.out.println("Component list:");
        AbstractSpaceship.getAbstractSpaceShip().getMainControlSystem().getSystems().values()
                .forEach(abstractSystem -> abstractSystem.getSystemComponents().forEach(System.out::println));

        // вывести всех противников
        System.out.println("Enemy list:");
        EvilClass.getVaultEnemies().getEnemySet().forEach(System.out::println);
        System.out.println();

    }

    private ActionListener sendMeEmail = (s, b, v) -> {
        if (s.equals("send") && b){
            sendEmail();
        }
    };

    private void addWeaponControlSystem(AbstractSpaceship spaceship){
        spaceship.getMainControlSystem().registerSystem(new WeaponControlSystem());
        // меняем размерность вместимости системы на 2 единицы
        var spaceshipControlSystem = spaceship.getMainControlSystem().getSystem(WeaponControlSystem.class);
        spaceshipControlSystem.ifPresent(controlSystem -> controlSystem.setTotalSpaceUnits(1));
        // добавляем 2 реактора
        spaceship.getMainControlSystem().registerSystemComponent(new Weapon());
        spaceship.getMainControlSystem().registerSystemComponent(new Weapon());
    }
    private void addShieldControlSystem(AbstractSpaceship spaceship){
        spaceship.getMainControlSystem().registerSystem(new ShieldControlSystem());
        // меняем размерность вместимости системы на 2 единицы
        var spaceshipControlSystem = spaceship.getMainControlSystem().getSystem(ShieldControlSystem.class);
        spaceshipControlSystem.ifPresent(controlSystem -> controlSystem.setTotalSpaceUnits(1));
        // добавляем 2 реактора
        spaceship.getMainControlSystem().registerSystemComponent(new Shield());
        spaceship.getMainControlSystem().registerSystemComponent(new Shield());
    }
    private void addReactorControlSystem(AbstractSpaceship spaceship){
        spaceship.getMainControlSystem().registerSystem(new ReactorControlSystem());
        // меняем размерность вместимости системы на 2 единицы
        var spaceshipControlSystem = spaceship.getMainControlSystem().getSystem(ReactorControlSystem.class);
        spaceshipControlSystem.ifPresent(controlSystem -> controlSystem.setTotalSpaceUnits(1));
        // добавляем 2 реактора
        spaceship.getMainControlSystem().registerSystemComponent(new Reactor());
        spaceship.getMainControlSystem().registerSystemComponent(new Reactor());
    }
    private void registerButtonSendEmail(){
        app.getInputManager().addMapping("send", new KeyTrigger(KeyInput.KEY_I));
        app.getInputManager().addListener(sendMeEmail, "send");
    }

}