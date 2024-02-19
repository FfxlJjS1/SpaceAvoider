package com.project.spaceavoider.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.spaceavoider.R;
import com.project.spaceavoider.gamesetting.GameState;
import com.project.spaceavoider.gamesetting.GameView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    public static boolean isLeftPressed = false;
    public static boolean isRightPressed = false;
    public static boolean isCenterPressed = false;
    public static TextView PointsTextView, LevelTextView;
    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.leftBtn)).setOnTouchListener(this);
        ((Button) findViewById(R.id.centerBtn)).setOnTouchListener(this);
        ((Button) findViewById(R.id.rightBtn)).setOnTouchListener(this);
        PointsTextView = findViewById(R.id.textViewPoints);
        LevelTextView = findViewById(R.id.textViewLevel);

        FrameLayout spaceLayout = (FrameLayout) findViewById(R.id.spaceLayout);
        gameView = new GameView(this);

        spaceLayout.addView(gameView, 0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.leftBtn:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.centerBtn:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isCenterPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isCenterPressed = false;
                        break;
                }
                break;
            case R.id.rightBtn:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
        }

        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 21:
                isLeftPressed = false;
                break;
            case 22:
                isRightPressed = false;
                break;
            case 19:
                isCenterPressed = false;
                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                isLeftPressed = true;
                break;
            case 22:
                isRightPressed = true;
                break;
            case 19:
                isCenterPressed = true;
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void Paused(View view) {
        GameView.gameState = GameState.gamePaused;
    }
}