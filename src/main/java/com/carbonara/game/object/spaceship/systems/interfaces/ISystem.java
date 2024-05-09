package com.carbonara.game.object.spaceship.systems.interfaces;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;

import java.util.Set;

public interface ISystem {
    void registerSystemComponent(AbstractSystemComponent component);    // добавляет компонент в систему
    void unregisterSystemComponent(AbstractSystemComponent component);  // удаляет компонент из системы
    Set<AbstractSystemComponent> getSystemComponents(); // получить список зарегистрированных компонентов
}
