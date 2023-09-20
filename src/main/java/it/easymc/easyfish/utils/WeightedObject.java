package it.easymc.easyfish.utils;

import java.util.ArrayList;
import java.util.Random;

public class WeightedObject {
    private final Object object;
    private final double chance;

    public WeightedObject(Object object, double chance) {
        this.object = object;
        this.chance = chance;
    }

    public Object getObject() {
        return object;
    }

    public double getChance() {
        return chance;
    }

    public static Object choice(ArrayList<WeightedObject> weightedObjects, Random random){
        double totalChance = weightedObjects.stream().mapToDouble(WeightedObject::getChance).sum();
        double casual = random.nextDouble() * totalChance;
        double accumulatedChance = 0.0;
        for (WeightedObject weightedObject: weightedObjects){
            accumulatedChance += weightedObject.chance;
            if (casual <= accumulatedChance){
                return weightedObject.object;
            }
        }
        return null;
    }
}
