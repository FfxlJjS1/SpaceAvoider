package com.project.spaceavoider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.spaceavoider.R;
import com.project.spaceavoider.gamesetting.GameView;

public class MagazineActivity extends AppCompatActivity {
    public static int coins = 0;
    public static int plusMaxStrengthCount = 0,
                    minesMaxMissileTimeOut = 0,
                    plusMissileStrength = 0,
                    plusSpeed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);
    }

    public void onBtn(View view) {
        switch (view.getId()) {
            case R.id.maxHeightPlus:
                if (coins >= 0)
                    plusMaxStrengthCount++;

                ((TextView)findViewById(R.id.textView2)).setText("+" + plusMaxStrengthCount);
                break;
            case R.id.maxMissileTimeOutMines:
                if (coins >= 0)
                    minesMaxMissileTimeOut--;

                ((TextView)findViewById(R.id.textView4)).setText(String.valueOf(minesMaxMissileTimeOut));
                break;
            case R.id.strenghtMissilePlus:
                if (coins >= 0)
                    plusMissileStrength++;

                ((TextView)findViewById(R.id.textView6)).setText("+" + plusMissileStrength);
                break;
            case R.id.speedPlus:
                if (coins >= 0)
                    plusSpeed++;

                ((TextView)findViewById(R.id.textView8)).setText("+" + plusSpeed);
                break;
        }


    }
}