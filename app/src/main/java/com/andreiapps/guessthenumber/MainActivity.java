package com.andreiapps.guessthenumber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // These are so that the generated number is not equal to the previously generated or guessed numbers
    int prevGuess=0, prevCorrect=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Declare the widgets
        Button resetButton = findViewById(R.id.resetButton);
        EditText guessInput = findViewById(R.id.guessInput);
        Button guessButton = findViewById(R.id.guessButton);
        TextView guessCorrect = findViewById(R.id.guessCorrect);
        //Declare the random number generator object
        Random random = new Random();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the game
                guessInput.setText("");
                guessCorrect.setText("");
                resetButton.setEnabled(false);
                guessButton.setEnabled(true);
            }
        });
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the guess as string, enable the reset button and disable the guess button
                String guess_str = guessInput.getText().toString();
                resetButton.setEnabled(true);
                guessButton.setEnabled(false);
                try {
                    // Try to convert the text to an integer, generate a number and check if the guess is correct
                    int guess = Integer.parseInt(guess_str);
                    int correct;
                    correct = random.nextInt(10) + 1;
                    while (correct == prevCorrect || correct == prevGuess) correct = random.nextInt(10) + 1;
                    if (guess == correct) guessCorrect.setText("You won!");
                    else guessCorrect.setText("You lost. The correct number was " + correct);
                    prevGuess = guess;
                    prevCorrect = correct;
                }
                catch (NumberFormatException e) {
                    // This runs if the guess is not an integer
                    guessCorrect.setText("Please enter a whole number from 1 to 10");
                }
            }
        });
    }
}