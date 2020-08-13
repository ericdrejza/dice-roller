package com.drejza.diceroller;

import java.util.ArrayList;
import java.util.Arrays;

public class Die implements Rollable, Comparable<Die> {

    private int numSides;
    private int value;

    Die(int numSides) {
        this.numSides = numSides;
    }

    Die(Die die) {
        numSides = die.numSides;
        value = die.value;
    }

    public int getNumSides() {
        return this.numSides;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        value = val;
    }


    /**
     * COMPARISON METHODS
     */

    public boolean sameNumSides(Die die2) {
        return this.numSides == die2.numSides;
    }


    /**
     * OVERRIDDEN METHODS
     */

    @Override
    public int compareTo(Die other) {
        return this.numSides - other.getNumSides();
    }

    @Override
    public int roll() {
        value = (int) (Math.random() * numSides) + 1;
        return value;
    }

    @Override
    public String toString() {
        return "d" + numSides;
    }

    public static void main(String[] args) {
        Die d4 = new Die(4);
        Die d20 = new Die(20);

        ArrayList<Die> dice = new ArrayList<>(
              Arrays.asList(d4, d20));

        System.out.println(d4.roll());
        System.out.println(d4.roll());
        System.out.println(d20.roll());
    }

}
