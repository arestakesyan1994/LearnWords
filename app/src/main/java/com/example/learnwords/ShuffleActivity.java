package com.example.learnwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShuffleActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);
        swipeToRefresh = findViewById(R.id.swipeToRefreshShuffle);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShuffleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToRefresh.setRefreshing(false);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment f = new FragmentForShuffle();
                        ft.replace(R.id.fragment, f);
                        ft.commit();
                    }
                }, 1000);
            }
        });
    }
}