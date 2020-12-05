package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;
import hu.szoftverprojekt.holdemfree.model.Bot;
import hu.szoftverprojekt.holdemfree.model.GameLogic;
import hu.szoftverprojekt.holdemfree.model.MediaVolumeEvent;
import hu.szoftverprojekt.holdemfree.model.PlaySound;
import hu.szoftverprojekt.holdemfree.model.Player;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdater;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdaterEventArgs;
import hu.szoftverprojekt.holdemfree.model.actions.Actions;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

public class GameScreen extends AppCompatActivity {
    
    private static final String TAG = "testtest";
    
    private final String[] skinNames = {"", "wooden_", "leafiron_", "golden_", "diamond_"};

    private int winstreak;
    private ConstraintLayout constraintLayout;
    private boolean canInteract;
    private GameLogic game;
    private ImageView[] cardsOnBoard = new ImageView[5];
    private ImageView[] playerCards = new ImageView[2];
    private TextView aiPot;
    private TextView currentPot;
    private TextView playerPot;
    private Player player;
    private Button foldButton;
    private Button holdButton;
    private Button raiseButton;
    private AppData data;
    private Switch muteSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        data = new AppData(this);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        muteSwitch = (Switch)findViewById(R.id.muteSwitch);
        aiPot = findViewById(R.id.ai_pot);
        currentPot = findViewById(R.id.current_pot);
        playerPot = findViewById(R.id.player_pot);
        foldButton = findViewById(R.id.fold);
        holdButton = findViewById(R.id.hold);
        raiseButton = findViewById(R.id.raise);

        switch (data.getInt("bgId")) {
            case 0:
                constraintLayout.setBackgroundResource(R.drawable.bg);
                break;
            case 1:
                constraintLayout.setBackgroundResource(R.drawable.bg2);
                break;
            case 2:
                constraintLayout.setBackgroundResource(R.drawable.bg3);
                break;
            case 3:
                constraintLayout.setBackgroundResource(R.drawable.bg4);
                break;
            case 4:
                constraintLayout.setBackgroundResource(R.drawable.bg5);
                break;

            default:
                constraintLayout.setBackgroundResource(R.drawable.bg);
        }

        if(!data.getBoolean("playmusic")) {
            muteSwitch.setChecked(true);
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        else {
            muteSwitch.setChecked(false);
            startService(new Intent(GameScreen.this, PlaySound.class));
        }
        muteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchSound();
            }
        });

        for (int i = 0; i < 5; i++){
            cardsOnBoard[i] = findViewById(getResId("card"+(i+1), R.id.class));
        }
        
        for (int i = 0; i < 2; i++){
            playerCards[i] = findViewById(getResId("player_card"+(i+1), R.id.class));
//            playerCards[i].setColorFilter(0xB3FFD700, PorterDuff.Mode.SRC_ATOP);
        }
        
        /////////////////////////////// DEBUG /////////////////////////////////
        debugBindBotCards();
        ///////////////////////////////////////////////////////////////////////
        
        
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.HOLD);
                game.nextTurn();
            }
        });
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.FOLD);
                game.nextTurn();
            }
        });
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.RAISE_BY(50));
                game.nextTurn();
            }
        });
        
        winstreak = 0;
        initGame();
        game.start();


    }
    
    private void initGame() {
        player = new Player("asd", 500);
        game = new GameLogic(data.getInt("difficulty"), player);
        game.onChange = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onChange ... " + gameData.toString());
    
                if(gameData.board.size() == 0) {
                    // clear images
                    for (int i = 0; i < 5; i++) {
                        cardsOnBoard[i].setImageResource(R.drawable.cb);
                    }
                } else {
                    for (int i = 0; i < gameData.board.size(); i++) {
                        cardsOnBoard[i].setImageResource(
                            getResId(skinNames[data.getInt("skinId")] + "k"+gameData.board.get(i).getId(), R.drawable.class));
                    }
                }
                
                for (int i = 0; i < 2; i++) {
                    playerCards[i].setImageResource(
                        getResId(skinNames[data.getInt("skinId")] + "k"+player.getHand().get(i).getId(), R.drawable.class));
                }
                /////////////////////////////// DEBUG /////////////////////////////////

                Integer text = GameLogic.calcScoreOfHand(gameData.board, player.getHand());
                log("cumValue: " + String.valueOf(text));

                debugSetImageForBotCards(gameData);
                ///////////////////////////////////////////////////////////////////////
                
                aiPot.setText("ai pot: " + Integer.toString(gameData.players.get(1).getMoney()));
                currentPot.setText(Integer.toString(gameData.moneyOnBoard));
                playerPot.setText(Integer.toString(player.getMoney()));

                // this will start the next turn
                if (gameData.roundsEnded < 4)
                    handlePlayers(gameData);
            }
        };
        game.onGameOver = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onGameOver");
                if (gameData.winnerIndex == 0) {
                    winstreak += 1;
                    data.save("wincount", data.getInt("wincount") +1 );
                    checkForUnlocks();
                } else {
                    winstreak = 0;
                }
                
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // don't start new game if only one player has money
                int playersLeft = 0;
                for (Player player : gameData.players) {
                    if (player.getMoney() >= GameLogic.calcMinBet()) {
                        playersLeft++;
                    }
                }
    
                if (playersLeft > 1)
                    game.start();
            }
        };
        
    }
    
    private void handlePlayers(ScreenUpdaterEventArgs e) {
        if (game.getCurrentPlayerIndex() != 0) {
            log("///////////////////////////////////////////////\nBots turn ......................");
            canInteract = false;
            ((Bot) game.getCurrentPlayer()).think(e.board);
            game.nextTurn();
        } else {
            log("///////////////////////////////////////////////\nUsers turn ......................");
            canInteract = true;
        }
    }
    
    private void checkForUnlocks() {
        if (winstreak >= 5) {
            data.save("ironEnabled", true);
        }
    }
    
    public static int getResId(String resName, Class<?> c) {
        
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    private void log(String s) {
        System.out.println(s);
    }
    
    
    // --------------------- DEBUG ---------------------------
    private ImageView[] debugBotCards = new ImageView[2];
    
    private void debugBindBotCards() {
        for (int i = 0; i < 2; i++)
            debugBotCards[i] = findViewById(getResId("debug_bot_card_"+(i+1), R.id.class));
    }
    
    private void debugSetImageForBotCards(ScreenUpdaterEventArgs e) {
        for (int i = 0; i < 2; i++) {
            debugBotCards[i].setImageResource(
                getResId(skinNames[data.getInt("skinId")] + "k"+e.players.get(1).getHand().get(i).getId(), R.drawable.class));
        }
    }

    private void switchSound() {
        if(muteSwitch.isChecked()) {
            data.save("playmusic", false);
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        else {
            data.save("playmusic", true);
            startService(new Intent(GameScreen.this, PlaySound.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(data.getBoolean("playmusic")) {
            try {
                startService(new Intent(GameScreen.this, PlaySound.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}