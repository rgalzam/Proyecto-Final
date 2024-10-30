package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    Spinner typeAccount;
    EditText mailEditText;
    EditText pwEditText;
    Button createBtn;

    private TextView logInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        typeAccount = findViewById(R.id.typeAccount);
        mailEditText = findViewById(R.id.mailEditText);
        pwEditText = findViewById(R.id.pwEditText);
        createBtn = findViewById(R.id.createBtn);

        //Array para el spinner de opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAccount.setAdapter(adapter);

        // Acciones al botón de crear cuenta
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeAcc = typeAccount.getSelectedItem().toString();
                String email = mailEditText.getText().toString();
                String pass = pwEditText.getText().toString();

                if(email.isEmpty()){
                    mailEditText.setText("Favor de llenar los datos");
                    return;
                }

                if(pass.isEmpty()){
                    pwEditText.setText("Favor de llenar los datos");
                }

                addCredentialsToDB(email, pass);
            }
        });

        //Transición, regresa a inicio de sesión
        logInTextView=findViewById(R.id.logInTextView);
        logInTextView.setOnClickListener(view ->{
            Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(myIntent);
        });
    }

    //Se agregan a la base de datos
    public void addCredentialsToDB(String email, String pass){
        HashMap<String, Object> textHashMap = new HashMap<>();
        textHashMap.put("mail", email);
        textHashMap.put("password", pass);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference credentialsRef = database.getReference("credentials");

        String key = credentialsRef.push().getKey();
        textHashMap.put("credentials", key);

        credentialsRef.child(key).setValue(textHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity2.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                mailEditText.getText().clear();
                pwEditText.getText().clear();
            }
        });
    }
}