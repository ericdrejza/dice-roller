package com.drejza.diceroller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Die implements Rollable, Comparable<Die>{

    private int numSides;
    private int value;

    Die(int numSides){
        this.numSides = numSides;
    }

    public int getNumSides(){
        return this.numSides;
    }

    /** OVERRIDDEN METHODS */

    @Override
    public int compareTo(Die other) {
        return this.numSides - other.getNumSides();
    }

    @Override
    public int roll(){
        return (int) (Math.random()*numSides) + 1;
    }

    @Override
    public String toString(){
        return "d" + numSides;
    }

    public static void main(String args[]){
        Die d4 = new Die(4);
        Die d20 = new Die(20);

        ArrayList<Die> dice =  new ArrayList<>(
              Arrays.asList(d4, d20));
    }

    dice.sort(new Comparator<Die>(){
        @Override
        public int compare(Die die1, Die die2) {
            return die1.getNumSides() - die2.getNumSides();
        }
    });
}
