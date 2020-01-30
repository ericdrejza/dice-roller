package com.drejza.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Roll roll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.roll = new Roll();

    }

    protected void toLists(View view){
        //Intent intent = new Intent(this, .class);


        //startActivity(intent);
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
