package com.example.covidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MenuActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    private AlertDialog alertDialog;
    private SharedPreferences sp;
    private boolean closeDistance;

    public boolean isCloseDistance() {
        return closeDistance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor no encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Se dirige a la Activity PhysicalState
     *
     * @param view
     */
    public void onClickPhysicalState(View view) {
        Intent intent = new Intent(MenuActivity.this, PhysicalStateActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Se dirige a la Activity AutoTest
     *
     * @param view
     */
    public void onClickAutoTest(View view) {
        Intent intent = new Intent(MenuActivity.this, AutoTestActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Finalizacion de la sesion y regrso a la Activity Login
     *
     * @param view
     */
    public void onClickLogOut(View view) {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        TimerTaskClass ttc = new TimerTaskClass();
        ttc.getInstance().stopTimer();
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                String proximitySP = String.valueOf(event.values[0]);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("Proximity");
                editor.putString("Proximity", proximitySP);
                Log.i("AppInfo", "<<<<PROXIMITY SENSOR: " + proximitySP + ">>>>");
                editor.commit();
                if (event.values[0] < event.sensor.getMaximumRange()) {
                    this.closeDistance = true;
                    new DistanceSensorAsyncTask(MenuActivity.this).execute();
                } else {
                    this.closeDistance = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * se dirige a la Activity AutoTest
     *
     * @param strings
     */
    public void runingAutoTestActivity(String... strings) {
        Intent intent = new Intent(MenuActivity.this, AutoTestActivity.class);
        sensorManager.unregisterListener(MenuActivity.this);
        startActivity(intent);
        finish();
    }

    /**
     * Alerta de errores
     *
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