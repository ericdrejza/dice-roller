package com.drejza.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FormulaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_list);
    }

    public void toRoller(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
