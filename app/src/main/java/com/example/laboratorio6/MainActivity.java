package com.example.laboratorio6;

// Import packages for android
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import java.util.Random;
import android.os.Handler;
import android.widget.TextView;

import org.w3c.dom.Text;

// MainActivity class
public class MainActivity extends AppCompatActivity {
    public Button alarmButton, arcadeButton, dogButton, gooseButton, coinButton, whooshButton, startButton;
    public MediaPlayer arcadeSound, dogSound, gooseSound, whooshSound, coinSound, alarmSound;
    public int sequence[] = new int[1000];
    public int currentPos;
    public int points;
    public int userSequence[] = new int[1000];
    public int L1, L2, L3, L4, L5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare textbox message and initialize
        TextView message = (TextView) this.findViewById(R.id.message);
        message.setText("Presione inicio para jugar.");

        // Declare "leader" ints
        L1 = 0;
        L2 = 0;
        L3 = 0;
        L4 = 0;
        L5 = 0;

        // Declare ALL buttons preemptively
        alarmButton = this.findViewById(R.id.alarmButton);
        arcadeButton = this.findViewById(R.id.arcadeButton);
        dogButton =  this.findViewById(R.id.dogButton);
        gooseButton = this.findViewById(R.id.gooseButton);
        coinButton = this.findViewById(R.id.coinButton);
        whooshButton = this.findViewById(R.id.whooshButton);
        startButton = this.findViewById(R.id.startButton);

        // Declare button visibility as off preemptively
        alarmButton.setVisibility(View.GONE);
        arcadeButton.setVisibility(View.GONE);
        dogButton.setVisibility(View.GONE);
        gooseButton.setVisibility(View.GONE);
        coinButton.setVisibility(View.GONE);
        whooshButton.setVisibility(View.GONE);

