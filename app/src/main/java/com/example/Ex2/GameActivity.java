package com.example.Ex2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private ImageButton game_IMG_rightArrow;
    private ImageButton game_IMG_leftArrow;
    private ImageView[] game_IMG_mouses;
    private ImageView[][] game_IMG_cats;
    private ImageView[][] game_IMG_cheeses;
    private ImageView[] game_IMG_hearts;
    private TextView game_LBL_score;
    private Timer timer;
    private Bundle bundle = new Bundle();
    private int rows, cols, lives,score;
    public static boolean sensorsEnabled = true;

    private GameManager gameManager;
    private SaveScoreActivity saveScore = new SaveScoreActivity();

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        lives = getResources().getInteger(R.integer.LIVES);
        cols = getResources().getInteger(R.integer.LANES);
        rows = getResources().getInteger(R.integer.ROWS);



        findViews();
        initViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Toast toast = Toast.makeText(this, "Oh no!", Toast.LENGTH_SHORT);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.cat_scream);

        gameManager = new GameManager(
                game_IMG_hearts.length,
                score,
                rows,
                cols,
                cols/2,
                2,
                toast,
                vibrator,
                mp);

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
        game_LBL_score = findViewById(R.id.game_LBL_score);

        game_IMG_mouses = new ImageView[]{
                findViewById(R.id.game_IMG_mouse0),
                findViewById(R.id.game_IMG_mouse1),
                findViewById(R.id.game_IMG_mouse2),
                findViewById(R.id.game_IMG_mouse3),
                findViewById(R.id.game_IMG_mouse4),
        };

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_cats = new ImageView[rows][cols];
        game_IMG_cheeses = new ImageView[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String catsID = "game_IMG_cat" + i + j;
                String cheeseID = "game_IMG_cheese" + i + j;
                int resCatsID = getResources().getIdentifier(catsID, "id", getPackageName());
                int resCheeseID = getResources().getIdentifier(cheeseID, "id", getPackageName());
                game_IMG_cats[i][j] = findViewById(resCatsID);
                game_IMG_cheeses[i][j] = findViewById(resCheeseID);
                game_IMG_cats[i][j].setVisibility(View.INVISIBLE);
                game_IMG_cheeses[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    public int getScore() {
        return score;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer();
    }

    protected void onPause() {
        super.onPause();
        if (sensorsEnabled){
            sensorManager.unregisterListener(this);
        }
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

    protected void onResume() {
        super.onResume();
        if (sensorsEnabled) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }

    }

    private void startTimer() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateUI());
            }
        }, 0, MenuActivity.getInter());

    }


    private void updateUI() {
        gameManager.updateGame();
        if(gameManager.getLives()==0){
            startSaveScoreActivity();
        }
        if (gameManager.getLives() < lives)
            game_IMG_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);
        score = gameManager.getScore();
        game_LBL_score.setText(""+score);
        boolean[][] isCatVisible = gameManager.getIsCatVisible();
        boolean[][] isCheeseVisible = gameManager.getIsCheeseVisible();
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++){
                game_IMG_cats[i][j].setVisibility(isCatVisible[i][j] ? View.VISIBLE : View.INVISIBLE);
                game_IMG_cheeses[i][j].setVisibility(isCheeseVisible[i][j] ? View.VISIBLE : View.INVISIBLE);
            }
        }

    }


    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if(x>1 && x<2){
                updateMousePosition(-1);
            }
            if(x<-1 && x>-2){
                updateMousePosition(1);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void startSaveScoreActivity() {
        timer.cancel();
        Intent intent = new Intent(this, SaveScoreActivity.class);
        bundle.putInt("SCORE", score);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
        finish();
    }


}

