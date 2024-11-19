package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Account extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    TextView longET, latiET;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account);

        latiET=(TextView) findViewById(R.id.latiET);
        longET=(TextView) findViewById(R.id.longET);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;
        this.map.setOnMapClickListener(this);
        this.map.setOnMapLongClickListener(this);

        setValues(19.8077463, -99.4077038);

        LatLng mexico = new LatLng(20, 100);
        map.addMarker(new MarkerOptions().position(mexico).title("México"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mexico));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        map.clear();
        LatLng mexico = new LatLng(latLng.latitude, latLng.longitude);
        map.addMarker(new MarkerOptions().position(mexico).title("México"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mexico));

        setValues(latLng.latitude, latLng.longitude);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        map.clear();
        LatLng mexico = new LatLng(latLng.latitude, latLng.longitude);
        map.addMarker(new MarkerOptions().position(mexico).title("México"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mexico));

        setValues(latLng.latitude, latLng.longitude);
    }

    public void setValues(double lat, double longi){
        longET.setText("Longitud: "+ longi);
        latiET.setText("Longitud: "+ lat);
    }
}