        // On startup, use color presets
        alarmButton.setBackgroundColor(getResources().getColor(R.color.DarkGoldenrod));
        arcadeButton.setBackgroundColor(getResources().getColor(R.color.DarkViolet));
        dogButton.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
        gooseButton.setBackgroundColor(getResources().getColor(R.color.SlateGray));
        coinButton.setBackgroundColor(getResources().getColor(R.color.Gold));
        whooshButton.setBackgroundColor(getResources().getColor(R.color.Plum));
        startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));

        // Start button:
        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Declare i variable, used in for-loops
                int i = 0;

                // Declare sequence on start button click
                for (i = 0; i < 1000; i++) {
                    sequence[i] = (int) (Math.random() * 6) + 1;
                }
                currentPos = 0;
                points = 0;
                // Set all buttons
                alarmButton.setBackgroundColor(getResources().getColor(R.color.DarkGoldenrod));
                arcadeButton.setBackgroundColor(getResources().getColor(R.color.DarkViolet));
                dogButton.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                gooseButton.setBackgroundColor(getResources().getColor(R.color.SlateGray));
                coinButton.setBackgroundColor(getResources().getColor(R.color.Gold));
                whooshButton.setBackgroundColor(getResources().getColor(R.color.Plum));
                startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                startButton.setEnabled(false);
                alarmButton.setVisibility(View.VISIBLE);
                arcadeButton.setVisibility(View.VISIBLE);
                dogButton.setVisibility(View.VISIBLE);
                gooseButton.setVisibility(View.VISIBLE);
                coinButton.setVisibility(View.VISIBLE);
                whooshButton.setVisibility(View.VISIBLE);
                // Set TextViews
                message.setText("¡Ponga atención!");
                // Start button delay handler
                Handler handlerA = new Handler();
                handlerA.postDelayed(new Runnable() {
                    public void run() {
                        // set start button
                        startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                    }
                }, 1000);
                // Print first one
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        button_show(sequence[0]);
                        startButton.setEnabled(true);
                        message.setText("Puntos: " + points + "\t\tRepita el patrón");
                    }
                }, 1500);

            }
        });

        // Pattern-Button onClick Listeners:

        // Arcade Sound:
        arcadeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(1);
                userSequence[currentPos] = 1;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // Dog sound:
        dogButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(4);
                userSequence[currentPos] = 4;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // Goose sound:
        gooseButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(3);
                userSequence[currentPos] = 3;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // Coin sound:
        coinButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(5);
                userSequence[currentPos] = 5;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // Whoosh sound:
        whooshButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(6);
                userSequence[currentPos] = 6;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // Alarm sound:
        alarmButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                button_show(2);
                userSequence[currentPos] = 2;
                currentPos = currentPos + 1;
                // Time to check sequence
                Handler handler = new Handler();
                int fail = 0;
                if (currentPos == (points + 1) ){
                    currentPos = 0;
                    points = points + 1;
                    for (int i = 0; i < points; i++)
                        if (userSequence[i] != sequence[i]) {
                            fail = 1;
                        }
                    // Outside for
                    if (fail == 0) {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Green));
                        message.setText("¡Felicidades, lo lograste!");
                        for (int i = 0; i <= points; i++) {
                            int inner = i;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    message.setText("¡Ponga atención!");
                                    startButton.setBackgroundColor(getResources().getColor(R.color.Goldenrod));
                                    button_show(sequence[inner]);
                                }

                            }, 1500*(i+1));
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startButton.setBackgroundColor(getResources().getColor(R.color.SandyBrown));
                                message.setText("Puntos: " + points + "\t\tRepita el patrón");
                            }
                        }, 1500*(points+2));
                    }
                    else {
                        startButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        // Leaderboard values
                        if (points-1 > L1){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = L1;
                            L1 = points - 1;
                        }
                        else if (points-1 > L2){
                            L5 = L4;
                            L4 = L3;
                            L3 = L2;
                            L2 = points - 1;
                        }
                        else if (points-1 > L3){
                            L5 = L4;
                            L4 = L3;
                            L3 = points - 1;
                        }
                        else if (points-1 > L4){
                            L5 = L4;
                            L4 = points - 1;
                        }
                        else if (points-1 > L5){
                            L5 = points - 1;
                        }
                        // Print Leaderboard points values
                        message.setText("Puntos: " + (points-1) + "\nFalló, lo siento mucho. Intente de nuevo.\n\n\n\n\n\n\n\nLeaderboard: \n" +
                                "1. " + L1 + " puntos \n" +
                                "2. " + L2 + " puntos \n" +
                                "3. " + L3 + " puntos \n" +
                                "4. " + L4 + " puntos \n" +
                                "5. " + L5 + " puntos \n");
                        // Declare button visibility as off
                        alarmButton.setVisibility(View.GONE);
                        arcadeButton.setVisibility(View.GONE);
                        dogButton.setVisibility(View.GONE);
                        gooseButton.setVisibility(View.GONE);
                        coinButton.setVisibility(View.GONE);
                        whooshButton.setVisibility(View.GONE);
                        // Reset points
                        points = 0;
                    }
                }
            }
        });

        // OUTSIDE INITIALIZE:
    }

    // Button show method
    public void button_show(int num) {
        // Declare ALL buttons preemptively
        alarmButton = this.findViewById(R.id.alarmButton);
        arcadeButton = this.findViewById(R.id.arcadeButton);
        dogButton =  this.findViewById(R.id.dogButton);
        gooseButton = this.findViewById(R.id.gooseButton);
        coinButton = this.findViewById(R.id.coinButton);
        whooshButton = this.findViewById(R.id.whooshButton);
        startButton = this.findViewById(R.id.startButton);

        // Multiples ifs
        if (num == 1) {
            final MediaPlayer arcadeSound = MediaPlayer.create(this, R.raw.arcade);
            arcadeSound.start();
            arcadeButton.setBackgroundColor(getResources().getColor(R.color.Lavender));
            arcadeButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                public void run() {
                    arcadeSound.reset();
                    arcadeButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                    arcadeButton.setBackgroundColor(getResources().getColor(R.color.DarkViolet));
                }
            }, 1500);
        }
        else if (num == 2) {
            final MediaPlayer alarmSound = MediaPlayer.create(this, R.raw.alarm);
            alarmSound.start();
            alarmButton.setBackgroundColor(getResources().getColor(R.color.Tan));
            alarmButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                public void run() {
                    alarmButton.setBackgroundColor(getResources().getColor(R.color.DarkGoldenrod));
                    alarmButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                }
            }, 1500);
        }
        else if (num == 3) {
            final MediaPlayer gooseSound = MediaPlayer.create(this, R.raw.geese);
            gooseSound.start();
            gooseButton.setBackgroundColor(getResources().getColor(R.color.Silver));
            gooseButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                public void run() {
                    gooseButton.setBackgroundColor(getResources().getColor(R.color.SlateGray));
                    gooseButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                }
            }, 1500);
        }
        else if (num == 4){
            final MediaPlayer dogSound = MediaPlayer.create(this, R.raw.dog);
            dogSound.start();
            dogButton.setBackgroundColor(getResources().getColor(R.color.RosyBrown));
            dogButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {
                public void run() {
                    dogButton.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                    dogButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                }
            }, 1500);
        }
        else if (num == 5) {
            final MediaPlayer coinSound = MediaPlayer.create(this, R.raw.coinwin);
            coinSound.start();
            coinButton.setBackgroundColor(getResources().getColor(R.color.Wheat));
            coinButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler5 = new Handler();
            handler5.postDelayed(new Runnable() {
                public void run() {
                    coinButton.setBackgroundColor(getResources().getColor(R.color.Gold));
                    coinButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                }
            }, 1500);
        }
        else if (num == 6) {
            final MediaPlayer whooshSound = MediaPlayer.create(this, R.raw.whoosh);
            whooshSound.start();
            whooshButton.setBackgroundColor(getResources().getColor(R.color.MediumVioletRed));
            whooshButton.setEnabled(false);
            // clickability
            alarmButton.setClickable(false);
            arcadeButton.setClickable(false);
            dogButton.setClickable(false);
            gooseButton.setClickable(false);
            coinButton.setClickable(false);
            whooshButton.setClickable(false);
            Handler handler6 = new Handler();
            handler6.postDelayed(new Runnable() {
                public void run() {
                    whooshButton.setBackgroundColor(getResources().getColor(R.color.Plum));
                    whooshButton.setEnabled(true);
                    alarmButton.setClickable(true);
                    arcadeButton.setClickable(true);
                    dogButton.setClickable(true);
                    gooseButton.setClickable(true);
                    coinButton.setClickable(true);
                    whooshButton.setClickable(true);
                }
            }, 1500);
        }

    }

    // OUTSIDE MAINACTIVITY:
}