package com.drejza.diceroller;

import android.content.Context;

import androidx.annotation.NonNull;

class DiceButton extends com.google.android.material.button.MaterialButton {

  Die die;

  public DiceButton(@NonNull Context context) {
    super(context);
  }

  public void setDie(Die die) {
    this.die = die;
  }

  public Die getDie() {
    return die;
  }

}
