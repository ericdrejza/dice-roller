package com.drejza.diceroller;

public enum Die {

    D4(4, 0),
    D6(6, 1),
    D8(8, 2),
    D10(10, 3),
    D12(12, 4),
    D20(20, 5),
    D100(100,6);

    private int numSides;
    private int diceIndex;

    Die(int numSides, int diceIndex){
        this.numSides = numSides;
        this.diceIndex = diceIndex;
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

}
