package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText telephoneNumber;
    private AlertDialog alertDialog;
    private float batteryPct;
    private ImageView imageViewBattery;
    private TextView tvBatteryLevel;

    /**
     * Creación de la Activity Main
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AppInfo", "<<<<ON_CREATE MAIN_ACTIVITY>>>>");
        setContentView(R.layout.activity_main);
        btnSend = (Button) findViewById(R.id.btnSendCode);
        telephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = MainActivity.this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        batteryPct = level * 100 / (float) scale;
        tvBatteryLevel = (TextView) findViewById(R.id.textViewBatteryLevel);
        tvBatteryLevel.setText(String.valueOf(batteryPct));
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        imageViewBattery = (ImageView) findViewById(R.id.imageViewBattery);
        if (isCharging) {
            imageViewBattery.setImageResource(R.drawable.ic_battery_charging_90_black_48dp);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AppInfo", "<<<<ON_STOP MAIN_ACTIVITY>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AppInfo", "<<<<ON_DESTROY MAIN_ACTIVITY>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AppInfo", "<<<<ON_PAUSE MAIN_ACTIVITY>>>>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("AppInfo", "<<<<ON_RESTART MAIN_ACTIVITY>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AppInfo", "<<<<ON_RESUME MAIN_ACTIVITY>>>>");
    }

    /**
     * Validación de numero y avance a la pantalla de autenticación
     *
     * @param view
     */
    public void onClickSendCode(View view) {
        Intent intent;
        if (telephoneNumber.getText().toString().length() != 10) {
            setAlertText("Error!", "Ingrese un número de telefono valido.");
        } else if (telephoneNumber.getText().toString().matches("")) {
            setAlertText("Error!", "Debe ingresar un número de telefono valido.");
        } else {
            intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            intent.putExtra("numeroTelefono", telephoneNumber.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    /**
     * @param title
     * @param message
     */
    public void setAlertText(String title, String message) {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}