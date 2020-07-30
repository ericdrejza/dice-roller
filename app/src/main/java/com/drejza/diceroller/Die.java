package com.drejza.diceroller;

public enum Die implements Rollable{

    D4(4, "d4"),
    D6(6, "d6"),
    D8(8, "d8"),
    D10(10, "d10"),
    D12(12, "d12"),
    D20(20, "d20"),
    D100(100, "d100");

    private int numSides;
    private String name;

    Die(int numSides, String name){
        this.numSides = numSides;
        this.name = name;
    }

    public int getNumSides(){
        return this.numSides;
    }

    public String getName() {
        return name;
    }

    /** MISC METHODS */

    public static Die dieTypeByNumSides(int numSides){
        for (Die die : Die.values()){
            if (numSides == die.getNumSides()){
                return die;
            }
        }
        return null;
    }

    @Override
    public int roll() {
        return (int) (Math.random()*numSides) + 1;
    }

    @Override
    public String toString() {
        return name;
    }
}
