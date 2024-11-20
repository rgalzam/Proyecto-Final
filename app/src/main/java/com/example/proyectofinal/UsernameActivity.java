package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UsernameActivity extends AppCompatActivity {

    private Button sendButton;
    EditText userET;
    String typeAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_username);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        userET = findViewById(R.id.userET);
        sendButton = findViewById(R.id.sendButton);
        typeAcc = getIntent().getStringExtra("typeAcc");

        sendButton.setOnClickListener(v -> saveUsernameAndCredentials(typeAcc));
    }

    private void saveUsernameAndCredentials(String typeAcc) {
        String username = userET.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity2 mainActivity2 = new MainActivity2();
        mainActivity2.addCredentialsToDB(typeAcc, username);

        Intent myintent = new Intent(UsernameActivity.this, MainActivity3.class);
        startActivity(myintent);
        finish();
    }
}