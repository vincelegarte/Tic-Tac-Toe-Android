package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    private Button start;
    private Button skip;
    private EditText playerName1;
    private EditText playerName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        start = findViewById(R.id.button_start);
        skip = findViewById(R.id.button_skip);
        playerName1 = findViewById(R.id.player1);
        playerName2 = findViewById(R.id.player2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerName1.getText().toString().trim().isEmpty()) {
                    playerName1.setError("Enter Player 1's name.");
                } else if (playerName2.getText().toString().trim().isEmpty()) {
                    playerName2.setError("Enter Player 2's name.");
                } else if (playerName1.getText().toString().equalsIgnoreCase(playerName2.getText().toString())) {
                    playerName2.setError("Name must be unique");
                } else {
                    Intent intent = new Intent(UserActivity.this, MultiPlayerActivity.class);
                    intent.putExtra("player1", playerName1.getText().toString());
                    intent.putExtra("player2", playerName2.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MultiPlayerActivity.class);
                intent.putExtra("player1", "Player 1");
                intent.putExtra("player2", "Player 2");
                startActivity(intent);
                finish();
            }
        });
    }
}