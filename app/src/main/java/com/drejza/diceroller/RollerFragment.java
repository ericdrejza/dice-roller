package com.drejza.diceroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Stack;

public class RollerFragment extends Fragment {

  private Stack<Die> diceStack;
  private String formula;
  private ArrayList<Die> rollHistory;
  private Roll roll;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    diceStack = new Stack<Die>();
    formula = "";
    rollHistory = new ArrayList<Die>();
    roll = new Roll();
  }

  @Nullable
  @Override
  public View onCreateView(
        @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.roller_fragment, container, false);

    setupDiceButtons(view);

    MaterialButton d4_button = view.findViewById(R.id.d4_button);
    MaterialButton d6_button = view.findViewById(R.id.d6_button);
    MaterialButton d8_button = view.findViewById(R.id.d8_button);
    MaterialButton d10_button = view.findViewById(R.id.d10_button);
    MaterialButton d12_button = view.findViewById(R.id.d12_button);
    MaterialButton d20_button = view.findViewById(R.id.d20_button);

    MaterialButton undo_button = view.findViewById(R.id.undo_button);
    MaterialButton roll_button = view.findViewById(R.id.roll_button);
    MaterialButton modify_button = view.findViewById(R.id.modify_button);

    return view;
  }


  public void setupDiceButtons(View view) {
    // Dynamically add the correct die types to each of these DiceButtons and
    // set the appropriate text to the buttons too

    final MaterialButton d4_button = view.findViewById(R.id.d4_button);
    final MaterialButton d6_button = view.findViewById(R.id.d6_button);
    final MaterialButton d8_button = view.findViewById(R.id.d8_button);
    final MaterialButton d10_button = view.findViewById(R.id.d10_button);
    final MaterialButton d12_button = view.findViewById(R.id.d12_button);
    final MaterialButton d20_button = view.findViewById(R.id.d20_button);
    //DiceButton d100_button = view.findViewById(R.id.d100_button);
    MaterialButton[] diceButtons = new MaterialButton[]{d4_button, d6_button, d8_button, d10_button, d12_button, d20_button};

    int d4_button_text = R.string.button_d4;
    int d6_button_text = R.string.button_d6;
    int d8_button_text = R.string.button_d8;
    int d10_button_text = R.string.button_d10;
    int d12_button_text = R.string.button_d12;
    int d20_button_text = R.string.button_d20;
    int[] dice_button_resource_ids = new int[]{d4_button_text, d6_button_text, d8_button_text, d10_button_text, d12_button_text, d20_button_text};

    String[] dice_button_strings = new String[]{};
    for (int i = 0; i < diceButtons.length; i++) {
      diceButtons[i].setText(dice_button_resource_ids[i]);
    }

  }


  // TODO: Add dice to stack and update formula
  public void addDie(Die die) {
    diceStack.push(die);

  }

  // Update formula based on Roll toString()
  public void updateString(Die die) {

  }


  // TODO: Remove die from stack when pressing undo, and clearing formula when holding undo

}
