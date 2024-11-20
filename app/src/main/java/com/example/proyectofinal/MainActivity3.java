package com.example.proyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MainActivity3 extends AppCompatActivity {

    DrawerLayout drawerLayer;
    ImageSlider imageSlider;

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        drawerLayer=findViewById(R.id.drawerLayer);
        imageSlider=findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.imgsc1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgsc2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgsc3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgsc4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
    }

    public void ClickMenu(View view) {openDrawer(drawerLayer);}

    private void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Inicio(View view){
        Intent myIntent = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(myIntent);
    }

    public void Account(View view){
        Intent myIntent = new Intent(MainActivity3.this, Account.class);
        startActivity(myIntent);
    }

    public void PersonalData(View view){
        BiometricManager biometricManager = BiometricManager.from(this);
        //mLayout = findViewById(R.id.mainData);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Toast.makeText(getApplicationContext(), "Autenticación aceptada", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(getApplicationContext(), "Dispositivo sin compatibilidad", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(getApplicationContext(), "Sensor no disponible", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Toast.makeText(getApplicationContext(), "Huella no enrolada", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity3.this, executor, new BiometricPrompt.AuthenticationCallback(){

            public void  onAuthenticationError(int errorCode, @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {

                Toast.makeText(getApplicationContext(), "Huella autenticada", Toast.LENGTH_SHORT).show();
                drawerLayer.setVisibility(View.VISIBLE);
                Intent myIntent = new Intent(MainActivity3.this, PersonalData.class);
                startActivity(myIntent);
                super.onAuthenticationSucceeded(result);

            }

            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Visualizar Datos Personales")
                .setDescription("Usar huella para entrar")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    public void LogOut(View view){
        logoutMenu(MainActivity3.this);
    }

    private void logoutMenu(MainActivity3 mainActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Está seguro que quiere cerrar sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent myIntent = new Intent(MainActivity3.this, PersonalData.class);
                startActivity(myIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}