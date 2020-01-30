package com.drejza.diceroller;

public enum Dice {

    D100(Roll.NUM_SIDES_D100,Roll.INDEX_D100),
    D20(Roll.NUM_SIDES_D20,Roll.INDEX_D20),
    D12(Roll.NUM_SIDES_D12,Roll.INDEX_D12),
    D10(Roll.NUM_SIDES_D10,Roll.INDEX_D10),
    D8(Roll.NUM_SIDES_D8,Roll.INDEX_D8),
    D6(Roll.NUM_SIDES_D6,Roll.INDEX_D6),
    D4(Roll.NUM_SIDES_D4,Roll.INDEX_D4);

    private int numSides;
    private int diceIndex;

    Dice(int numSides, int diceIndex){
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
    public static int indexOfDieType(Dice dieType){
        switch (dieType){
            case D100:
                return Dice.D100.getDiceIndex();
            case D20:
                return Dice.D20.getDiceIndex();
            case D12:
                return Dice.D12.getDiceIndex();
            case D10:
                return Dice.D10.getDiceIndex();
            case D8:
                return Dice.D8.getDiceIndex();
            case D6:
                return Dice.D6.getDiceIndex();
            case D4:
                return Dice.D4.getDiceIndex();
            default:
                return -1;
        }
    }


    public static Dice dieTypeByIndex(int dieIndex){
        switch(dieIndex){
            case Roll.INDEX_D100:
                return Dice.D100;
            case Roll.INDEX_D20:
                return Dice.D20;
            case Roll.INDEX_D12:
                return Dice.D12;
            case Roll.INDEX_D10:
                return Dice.D10;
            case Roll.INDEX_D8:
                return Dice.D8;
            case Roll.INDEX_D6:
                return Dice.D6;
            case Roll.INDEX_D4:
                return Dice.D4;
            default:
                return Dice.D20;
        }
    }


    public static Dice dieTypeByNumSides(int numSides){
        switch(numSides){
            case Roll.NUM_SIDES_D100:
                return Dice.D100;
            case Roll.NUM_SIDES_D20:
                return Dice.D20;
            case Roll.NUM_SIDES_D12:
                return Dice.D12;
            case Roll.NUM_SIDES_D10:
                return Dice.D10;
            case Roll.NUM_SIDES_D8:
                return Dice.D8;
            case Roll.NUM_SIDES_D6:
                return Dice.D6;
            case Roll.NUM_SIDES_D4:
                return Dice.D4;
            default:
                return Dice.D20;
        }
    }
}
