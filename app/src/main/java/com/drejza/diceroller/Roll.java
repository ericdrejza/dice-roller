package com.drejza.diceroller;

import java.util.ArrayList;
import java.util.EnumMap;


public class Roll implements Rollable {

  public static final int CHAR_DIGIT_TO_INT_OFFSET = -48;

  //private static final String ROLL_REGEX_PATTERN_STRING = "\\s*(\\d*)[d]([4, 6, 8, 10, 12, 20, 100])\\s*([+, -]?)\\s*(\\d*)\\s*";
  //     $1                $2                     $3       $4
  //    count            dice type               sign     |mod|


  /** Class Attributes */
  private EnumMap<Die, Integer> dice;
  private int mod;
  private String name;
  private ArrayList<Roll> separatedRolls;
  private int value;

  // TODO: Figure out what I want to use to track individual rolls
  // an int[] or an ArrayList<Integer> would be nice, but I have to make sure that I get it
  // to line up with the dice in the formula so that everything is printed in order.
  // The other idea is to create an ArrayList of individual rolls and then sum their values.

  /** Contructors */
  public Roll(){
    name = "";
    mod = 0;
    value = 0;
    separatedRolls = new ArrayList<Roll>();

    dice = new EnumMap<>(Die.class);
    for (Die die : Die.values()) {
      dice.put(die, 0);
    }
  }

  public Roll(Die die){
    name = die.getName();
    mod = 0;
    separatedRolls = null;
    dice = new EnumMap<>(Die.class);
    dice.put(die, 1);
    value = die.roll();
  }

  /** GETTERS & SETTERS */
  public EnumMap<Die, Integer> getDice() {
    return dice;
  }

  public void setDice(EnumMap<Die, Integer> dice) {
    this.dice = dice;
  }

  public int getMod() {
    return mod;
  }

  public void setMod(int mod) {
    this.mod = mod;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getNumDice() {
    int totalDice = 0;

    for (Die die : dice.keySet()) {
      if (dice.get(die) != null)
        totalDice += dice.get(die);
    }

    return totalDice;
  }


  /** METHODS */

  // increase the value of the Integer by 1 for the specified die type
  public void addDie(Die die){
    dice.put(die, dice.get(die) + 1 );
  }

  // loop addDie a number of times
  public void addDice(Die die, int numOfDice){
    for (int i = 0; i < numOfDice; i++){
      addDie(die);
    }
  }

  // reset individual rolls
  public void resetSeparatedRolls(){
    separatedRolls = new ArrayList<Roll>();
  }

  // reset products of roll
  public void resetProducts(){
    resetSeparatedRolls();
    value = 0;
  }

  // Sets every Die to map to an Integer with a value of 0
  public void resetDice(){
    for (Die die : Die.values()) {
      dice.put(die, 0);
    }
  }

  // resets dice and sets value and mod to 0
  public void resetRoll(){
    resetDice();
    resetProducts();
    mod = 0;
    name = "";
  }

  // Rolls the dice of the roll with random values and returns the sum
  public int roll(){
    resetProducts();
    value = mod;  // make the start of the roll equal to the mod
    int numDice = getNumDice();

    if (numDice == 1) {
      for (Die die : dice.keySet()) {
        Integer numOfDiceType = dice.get(die);
        if (numOfDiceType != null){
          for (int i = 0; i < numOfDiceType; i++) {
            value += die.roll();
          }
        }
      }
    }
    else if (numDice > 1) {
      // roll for each die in the map and add to the sum
      for (Die die : dice.keySet()) {
        Integer numOfDiceType = dice.get(die);
        if (numOfDiceType != null) {
          for (int i = 0; i < numOfDiceType; i++) {
            separatedRolls.add(new Roll(die));
            value += separatedRolls.get(i).roll();
          }
        }
      }
    }
    return value;
  }


  /** MISC METHODS */
  public Die findFirstExistingDieInDice(){
    for (Die die : dice.keySet()){
      if (dice.get(die) > 0){
        return die;
      }
    }
    return null;
  }


  /** String METHODS */

  // returns a representation of mod in the desired string form: "{sign} {mod}"
  public String modString(){
    String string  = "";

    if (mod > 0) {
      string = string + " + " + mod;
    }
    else if (mod < 0) {
      String modText = String.valueOf(Math.abs(mod));
      string = string + " - " + modText;
    }

    return string;
  }

  // returns the formula of the roll as a string
  public String formulaString() {
    String string = "";
    int totalDice = getNumDice();
    int count = 0;

    if (totalDice == 0){
      return String.valueOf(mod);
    }

    for (Die die : dice.keySet()) {
      Integer numOfDiceType = dice.get(die);
      count += numOfDiceType;
      if (numOfDiceType > 0) {
        string = string + numOfDiceType + die;

        if (count == totalDice)
          break;
        else
          string = string + " + ";
      }
    }

    string = string + modString();

    return string;
  }


  // returns the values of each rolled die grouped in parentheses by die type
  public String separatedRollString() {
    String string = "(";
    Die prevDieType = null;
    Die currDieType;
    Roll currSeparateRoll;

    int numDice = getNumDice();

    if (numDice == 0) {
      return String.valueOf(mod);
    }
    else if (numDice == 1) {
      string = string + (value - mod) + ")";
    }
    else {
      for (int i = 0; i < separatedRolls.size(); i++) {
        currSeparateRoll = separatedRolls.get(i);
        currDieType = currSeparateRoll.findFirstExistingDieInDice();

        if (currDieType == prevDieType || prevDieType == null) {
          string = string + currSeparateRoll.getValue();
          if (i + 1 < separatedRolls.size()){
            if (separatedRolls.get(i+1).findFirstExistingDieInDice() == currDieType) {
              string = string + " + ";
            }
          }
        } else {
          string = string + ") + (" + currSeparateRoll.getValue();
        }

        prevDieType = currDieType;

        if (i == separatedRolls.size() - 1) {
          string = string + ")";
        }
      }
    }

    string = string + modString();

    return string;
  }

  // String Format: *Formula = separated rolls = sum*
  @Override
  public String toString() {
    // start the string as the formula string
    String string = name;
    if (name != null && name != "") {
      string = string + ": ";
    }
    string = string + formulaString();

    // concatenate separtedRollsString
    string = string + " = " + separatedRollString();

    // concatenate sum to string
    string = string + " = " + value;

    return string;
  }

  public String repeatRollsString(int numRepeats) {
    String string = "";
    for (int i = 0; i < numRepeats; i++) {
      this.roll();
      string = string + "\n" + this.toString();
    }
    return string;
  }

  // MAIN
  public static void main(String[] args){
    Roll roll_a = new Roll();
    roll_a.addDie(Die.D4);

    Roll roll_b = new Roll();
    roll_b.addDie(Die.D6);
    roll_b.addDie(Die.D8);
    roll_b.setMod(2);

    Roll roll_c = new Roll();
    roll_c.setMod(-2);

    Roll fireball = new Roll();
    fireball.addDice(Die.D6, 8);
    fireball.roll();


    System.out.println(roll_a.repeatRollsString(5) + "\n");
    System.out.println(roll_b.repeatRollsString(5) + "\n");
    System.out.println(roll_c + "\n");
    System.out.println(fireball);

  }

}
