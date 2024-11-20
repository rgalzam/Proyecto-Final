package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalData extends AppCompatActivity {

    TextView orgNameTV2;
    TextView emailTV;
    TextView typeAccTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_data);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        orgNameTV2=findViewById(R.id.orgNameTV2);
        emailTV=findViewById(R.id.emailTV);
        typeAccTV=findViewById(R.id.typeAccTV);
        UserRetrieve.fetchAndDisplayAllData(this, orgNameTV2, typeAccTV, emailTV);
    }

    public void Back(View view){
        Intent myIntent = new Intent(PersonalData.this, MainActivity3.class);
        startActivity(myIntent);
    }
}