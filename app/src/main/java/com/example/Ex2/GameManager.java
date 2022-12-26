package com.example.Ex2;

import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Random;

public class GameManager {

    private final int ticksToNewCat;
    private final int ticksToNewCheese=4;
    private int ticks;
    private int lives;
    private int score;
    private int mouseCol;

    private boolean[][] isCatVisible;
    private boolean[][] isCheeseVisible;
    private int rows;
    private int cols;
    private Vibrator vibrator;
    private Toast toast;
    private MediaPlayer mp;

    Random rand = new Random();

    public GameManager(int lives, int score, int rows, int cols, int mouseStartingCol, int ticksToNewCat, Toast toast, Vibrator vibrator, MediaPlayer mp) {
        this.ticksToNewCat = ticksToNewCat;
        ticks = 0;
        mouseCol = mouseStartingCol;
        this.rows = rows;
        this.cols = cols;
        isCatVisible = new boolean[rows][cols];
        isCheeseVisible = new boolean[rows][cols];
        this.lives = lives;
        this.score = score;
        this.vibrator = vibrator;
        this.toast = toast;
        this.mp = mp;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                isCatVisible[i][j] = false;
                isCheeseVisible[i][j] = false;
            }
        }
    }

    public void moveMouse(int direction) {
        if (direction < 0 && mouseCol != 0) //move left
            mouseCol--;
        if (direction > 0 && mouseCol != cols - 1) //move right
            mouseCol++;
    }

    public int getMouseCol() {
        return mouseCol;
    }

    public int getScore() {
        return score;
    }

    public boolean[][] getIsCatVisible() {
        return isCatVisible;
    }

    public boolean[][] getIsCheeseVisible() {
        return isCheeseVisible;
    }


    public void checkCollision() {
        if (isCatVisible[rows - 1][mouseCol]) {
            if (lives > 0)
                lives--;
            mp.start();
            toast.show();
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        if(isCheeseVisible[rows-1][mouseCol]){
            score +=10;
        }
        //if(score%100==0)
            //lives++;
    }

    private void addCat() {

        isCatVisible[0][rand.nextInt(cols)] = true;
    }

    private void addCheese(){
        int res = rand.nextInt(cols);
        if(isCatVisible[0][res]==false)
            isCheeseVisible[0][res] = true;
    }

    private void updateCatsAndCheeses() {
        for (int i = rows - 1; i > 0; i--)
        {
            for (int j = 0; j < cols; j++){
                isCatVisible[i][j] = isCatVisible[i - 1][j];
                isCheeseVisible[i][j] = isCheeseVisible[i - 1][j];
            }
        }

        for (int i = 0; i < cols; i++) {
            isCatVisible[0][i] = false;
            isCheeseVisible[0][i]=false;
        }
    }

    public void updateGame() {
        checkCollision();
        updateCatsAndCheeses();
        ticks++;
        if (ticks == ticksToNewCat) {
            addCat();
        }
        if(ticks == ticksToNewCheese){
            ticks = 0;
            addCat();
            addCheese();
        }

    }


    public int getLives() {
        return lives;
    }


}
