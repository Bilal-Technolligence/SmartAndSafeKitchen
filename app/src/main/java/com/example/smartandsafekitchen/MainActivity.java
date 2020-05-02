package com.example.smartandsafekitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView btnWater, btnSmoke, btnGas, btnTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWater = findViewById(R.id.cardWaterSensor);
        btnSmoke = findViewById(R.id.cardSmokeSensor);
        btnGas = findViewById(R.id.cardGasSensor);
        btnTemp = findViewById(R.id.cardTempSensor);


        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WaterSensorActivity.class));
            }
        });
        btnSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SmokeSensorActivity.class));
            }
        });
        btnGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GasSensorActivity.class));
            }
        });
        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class));
            }
        });
    }
}
