package com.example.proyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    DrawerLayout drawerLayer;
    ImageSlider imageSlider;

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
                Intent myIntent = new Intent(MainActivity3.this, MainActivity.class);
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