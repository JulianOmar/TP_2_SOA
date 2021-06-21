package com.example.covidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class AlertCovidActivity extends AppCompatActivity {


    /**
     * Se crea la Activity Alerta de Covid
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AppInfo", "<<<<ON_CREATE ALERT_COVID_ACTIVITY>>>>");
        setContentView(R.layout.activity_alert_covid);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AppInfo", "<<<<ON_STOP ALERT_COVID_ACTIVITY>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AppInfo", "<<<<ON_DESTROY ALERT_COVID_ACTIVITY>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AppInfo", "<<<<ON_PAUSE ALERT_COVID_ACTIVITY>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AppInfo", "<<<<ON_RESUME ALERT_COVID_ACTIVITY>>>>");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AppInfo", "<<<<ON_START ALERT_COVID_ACTIVITY>>>>");
    }

    /**
     * Regreso al menu pirncipal
     *
     * @param view
     */
    public void onClickAccept(View view) {
        Intent intent = new Intent(AlertCovidActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Retorno al Activity Menu
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AlertCovidActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}

