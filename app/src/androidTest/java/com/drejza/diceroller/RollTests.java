package com.drejza.diceroller;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class RollTests {

    @Test
    public void testRoll(){
        Roll roll_A = new Roll(1, Die.D6, 0);
        int roll_A_value = roll_A.roll();
        assertTrue(roll_A_value >=1 && roll_A_value <=6);
    }
}
