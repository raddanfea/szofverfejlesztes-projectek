package hu.szoftverprojekt.holdemfree.controller;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DeckScreen extends AppCompatActivity {

    private Button back_button, gameButton, settingsButton, resetButton, setDefault, setWooden, setIron, setGolden, setDiamond,
            goldenInfo, diamondInfo, woodenInfo, ironInfo;
    private ImageView defaultSkin, wooden, iron, golden, diamond;
    private Button buttons[];
    private TextView winCount;
    private AppData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_screen);

        data = new AppData(this);


        defaultSkin = (ImageView) findViewById(R.id.defaultSkin);
        wooden = (ImageView) findViewById(R.id.wooden);
        iron = (ImageView) findViewById(R.id.iron);
        golden = (ImageView) findViewById(R.id.golden);
        diamond = (ImageView) findViewById(R.id.diamond);

        winCount = (TextView) findViewById(R.id.winCount);
        defaultSkin.setImageResource(R.drawable.k1);
        wooden.setImageResource(R.drawable.wooden_k1);
        iron.setImageResource(R.drawable.leafiron_k1);
        golden.setImageResource(R.drawable.golden_k1);
        diamond.setImageResource(R.drawable.diamond_k1);

        goldenInfo = (Button)findViewById(R.id.goldenInfo);
        diamondInfo = (Button)findViewById(R.id.diamondInfo);
        woodenInfo = (Button)findViewById(R.id.woodenInfo);
        ironInfo = (Button)findViewById(R.id.ironInfo);
        setDefault = (Button) findViewById(R.id.setDefault);
        gameButton = (Button) findViewById(R.id.gameButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        setWooden = (Button) findViewById(R.id.setWooden);
        setIron = (Button) findViewById(R.id.setIron);
        setGolden = (Button) findViewById(R.id.setGolden);
        setDiamond = (Button) findViewById(R.id.setDiamond);
        buttons = new Button[]{setDefault, setWooden, setIron, setGolden, setDiamond};
        back_button = (Button) findViewById(R.id.back_button);
        resetButton = (Button)findViewById(R.id.resetButton);


        //KÁRTYÁK TESZTELÉSÉHEZ
        //DE RESET GOMBHOZ KI KELL VENNI
        /*
        data.save("skinId", 0);
        data.save("ironEnabled", false);
        data.save("wincount", 27);
        */
        winCount.setText("Number of wins: " + data.getInt("wincount"));



        if (data.getInt("wincount") >= 10) {
            setWooden.setEnabled(true);
            woodenInfo.setVisibility(View.GONE);
        } else {
            setWooden.setEnabled(false);
            woodenInfo.setVisibility(View.VISIBLE);
        }

        if (data.getBoolean("ironEnabled")) {
            setIron.setEnabled(true);
            ironInfo.setVisibility(View.GONE);
        } else {
            setIron.setEnabled(false);
            ironInfo.setVisibility(View.VISIBLE);
        }

        if (data.getInt("wincount") >= 25) {
            setGolden.setEnabled(true);
            goldenInfo.setVisibility(View.GONE);
        } else {
            setGolden.setEnabled(false);
            goldenInfo.setVisibility(View.VISIBLE);
        }

        if (data.getInt("wincount") >= 50) {
            setDiamond.setEnabled(true);
            diamondInfo.setVisibility(View.GONE);
        } else {
            setDiamond.setEnabled(false);
            diamondInfo.setVisibility(View.VISIBLE);
        }

        switch (data.getInt("skinId")) {
            case 0:
                setDefault.setEnabled(false);
                setDefault.setText("In use");
                break;
            case 1:
                setWooden.setEnabled(false);
                setWooden.setText("In use");
                break;
            case 2:
                setIron.setEnabled(false);
                setIron.setText("In use");
                break;
            case 3:
                setGolden.setEnabled(false);
                setGolden.setText("In use");
                break;
            case 4:
                setDiamond.setEnabled(false);
                setDiamond.setText("In use");
                break;
        }

        setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("skinId", 0);
                changeButtons(0);
            }
        });

        setWooden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("skinId", 1);
                changeButtons(1);
            }
        });

        setIron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("skinId", 2);
                changeButtons(2);
            }
        });

        setGolden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("skinId", 3);
                changeButtons(3);
            }
        });

        setDiamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("skinId", 4);
                changeButtons(4);
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBackScreen();
            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        woodenInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "You have to win "+(10-data.getInt("wincount"))+" more games to unlock this skin!", Toast.LENGTH_LONG).show();
            }
        });

        ironInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "You have to win 5 games in a row to unlock this skin!", Toast.LENGTH_LONG).show();
            }
        });

        goldenInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "You have to win "+(25-data.getInt("wincount"))+" more games to unlock this skin!", Toast.LENGTH_LONG).show();
            }
        });

        diamondInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "You have to win "+(50-data.getInt("wincount"))+" more games to unlock this skin!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeButtons(int buttonId) {
        for(int i=0; i < buttons.length; i++) {
            if(data.getInt("wincount")>=50 && data.getBoolean("ironEnabled")) {
                if(i==buttonId) {
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if(data.getInt("wincount")>=50 && !data.getBoolean("ironEnabled")) {
                if(i == 2) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId) {
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if(data.getInt("wincount")>=25 && data.getInt("wincount ")<50 && data.getBoolean("ironEnabled")) {
                if(i == 4) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId) {
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if (data.getInt("wincount")>=25 && data.getInt("wincount ")<50 && !data.getBoolean("ironEnabled")) {
                if(i == 2 || i == 4) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId) {
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if (data.getInt("wincount")>=10 && data.getInt("wincount")<25 && data.getBoolean("ironEnabled")) {
                if(i == 3 || i == 4) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId){
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if (data.getInt("wincount")>=10 && data.getInt("wincount")<25 && !data.getBoolean("ironEnabled")) {
                if(i == 2 || i == 3 || i == 4 ) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId){
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if(data.getInt("wincount")<10 && data.getBoolean("ironEnabled")) {
                if(i == 1 || i == 3 || i == 4) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId){
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }
            else
            if(data.getInt("wincount")<10 && !data.getBoolean("ironEnabled")) {
                if(i == 1 || i == 2 || i == 3 || i == 4) {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(false);
                }
                else
                if (i == buttonId){
                    buttons[i].setText("In use");
                    buttons[i].setEnabled(false);
                }
                else {
                    buttons[i].setText("Set");
                    buttons[i].setEnabled(true);
                }
            }

        }
    }

    private void resetGame() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("WARNING!").setMessage("Do you really want to reset the game?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.save("wincount", 0);
                        data.save("skinId", 0);
                        data.save("ironEnabled", false);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void openBackScreen() {
        finish();
    }

    private void openSettings() {
        Intent target_settings = new Intent(this, SettingsScreen.class);
        startActivity(target_settings);
    }

    private void openGame() {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }

}
