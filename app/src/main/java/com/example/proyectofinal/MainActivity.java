package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText usernameET;
    EditText passwordET;

    private Button createAccBtn;
    private Button loginButton;

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

        usernameET=findViewById((R.id.usernameET));
        passwordET=findViewById((R.id.passwordET));

        createAccBtn=findViewById(R.id.createAccBtn);
        loginButton = findViewById(R.id.loginButton);

        FirebaseApp.initializeApp(this);  // Inicializa Firebase (asegúrate de que Firebase está configurado)
        mAuth = FirebaseAuth.getInstance();

        //Envia a usuario a crear nueva cuenta
        createAccBtn.setOnClickListener(view ->{
            Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(myIntent);
        });

        loginButton.setOnClickListener(view ->{
            String email = usernameET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            // Valida credenciales
            if (!email.isEmpty() && !password.isEmpty()) {
                verificarCredenciales(email, password);
            } else {
                Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void verificarCredenciales(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Intent myintent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(myintent);
                            finish();
                        }
                    } else {
                        // Fallo en el inicio de sesión
                        Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}