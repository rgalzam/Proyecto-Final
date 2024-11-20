package com.example.proyectofinal;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRetrieve {

    public static void fetchAndDisplayUsername(Context context, TextView usernameTextView) {
        // Obtiene el usuario actual de Firebase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            // Obtiene el uid del usuario que es el mismo que su llave única en Realtime DB
            String uid = currentUser.getUid();

            // Referencia al nodo de credenciales en RealtimeDV
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("credentials");

            // Encontrar el usuario que tenga elmismo UID
            userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Recupera usuario
                        String username = dataSnapshot.child("username").getValue(String.class);

                        // Solamente escribe el usuario en el text view
                        usernameTextView.setText(username);

                        String email=currentUser.getEmail();

                    } else {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    //All Data
    public static void fetchAndDisplayAllData(Context context, TextView usernameTextView, TextView typeAccTextView, TextView emailTextView) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("credentials");

            userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Recupera usuario y tipo de cuenta
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String typeAcc = dataSnapshot.child("typeAcc").getValue(String.class);

                        // Actualiza los TextView
                        usernameTextView.setText("Nombre de la empresa: " + username);
                        typeAccTextView.setText("Tipo de cuenta: "+typeAcc);

                        //Obtiene email y actualiza Text View
                        String email=currentUser.getEmail();
                        emailTextView.setText("Correo: " + email);

                    } else {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }
}

