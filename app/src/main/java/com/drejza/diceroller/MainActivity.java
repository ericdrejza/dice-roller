package com.drejza.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Roll roll;
    private Stack<Die> formulaHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.roll = new Roll();
    }

    public void toList(View view){
        Intent intent = new Intent(this, FormulaListActivity.class);
        startActivity(intent);
    }


    // sends a toast
    public void toastHello(View view){
        Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();

    }

    public void update(){
        String formula = "";
        for (int i = 0; i < roll.getDice().length; i++){
            // iterate through the dice and add to the formula
        }
    }


    public void changeDieType(View view){
        Button clicked_btn = findViewById(view.getId());


    }

}
