package it.easymc.easyfish.utils;

import it.easymc.easyfish.EasyFish;

public class Ranger {
    private final double min;
    private final double max;

    public Ranger(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double random(){
        return min + (max - min) * EasyFish.INS.getRandom().nextDouble();
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
