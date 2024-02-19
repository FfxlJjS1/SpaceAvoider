package com.project.spaceavoider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.spaceavoider.activities.MainMenu;
import com.project.spaceavoider.R;
import com.project.spaceavoider.gamesetting.GameState;
import com.project.spaceavoider.gamesetting.GameView;

public class GameDialogActivity extends AppCompatActivity {
    GameState localGameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_game_over);

        localGameState = (GameState) getIntent().getSerializableExtra("gameState");

        TextView textView = findViewById(R.id.Text),
                textViewInfo = findViewById(R.id.TextViewInfo);
        Button banRestart = findViewById(R.id.dialog_btn_restart);

        if (localGameState == GameState.gamePaused) {
            textView.setText("Пауза");
            textViewInfo.setText("Вы набрали " + GameView.points + " очков");
            banRestart.setText("Продолжить");
        } else if (GameView.points > GameView.theHighlyPoints) {
            textView.setText("Вы проиграли");
            textViewInfo.setText("Вы побили рекорд!\nПрошлый рекорд: " + GameView.theHighlyPoints  + "\nНовый рекорд: " + GameView.points);
            textView.setText("Вы побили рекорд!!! " + GameView.theHighlyPoints + " на " + GameView.points);
            GameView.theHighlyPoints = GameView.points;
        } else
            textView.setText("Вы набрали " + GameView.points + " очков");
    }

    public void onBtnRestart(View view) {
        if (localGameState == GameState.gamePaused)
            GameView.gameState = GameState.gameResume;
        else
            GameView.gameState = GameState.gameRunning;

        finish();
    }

    public void onBtnMenu(View view) {
        GameView.gameState = GameState.gameOver;

        startActivity(new Intent(this, MainMenu.class));
        finish();
    }

    public void onBtnMagazine(View view) {
        Intent intent = new Intent(this, MagazineActivity.class);
        startActivity(intent);
    }
}