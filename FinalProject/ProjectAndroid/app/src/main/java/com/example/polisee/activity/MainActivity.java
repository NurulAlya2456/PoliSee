package com.example.polisee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.polisee.R;
import com.example.polisee.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Button btnmasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmasuk = findViewById(R.id.btnmasuk);

        btnmasuk.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
            startActivity(intent);
        });
    }
}