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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Spinner typeAccount;
    EditText mailEditText;
    EditText pwEditText;
    EditText confirmPWEditText;
    Button createBtn;


    private Button logInBtn;

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
        confirmPWEditText = findViewById(R.id.confirmPWEditText);
        createBtn = findViewById(R.id.createBtn);

        //Array para el spinner de opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAccount.setAdapter(adapter);

        // Validaciones al botón de crear cuenta
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeAcc = typeAccount.getSelectedItem().toString();
                String email = mailEditText.getText().toString();
                String pass = pwEditText.getText().toString();
                String confirmPass = confirmPWEditText.getText().toString();

                if(typeAcc.equals("Selecciona tipo de cuenta")){
                    Toast.makeText(MainActivity2.this, "Por favor seleccione un tipo de cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Por favor ingrese un correo válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Por favor ingrese contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!pass.equals(confirmPass)){
                    Toast.makeText(MainActivity2.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                registrarUsuario(email, pass, typeAcc);

            }
        });

        //Transición, regresa a inicio de sesión
        logInBtn=findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(view ->{
            Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(myIntent);
        });
    }

    //Se agregan a la base de datos de autenticación
    private void registrarUsuario(String email, String password, String typeAcc) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso, el usuario está ahora autenticado
                        addCredentialsToDB(typeAcc);
                        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "Error al registrar usuario, intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
                    typeAccount.setSelection(0);
                    mailEditText.getText().clear();
                    pwEditText.getText().clear();
                    confirmPWEditText.getText().clear();
                });
    }

    // El tipo de cuenta se agrega a realtime database
    public void addCredentialsToDB(String typeAcc){
        HashMap<String, Object> textHashMap = new HashMap<>();

        textHashMap.put("typeAcc", typeAcc);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference credentialsRef = database.getReference("credentials");

        String key = credentialsRef.push().getKey();
        textHashMap.put("key", key);

        credentialsRef.child(key).setValue(textHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }
}