package com.drejza.diceroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Roll roll;
    private Stack<Die> formulaHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                  new RollerFragment()).commit();
        }

        this.roll = new Roll();
    }

   private BottomNavigationView.OnNavigationItemSelectedListener navListener =
          new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  Fragment selectedFragment = null;

                  switch (item.getItemId()) {
                      case R.id.nav_roll:
                          selectedFragment = new RollerFragment();
                          break;
                      case R.id.nav_list:
                          selectedFragment = new ListFragment();
                          break;
                      case R.id.nav_settings:
                          selectedFragment = new SettingsFragment();
                      break;
                  }

                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                  return true;
              }
          };

    public void toList(View view){
        Intent intent = new Intent(this, FormulaListActivity.class);
        startActivity(intent);
    }


    // sends a toast
    public void toastHello(View view){
        Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();

    }

    public void changeDieType(View view){
        Button clicked_btn = findViewById(view.getId());


    }

}
