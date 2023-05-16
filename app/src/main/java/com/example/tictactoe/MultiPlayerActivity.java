package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MultiPlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button reset;
    private Button quit;
    private Button[][] buttons = new Button[3][3];
    private boolean pturn = true;
    private int count;
    private TextView turn;
    private String player1;
    private String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        reset = findViewById(R.id.button_reset);
        quit = findViewById(R.id.button_quit);
        turn = findViewById(R.id.text_turn);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonPosition = "button" + i + "" + j;
                int resId = getResources().getIdentifier(buttonPosition, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        player1 = getIntent().getExtras().getString("player1");
        player2 = getIntent().getExtras().getString("player2");

        turn.setText(player1+"'S TURN");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        buttons[i][j].setText("");
                        turn.setText(player1+"'S TURN");
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
                Intent intent = new Intent(MultiPlayerActivity.this, MainActivity.class);
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
        if (pturn) { //player one's turn
            turn.setText(player2+"'S TURN");
            ((Button) v).setText("X");
        } else { //player two's turn
            turn.setText(player1+"'S TURN");
            ((Button) v).setText("O");
        }
        count++;

        if (winner()) {
            if (pturn) {
                turn.setText(player1+" WINS!");
                for (int i = 0; i < 3; i++) { //stops receiving input from the user 1
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            } else {
                turn.setText(player2+" WINS!");
                for (int i = 0; i < 3; i++) { //stops receiving input from the user 2
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        } else if (count == 9) {
            turn.setText("DRAW");
        } else {
            pturn = !pturn;
        }
    }

    private boolean winner() { //checks who's the winner
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) { //row
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //column
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //left diagonal
            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //right diagonal
            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")) {
                return true;
            }
        }

        return false;
    }
}