package com.example.exc1;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class GameManager {

    private final int ticksToNewCat;
    private int ticks;
    private int lives;
    private int mouseCol;

    private boolean[][] isCatVisible;
    private int rows;
    private int cols;
    private Vibrator vibrator;
    private Toast toast;

    Random rand = new Random();

    public GameManager(int lives, int rows, int cols, int mouseStartingCol, int ticksToNewCat, Toast toast, Vibrator vibrator) {
        this.ticksToNewCat = ticksToNewCat;
        ticks = 0;
        mouseCol = mouseStartingCol;
        this.rows = rows;
        this.cols = cols;
        isCatVisible = new boolean[rows][cols];
        this.lives = lives;
        this.vibrator = vibrator;
        this.toast = toast;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                isCatVisible[i][j] = false;
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

    public boolean[][] getIsCatVisible() {
        return isCatVisible;
    }

    public void checkCollision() {
        if (isCatVisible[rows - 1][mouseCol]) {
            if (lives > 0)
                lives--;
            toast.show();
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void addCat() {
        isCatVisible[0][rand.nextInt(cols)] = true;
    }

    private void updateCats() {
        for (int i = rows - 1; i > 0; i--)
            for (int j = 0; j < cols; j++)
                isCatVisible[i][j] = isCatVisible[i - 1][j];

        for (int i = 0; i < cols; i++) {
            isCatVisible[0][i] = false;
        }
    }

    public void updateGame() {
        checkCollision();
        updateCats();
        ticks++;
        if (ticks == ticksToNewCat) {
            ticks = 0;
            addCat();
        }

        Log.d("isCatVisible", Arrays.deepToString(isCatVisible));
    }


    public int getLives() {
        return lives;
    }


}
