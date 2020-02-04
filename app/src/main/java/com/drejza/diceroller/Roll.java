package com.drejza.diceroller;

import android.util.Log;

import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Roll {
    public static final int INDEX_D100 = 0;
    public static final int INDEX_D20 = 1;
    public static final int INDEX_D12 = 2;
    public static final int INDEX_D10 = 3;
    public static final int INDEX_D8 = 4;
    public static final int INDEX_D6 = 5;
    public static final int INDEX_D4 = 6;

    public static final int NUM_SIDES_D4 = 4;
    public static final int NUM_SIDES_D6 = 6;
    public static final int NUM_SIDES_D8 = 8;
    public static final int NUM_SIDES_D10 = 10;
    public static final int NUM_SIDES_D12 = 12;
    public static final int NUM_SIDES_D20 = 20;
    public static final int NUM_SIDES_D100 = 100;

    public static final int CHAR_DIGIT_TO_INT_OFFSET = -48;

    private static final String ROLL_REGEX_PATTERN = "\\s*(\\d*)[d]([4, 6, 8, 10, 12, 20, 100])\\s*([+,-]?)\\s*(\\d*)\\s*";
                                                     //     $1                $2                     $3       $4
                                                     //    count            dice type               sign     |mod|

    private static final int[] DICE_SET = new int[]{100, 20, 12, 10, 8, 6, 4};

    /** Class Attributes */
    private int[] dice;  // each index represents the number of the type of die
                             // [d100, d20, d12, d10, d8, d6, d4]

    private int flatMod;
    private int perDieMod;
    private int value;
    private String name;


    /** Contructors */
    public Roll(){
        this.name = null;
        this.dice = new int[]{0, 0, 0, 0, 0, 0, 0};
        this.perDieMod = 0;
        this.flatMod = 0;
        this.value = 0;
    }

    public Roll(String roll){
        dice = new int[]{0, 0, 0, 0, 0, 0, 0};
        value = 0;
        perDieMod = 0;
        flatMod = 0;


        Scanner scnr = new Scanner(roll);
        String line;
        String curr;
        char c;
        int numDice = 0;
        int numSides = 0;
        Dice dieType;

        int sign; // 1 or -1 for positive or negative multiplier

        try{
            if((scnr.hasNextLine())) {
                line = scnr.nextLine();

                if (!line.matches(ROLL_REGEX_PATTERN)) {
                    throw (new Exception("String does not match required pattern"));
                }

                else{

                    /** Step 1: focus on the number of dice */
                    curr = line.replaceAll(ROLL_REGEX_PATTERN, "$1");
                    numDice = intFromString(curr);


                    /** Step 2: Focus on the die type */
                    curr = line.replaceAll(ROLL_REGEX_PATTERN, "$2");
                    numSides = intFromString(curr);
                    dieType = Dice.dieTypeByNumSides(numSides);

                    // plug known values into the dice attribute for this roll
                    dice[Dice.indexOfDieType(dieType)] += numDice;


                    /** Step 3: Focus on the sign of the modifier */
                    curr = line.replaceAll(ROLL_REGEX_PATTERN, "$3");
                    c = curr.charAt(0);
                    if (c == '+'){
                        sign = 1;
                    }
                    else if (c == '-'){
                        sign = -1;
                    }
                    else {
                        sign = 1;
                    }


                    /** Step 4: Focus on the modifier */
                    curr = line.replaceAll(ROLL_REGEX_PATTERN, "$4");
                    flatMod = intFromString(curr) * sign;
                }
            }
        }
        catch(Exception excp){
            Log.d("Roll", excp.getMessage());
        }
    } // End Roll constructor

    public Roll(int count, Dice die, int flatMod){
        dice = new int[]{0, 0, 0, 0, 0, 0, 0};
        value = 0;
        perDieMod = 0;
        this.flatMod = flatMod;

        dice[Dice.indexOfDieType(die)] += count;
    }

    public Roll(int[] dice, int flatMod){
        try{
            if (dice.length > 7){
                throw (new Exception("dice array too big; set roll to default zeros"));
            }
            else {
                this.dice = dice;
                this.flatMod = flatMod;
                this.perDieMod = 0;
                this.value = 0;
            }
        }
        catch(Exception excp){
            Log.e("Roll", excp.getMessage());
            this.dice = new int[]{0,0,0,0,0,0,0};
            this.flatMod = 0;
            this.perDieMod = 0;
            this.value = 0;
        }
    }

    /** GETTERS & SETTERS */
    public int[] getDice() {
        return dice;
    }

    public void setDice(int[] dice) {
        this.dice = dice;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPerDieMod() {
        return perDieMod;
    }

    public void setPerDieMod(int perDieMod) {
        this.perDieMod = perDieMod;
    }

    public int getFlatMod() {
        return flatMod;
    }

    public void setFlatMod(int flatMod) {
        this.flatMod = flatMod;
    }

    public void resetDice(){
        for (int i = 0; i < dice.length; i++){
            this.dice[i] = 0;
        }
    }

    public void resetRoll(){
        resetDice();

        setFlatMod(0);
    }

    /** METHODS */
    // Rolls the dice of the roll with random values and returns the sum
    public int roll(){
        Random random = new Random(System.currentTimeMillis());

        this.value = this.flatMod;  // make the start of the roll the flat mod

        // roll for each die in the array and add to the sum
        for (int i = 0; i < dice.length; i++){
            for (int j = 0; j < dice[i]; j++){
                this.value += (Dice.dieTypeByIndex(i).getNumSides() * Math.random()) + 1;
            }
        }
        return this.value;
    }

    // increase the value by 1 at the index of the specified die type
    public void addDie(Dice die){
        int index = Dice.indexOfDieType(die);
        dice[index] =  dice[index] + 1;
    }



    /** MISC METHODS */

    public static int intFromString(String string){
        int i = 0;
        char c = string.charAt(i);
        int value = 0;

        while(Character.isDigit(c)){

            value = value * 10;
            value += (int)c + CHAR_DIGIT_TO_INT_OFFSET;

            i++;
            c = string.charAt(i);
        }
        return value;
    }



    // MAIN
    public static void main(String[] args){

    }

}
