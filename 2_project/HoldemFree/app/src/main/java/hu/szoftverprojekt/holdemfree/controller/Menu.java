package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hu.szoftverprojekt.holdemfree.R;

public class Menu extends AppCompatActivity {

    private long BackPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button start_game_button = (Button) findViewById(R.id.start_game);
        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameScreen();
            }
        });

        /* Máté fogja létrhozni a DeckScrrent és SegttingScreent
        Button change_deck_button = (Button) findViewById(R.id.change_deck);
        change_deck_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeckScreen();
            }
        });

        Button settings_button = (Button) findViewById(R.id.settings);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsScreen();
            }
        });
        */

        Button exit_button = (Button) findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame();
            }
        });

    }

    private void openGameScreen() {
        Intent target_start_game = new Intent(this, GameScreen.class);
        startActivity(target_start_game);
    }

   /* amint kész a DeckScreen és SettingScreen használható
   private void openDeckScreen () {
        Intent target_change_deck = new Intent(this, DeckScreen.class);
        startActivity(target_change_deck);
    }

   private void openSettingsScreen () {
       Intent target_settings = new Intent(this, SettingsScreen.class);
       startActivity(target_settings);
   }
   */

    private void exitGame () {

        if (BackPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();;
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Exit again to close the game", Toast.LENGTH_LONG);
            backToast.show();
        }

        BackPressedTime = System.currentTimeMillis();
    }

}