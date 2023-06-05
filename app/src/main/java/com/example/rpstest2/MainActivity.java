package com.example.rpstest2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public TextLCD textLCD = new TextLCD();
    public FullLED fullLED = new FullLED();
    public DotMatrix dotMatrix = new DotMatrix();

    Button buttonRock, buttonScissor, buttonPaper, btn_Reset;
    TextView Score;
    ImageView iv_yourChoice, iv_computerChoice;
    int HumanScore, ComputerScore = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dotMatrix.ON();

        buttonPaper = (Button) findViewById(R.id.buttonPaper);
        buttonRock = (Button) findViewById(R.id.buttonRock);
        buttonScissor = (Button) findViewById(R.id.buttonScissor);
        btn_Reset = (Button) findViewById(R.id.btn_Reset);
        iv_computerChoice = (ImageView) findViewById(R.id.iv_computerChoice);
        iv_yourChoice = (ImageView) findViewById(R.id.iv_yourChoice);

        Score = (TextView) findViewById(R.id.Score);
        buttonPaper.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                iv_yourChoice.setImageResource(R.drawable.paper);
                String message = play_turn("paper");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                Score.setText("You : " + Integer.toString(HumanScore) + " Computer : " + Integer.toString(ComputerScore));
            }
        });

        buttonRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_yourChoice.setImageResource(R.drawable.rock);
                String message = play_turn("rock");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                Score.setText("You : " + Integer.toString(HumanScore) + " Computer : " + Integer.toString(ComputerScore));
            }
        });

        buttonScissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_yourChoice.setImageResource(R.drawable.scis);
                String message = play_turn("scissor");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                Score.setText("You : " + Integer.toString(HumanScore) + " Computer : " + Integer.toString(ComputerScore));
            }
        });

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLCD.clear();
                fullLED.FLEDCONTROL(9, 0, 0, 0);
                HumanScore = 0;
                ComputerScore = 0;
                Score.setText("You : " + Integer.toString(HumanScore) + " Computer : " + Integer.toString(ComputerScore));
            }
        });

    }

    public String play_turn(String yourChoice) {

        String computer = "";
        Random r = new Random();
        //choose 1 2 or 3

        int computer_choice_number = r.nextInt(3) + 1;

        if (computer_choice_number == 1) {
            computer = "rock";
        } else if (computer_choice_number == 2) {
            computer = "scissor";
        } else if (computer_choice_number == 3) {
            computer = "paper";
        }

        //set the computer image based on his choice
        if (computer == "rock") {
            iv_computerChoice.setImageResource(R.drawable.rock);
        } else if (computer == "scissor") {
            iv_computerChoice.setImageResource(R.drawable.scis);
        } else if (computer == "paper") {
            iv_computerChoice.setImageResource(R.drawable.paper);
        }

        //for scoring
        if (computer == yourChoice) {
            textLCD.Draw();
            PiezoManager.Draw();
            fullLED.FLEDCONTROL(9, 0, 0, 100);

            return "Draw!";
        } else if (yourChoice == "rock" && computer == "scissor") {
            HumanScore++;
            PiezoManager.UserWin();
            textLCD.UserWin();
            fullLED.FLEDCONTROL(9, 0, 100, 0);
            return "You win!";
        } else if (computer == "paper" && yourChoice == "scissor") {
            HumanScore++;
            PiezoManager.UserWin();
            textLCD.UserWin();
            fullLED.FLEDCONTROL(9, 0, 100, 0);
            return "You win!";
        } else if (computer == "rock" && yourChoice == "paper") {
            HumanScore++;
            PiezoManager.UserWin();
            textLCD.UserWin();
            fullLED.FLEDCONTROL(9, 0, 100, 0);
            return "You win!";
        } else if (yourChoice == "paper" && computer == "scissor") {
            ComputerScore++;
            PiezoManager.ComputerWin();
            textLCD.ComputerWin();
            fullLED.FLEDCONTROL(9, 100, 0, 0);
            return "Computer win!";
        } else if (yourChoice == "rock" && computer == "paper") {
            ComputerScore++;
            PiezoManager.ComputerWin();
            textLCD.ComputerWin();
            fullLED.FLEDCONTROL(9, 100, 0, 0);
            return "Computer win!";
        } else if (yourChoice == "scissor" && computer == "rock") {
            ComputerScore++;
            PiezoManager.ComputerWin();
            textLCD.ComputerWin();
            fullLED.FLEDCONTROL(9, 100, 0, 0);
            return "Computer win!";
        } else
            return "Not Sure";
    }


}
