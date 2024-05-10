package com.carbonara.game.object.spaceship.managers;

public class EnergyControlManager {
    /* управление энергией корабля */
    private int quantityEnergyUnits; // общее кол-во доступной энергии

    // Метод для добавления произведенной энергии
    public synchronized void produceEnergy(int energy) {
        quantityEnergyUnits += energy;
    }

    // Метод для потребления энергии
    public synchronized boolean consumeEnergy(int energy) {
        if (quantityEnergyUnits - energy >= 0) {
            quantityEnergyUnits -= energy;
            return true;    // Энергия успешно выделена
        } else {
            return false;   // Недостаточно энергии для выделения
        }
    }

    // Метод для освобождения энергии
    public synchronized void releaseEnergy(int energy) {
        quantityEnergyUnits += energy;
    }
}

