package com.drejza.diceroller;

import android.util.Log;

import java.util.EnumMap;
import java.util.Random;
import java.util.Scanner;


public class Roll implements Rollable {

  public static final int CHAR_DIGIT_TO_INT_OFFSET = -48;

  //private static final String ROLL_REGEX_PATTERN_STRING = "\\s*(\\d*)[d]([4, 6, 8, 10, 12, 20, 100])\\s*([+, -]?)\\s*(\\d*)\\s*";
  //     $1                $2                     $3       $4
  //    count            dice type               sign     |mod|


  /** Class Attributes */
  private EnumMap<Die, Integer> dice;
  private int mod;
  private String name;
  private int value;


  /** Contructors */
  public Roll(){
    name = null;
    mod = 0;
    value = 0;

    dice = new EnumMap<>(Die.class);
    for (Die die : Die.values()) {
      dice.put(die, new Integer(0));
    }
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



  /** METHODS */

  // increase the value of the Integer by 1 for the specified die type
  public void addDie(Die die){
    dice.put(die, new Integer(dice.get(die).intValue() + 1 ));
  }

  // Sets every Die to map to an Integer with a value of 0
  public void resetDice(){
    for (Die die : Die.values()) {
      dice.put(die, new Integer(0));
    }
  }

  // resets dice and sets value and mod to 0
  public void resetRoll(){
    resetDice();
    mod = 0;
    value = 0;
  }

  // Rolls the dice of the roll with random values and returns the sum
  public int roll(){

    value = mod;  // make the start of the roll equal to the mod

    // roll for each die in the map and add to the sum
    for (Die die : dice.keySet()){
      for (int i = 0; i < dice.get(die).intValue(); i++){
        value += die.roll();
      }
    }
    return value;
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
    System.out.print("hello");
  }

}
