package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button reset;
    private Button quit;
    private Button[][] buttons = new Button[3][3];
    private boolean pturn = true;
    private int count;
    private TextView winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        reset = findViewById(R.id.button_reset);
        quit = findViewById(R.id.button_quit);
        winner = findViewById(R.id.text_winner);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonPosition = "button" + i + "" + j;
                int resId = getResources().getIdentifier(buttonPosition, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        buttons[i][j].setText("");
                        winner.setText("");
                        buttons[i][j].setEnabled(true);
                    }
                }
                count = 0;
                pturn = true;
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SinglePlayerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (pturn) {
            ((Button) v).setText("X");
        }

        count++;

        if (winner()) { //if the player wins
            if (pturn) {
                winner.setText("YOU WIN!");
                for (int i = 0; i < 3; i++) { //stops receiving input from the player 1
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        } else if (count == 5) {
            winner.setText("DRAW!");
        } else {
            ai();
        }

    }

    public void ai() {
        int count;
        int position;
        count = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().toString().equals("")) count++;

        position = (int) (Math.random() * count);
        count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    if (count == position) {
                        buttons[i][j].setText("O");
                    }

                    count++;

                    if (winner()) { //if ai wins
                        winner.setText("YOU LOSE!");
                        for (i = 0; i < 3; i++) { //stops receiving input from the ai
                            for (j = 0; j < 3; j++) {
                                buttons[i][j].setEnabled(false);
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean winner() { //checks who's the winner
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) { //vertical
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //horizontal
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //left diagonal
            if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //right diagonal
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
                return true;
            }
        }

        return false;
    }
}