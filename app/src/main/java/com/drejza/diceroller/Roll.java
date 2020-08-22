package com.drejza.diceroller;

import java.util.Collections;
import java.util.ArrayList;


public class Roll implements Rollable {

  //public static final int CHAR_DIGIT_TO_INT_OFFSET = -48;
  public static final String ROLL_REGEX = "";

  private static final String ROLL_REGEX_PATTERN_STRING = "\\s*[+, -]?\\s*((\\d+)[d](\\d+))*\\s*([+, -]?)\\s*(\\d*)\\s*";
  //     $1       $2           $3          $4
  //    count     dice type    sign       |mod|
  // [4, 6, 8, 10, 12, 20, 100]

  /** Class Attributes */
  //private EnumMap<Die, Integer> dice;
  private ArrayList<Die> dice;
  private String formula; // Will be used if someone wants to type a custom formula
  private int mod;
  private String name;
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
    formula = null;

    dice = new ArrayList<>();

    /*dice = new EnumMap<>(Die.class);
    for (Die die : Die.values()) {
      dice.put(die, 0);
    }*/
  }

  public Roll(Die die){
    name = "";
    mod = 0;
    dice = new ArrayList<>();
    formula = null;

    addDie(die);
  }

  public Roll(String formula) {
    name = "";
    mod = 0;
    value = 0;
    this.formula = formula;

    // TODO

    dice = new ArrayList<>();
  }

  /** GETTERS & SETTERS */
  public ArrayList<Die> getDice() {
    return dice;
  }

  public void setDice(ArrayList<Die> dice) {
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


  /** METHODS */

  // a die to dice
  public void addDie(Die die){
    dice.add(die);
  }

  public void addDie(int dieType){
    dice.add(new Die(dieType));
  }

  // loop addDie a number of times
  public void addDice(Die die, int numOfDice){
    dice.add(die);
    for (int i = 1; i < numOfDice; i++){
      addDie(new Die(die));
    }
  }

  // loop addDie a number of times
  public void addDice(int dieType, int numOfDice){
    for (int i = 0; i < numOfDice; i++){
      addDie(new Die(dieType));
    }
  }

  // Decrement the Integer mapped by the specified die type to a minimum of 0
  public void removeDie(Die die){
    if (dice.contains(die)) {
      dice.remove(die);
    }
    else {
      for (Die die1 : dice){
        if (die.sameNumSides(die1)) {
          dice.remove(die1);
        }
      }
    }
  }

  public void removeDie(int dieType){
    for (int i = 0; i < dice.size(); i++){
      if (dice.get(i).getNumSides() == dieType){
        dice.remove(i);
        return;
      }
    }
  }

  // loop addDie a number of times
  public int removeDice(Die die, int numOfDice){
    int countRemoved = 0;
    for (Die die2 : dice) {
      if (die.sameNumSides(die2)){
        removeDie(die2);
        countRemoved++;
        if (countRemoved == numOfDice)
          return countRemoved;
      }
    }
    return countRemoved;
  }


  // reset products of roll
  public void resetProducts(){
    for (Die die : dice) {
      die.setValue(0);
    }
    value = 0;
  }

  // Sets every Die to map to an Integer with a value of 0
  public void resetDice(){
    dice = new ArrayList<>();
  }

  // resets dice and products, mod to 0, and name to ""
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

    for (Die die : dice) {
      die.roll();
      value += die.getValue();  // add the value of the current die to the value of the roll
    }

    return value;
  }

  // Rolls any d20 dice with advantage - take the highest of all the d20's
  public int rollAdvantage(){
    resetProducts();
    value = mod;

    Die maxDie = null;

    for (Die die : dice) {
      die.roll();
      if ((die.getNumSides() == 20) && ((maxDie == null) || die.getValue() > maxDie.getValue())){
        maxDie = die;
      }
    }

    if (maxDie != null) {
      value += maxDie.getValue();
    }

    return value;
  }

  /** MISC METHODS */


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
    Collections.sort(dice);
    StringBuilder string = new StringBuilder();
    int totalDice = dice.size();

    if (totalDice == 0){
      return String.valueOf(mod);
    }

    int countType = 0;
    Die prevType = null;
    Die currType;

    for (Die die : dice) {
      currType = die;
      if (prevType == null || currType.sameNumSides(prevType)) {
        countType++;
      }
      else {
        string.append(countType).append(prevType.toString()).append(" + ");
        countType = 1;
      }
      prevType = currType;
    }
    string.append(countType).append(prevType.toString());
    string.append(modString());

    return string.toString();
  }

  // returns the values of each rolled die grouped in parentheses by die type
  public String separatedRollString() {
    Collections.sort(dice);
    StringBuilder string = new StringBuilder("(");
    int numDice = dice.size();

    Collections.sort(dice);

    if (numDice == 0) {
      return String.valueOf(mod);
    }

    Die prevDie = null;
    Die currDie;

    for (int i = 0; i < dice.size(); i++) {
      currDie = dice.get(i);

      // if this is the first die (prevDie == null)
      // or this die is the same type as the last die
      if (prevDie == null || currDie.sameNumSides(prevDie)) {
        string.append(currDie.getValue());
      }
      else {
        string.append(") + (").append(currDie.getValue());
      }

      // if there is another die in the list
      if (i + 1 < dice.size()) {

        // if the current die is the same type as the next die
        if (currDie.sameNumSides(dice.get(i + 1))) {
          string.append(" + ");
        }
      }

      prevDie = currDie;
    }

    string.append(")");
    string.append(modString());

    return string.toString();
  }

  // String Format: *Formula = separated rolls = sum*
  @Override
  public String toString() {
    // start the string as the formula string
    String string = name;
    if (name != null && !name.equals("")) {
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
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < numRepeats; i++) {
      this.roll();
      string.append(this.toString()).append("\n");
    }
    return string.substring(0,string.length()-1);
  }

  // MAIN
  public static void main(String[] args){
    Roll roll_a = new Roll();
    roll_a.addDie(new Die(20));

    Roll roll_b = new Roll();
    roll_b.addDie(new Die(6));
    roll_b.addDie(new Die(8));
    roll_b.addDie(new Die(8));
    roll_b.addDie(new Die(10));

    Roll roll_c = new Roll();
    roll_c.addDie(new Die(4));
    roll_c.addDie(new Die(6));
    roll_c.addDie(new Die(8));
    roll_c.addDie(new Die(10));
    roll_c.setMod(-2);
    roll_c.roll();


    Roll fireball = new Roll();
    fireball.addDice(6, 8);
    fireball.roll();


    System.out.println(roll_a.repeatRollsString(1) + "\n");
    System.out.println(roll_b.repeatRollsString(1) + "\n");
    System.out.println(roll_c + "\n");
    System.out.println(fireball + "\n");

//    System.out.println(fireball.repeatRollsString(1));

  }

}
