package com.drejza.diceroller;

public enum Die implements Rollable{

    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20),
    D100(100);

    private int numSides;
    private int diceIndex;

    Die(int numSides){
        this.numSides = numSides;
    }

    public int getNumSides(){
        return this.numSides;
    }

    public int getDiceIndex(){
        return this.diceIndex;
    }


    /** MISC METHODS */
    // consumes a die type and returns the index it would be found at in the array

    public static Die dieTypeByIndex(int dieIndex){
        for (Die die : Die.values()) {
            if (dieIndex == die.getDiceIndex()){
                return die;
            }
        }
        return null;
    }

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
}
