package com.example.exc1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ImageButton game_IMG_rightArrow;
    private ImageButton game_IMG_leftArrow;
    private ImageView[] game_IMG_mouses;
    private ImageView[][] game_IMG_cats;
    private ImageView[] game_IMG_hearts;
    private Timer timer;
    private GameManager gameManager;

    private int rows, cols, lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lives = getResources().getInteger(R.integer.LIVES);
        cols = getResources().getInteger(R.integer.LANES);
        rows = getResources().getInteger(R.integer.ROWS);


        findViews();
        initViews();

        Toast toast = Toast.makeText(this, "Oh no!", Toast.LENGTH_SHORT);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        gameManager = new GameManager(
                game_IMG_hearts.length,
                rows,
                cols,
                cols / 2,
                2,
                toast,
                vibrator);

        startTimer();


    }

    private void initViews() {

        game_IMG_leftArrow.setOnClickListener(view -> updateMousePosition(-1));

        game_IMG_rightArrow.setOnClickListener(view -> updateMousePosition(1));
    }

    private void updateMousePosition(int direction) {
        game_IMG_mouses[gameManager.getMouseCol()].setVisibility(View.INVISIBLE);
        gameManager.moveMouse(direction);
        game_IMG_mouses[gameManager.getMouseCol()].setVisibility(View.VISIBLE);

    }

    private void findViews() {
        game_IMG_rightArrow = findViewById(R.id.game_IMG_rightArrow);
        game_IMG_leftArrow = findViewById(R.id.game_IMG_leftArrow);

        game_IMG_mouses = new ImageView[]{
                findViewById(R.id.game_IMG_mouse1),
                findViewById(R.id.game_IMG_mouse2),
                findViewById(R.id.game_IMG_mouse3)
        };

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_cats = new ImageView[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String catsID = "game_IMG_cat" + i + j;
                int resID = getResources().getIdentifier(catsID, "id", getPackageName());
                game_IMG_cats[i][j] = findViewById(resID);
                game_IMG_cats[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void startTimer() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateUI());
            }
        }, 0, getResources().getInteger(R.integer.INTERVAL));

    }


    private void updateUI() {
        gameManager.updateGame();
        if (gameManager.getLives() < lives)
            game_IMG_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);
        boolean[][] isCatVisible = gameManager.getIsCatVisible();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                game_IMG_cats[i][j].setVisibility(isCatVisible[i][j] ? View.VISIBLE : View.INVISIBLE);
    }
}

