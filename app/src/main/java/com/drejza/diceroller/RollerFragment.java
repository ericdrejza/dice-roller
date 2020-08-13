package com.drejza.diceroller;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.Stack;

public class RollerFragment extends Fragment {

  public final static int d4_VALUE = 4;
  public final static int d6_VALUE = 6;
  public final static int d8_VALUE = 8;
  public final static int d10_VALUE = 10;
  public final static int d12_VALUE = 12;
  public final static int d20_VALUE = 20;

  private Stack<Die> diceStack;
  private String formula;
  private Roll roll;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    diceStack = new Stack<Die>();
    formula = "";
    roll = new Roll();
    //rollHistory = new ArrayList<String>();
  }

  @Nullable
  @Override
  public View onCreateView(
        @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.roller_fragment, container, false);

    final EditText formula_edit_text = view.findViewById(R.id.formula_edit_text);
    final TextView rollValueTextView = view.findViewById(R.id.roll_value_text_view);


    // Button Declaration & Initialization
    setupDiceButtons(view);

    // Action Buttons
    MaterialButton undo_button = view.findViewById(R.id.undo_button);
    MaterialButton roll_button = view.findViewById(R.id.roll_button);
    MaterialButton modify_button = view.findViewById(R.id.modify_button);
    MaterialButton clear_history_button = view.findViewById(R.id.clear_history_button);

    // Setting OnClickListeners for Action Buttons

    // set undo onClickListener
    undo_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!diceStack.isEmpty()){
          Die lastDie = diceStack.pop();
          roll.removeDie(lastDie);
        }
        updateFormulaEditText(formula_edit_text);
      }
    });
    // set undo onLongClickListener
    undo_button.setOnLongClickListener( new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        diceStack.removeAllElements();
        roll.resetRoll();

        updateFormulaEditText(formula_edit_text);
        updateRollValueTextView(rollValueTextView);
        Toast.makeText(getContext(), R.string.cleared_toast, Toast.LENGTH_SHORT).show();
        return true;
      }
    });

    // set roll OnClickListener
    roll_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View viewOther) {
        roll.roll();
        updateRollValueTextView(rollValueTextView);

        // updating roll history

        ConstraintLayout rollHistoryConstraintLayout = view.findViewById(R.id.roll_history_layout);
        int cLayoutChildCount = rollHistoryConstraintLayout.getChildCount();
        TextView currTextView = null;

        for (int i = 0; i < cLayoutChildCount; i++){
          currTextView = (TextView) rollHistoryConstraintLayout.getChildAt(i);
          System.out.println("found TextView: " + currTextView.getText());
          ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) currTextView.getLayoutParams();
          // Check to see if the current text view's bottom is constrained by the bottom of the parent
          if (lp.bottomToBottom == rollHistoryConstraintLayout.getId()) {
            System.out.println("found bottomMostTextView: " + currTextView.getText());
            break;
          }
        }

        // set histroyTextView fields
        TextView mostRecentHistoryTextView = new TextView(getContext());
        rollHistoryConstraintLayout.addView(mostRecentHistoryTextView);
        mostRecentHistoryTextView.setId(View.generateViewId());
        mostRecentHistoryTextView.setText(roll.toString());
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,8,0,0);
        mostRecentHistoryTextView.setLayoutParams(lp);
        mostRecentHistoryTextView.setTextSize(16);

        // Set up constraints for the new TextView and the one being pushed up
        // Should just be the currentTextView bottom to top of historyTextView
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rollHistoryConstraintLayout);
        constraintSet.connect(mostRecentHistoryTextView.getId(), ConstraintSet.BOTTOM,
              ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        if (currTextView != null){
//          constraintSet.connect(currTextView.getId(), ConstraintSet.BOTTOM,
//                historyTextView.getId(), ConstraintSet.TOP);
          constraintSet.connect(mostRecentHistoryTextView.getId(), ConstraintSet.TOP,
                currTextView.getId(), ConstraintSet.BOTTOM, 8);
          System.out.println("Bottom-most TextView: " + currTextView.getId());
          System.out.println("New bottom TextView: " + mostRecentHistoryTextView.getId());
        }
        else {
          // This would be the case if there were no TextViews present yet
          constraintSet.connect(mostRecentHistoryTextView.getId(), ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        }

        /*
        <TextView
                    android:id="@+id/history_line1"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginVertical="8dp"
                    android:text="@string/history"
                    android:textSize="16sp"
                    app:layout_constraintBottom_toBottomOf="@id/roll_history_layout" />
         */
      }
    });

    // set clear history OnClickListener
    clear_history_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view1) {
        ConstraintLayout rollHistoryConstraintLayout = view.findViewById(R.id.roll_history_layout);
        rollHistoryConstraintLayout.removeAllViews();
      }
    });


    return view;
  } // Close onCreateView


  /** BUTTON METHODS */

  // Sets fields on the buttons that aren't able to be done in xml due to using <include> tag
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
    MaterialButton[] diceButtons = new MaterialButton[]{
          d4_button, d6_button, d8_button, d10_button, d12_button, d20_button};

    final int[] dice_button_values = new int[]{
          RollerFragment.d4_VALUE, RollerFragment.d6_VALUE, RollerFragment.d8_VALUE,
          RollerFragment.d10_VALUE, RollerFragment.d12_VALUE, RollerFragment.d20_VALUE};

    int d4_button_text = R.string.button_d4;
    int d6_button_text = R.string.button_d6;
    int d8_button_text = R.string.button_d8;
    int d10_button_text = R.string.button_d10;
    int d12_button_text = R.string.button_d12;
    int d20_button_text = R.string.button_d20;
    final int[] dice_button_resource_ids = new int[]{
          d4_button_text, d6_button_text, d8_button_text,
          d10_button_text, d12_button_text, d20_button_text};

    String[] dice_button_strings = new String[]{};
    for (int i = 0; i < diceButtons.length; i++) {
      diceButtons[i].setText(dice_button_resource_ids[i]);
      setDieButtonOnClickListener(diceButtons[i], dice_button_values[i], view);
    }

  }

  // Dice buttons
  // sets the OnClickListener for each button
  public void setDieButtonOnClickListener(MaterialButton button, final int dieType, final View parentView){
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Die die = new Die(dieType);
        diceStack.push(die);
        roll.addDie(die);
        Collections.sort(roll.getDice());
        updateFormulaEditText((EditText) parentView.findViewById(R.id.formula_edit_text));
      }
    });
  }

  // Update formula based on Roll toString()
  public void updateFormulaEditText(EditText et) {
    formula = roll.formulaString();
    if (formula.equals("0")){
      formula = "";
    }
    et.setText(formula);
  }

  public void updateRollValueTextView(TextView tv) {
    String rollValue = String.valueOf(roll.getValue());
    if (rollValue.equals("0")){
      rollValue = "";
    }
    tv.setText(rollValue);
  }
}
