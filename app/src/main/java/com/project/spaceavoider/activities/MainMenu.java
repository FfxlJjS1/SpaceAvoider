package com.project.spaceavoider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.spaceavoider.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onBtnToGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onBtnToRank(View view) {
    }

    public void onBtnToSetting(View view) {
    }

    public void onBtnExit(View view) {
        finishAffinity();
    }

    public void onBtnMagazine(View view) {
        Intent intent = new Intent(this, MagazineActivity.class);
        startActivity(intent);
    }
}