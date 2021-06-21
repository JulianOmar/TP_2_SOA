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
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class PhysicalStateActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private TextView tvSteps;
    private TextView tvSpeed;
    private TextView tvActiveTime;
    private TextView tvDistance;
    private SharedPreferences sp;

    private AlertDialog alertDialog;
    public static final String ACTUAL_STEPS = "ActualSteps";
    public static final String INITIAL_TIME = "InitialTime";
    public static final String ACTIVE_TIME = "InitialTime";

    private Long initialTime;
    private Long previousTime;
    private Long actualTime;
    private Long activeTime;
    private Long auxTime;
    private Integer previousSteps;
    private Integer actualSteps;
    private Double speed;
    boolean running = false;

    /**
     * Se crea la Activity PhysicalState
     * Se obtien los valores SP
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_state);

        Intent intent2 = getIntent();
        Bundle extras = intent2.getExtras();

        previousTime = System.currentTimeMillis();
        initialTime = System.currentTimeMillis();
        previousSteps = 0;
        actualSteps = 0;
        tvSteps = (TextView) findViewById(R.id.textViewSteps2);
        tvSpeed = (TextView) findViewById(R.id.textViewSpeed2);
        tvActiveTime = (TextView) findViewById(R.id.textViewActiveTime2);
        tvDistance = (TextView) findViewById(R.id.textViewDistance2);


        tvSteps.setText("0");
        tvActiveTime.setText("0s");
        tvSpeed.setText("0p/s");
        tvDistance.setText("0m");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Log.i("AppInfo", "<<<<ON_CREATE PHYSICAL_STATE_ACTIVITY>>>>");
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
        //running = false;
        //Para que el hardware deje de detectar pasos
        //sensorManager.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        previousTime = System.currentTimeMillis();
        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor no encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Se detectan los pasos registrados por el usuario
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_STEP_DETECTOR:
                if (running) {
                    Long seconds;
                    actualSteps++;
                    tvSteps.setText(String.valueOf(actualSteps));
                    tvDistance.setText(String.valueOf(new DecimalFormat("#.##").format(actualSteps * 0.9)) + "m");
                    actualTime = java.lang.System.currentTimeMillis();
                    seconds = (actualTime - previousTime) / 1000;
                    tvActiveTime.setText(String.valueOf((actualTime - initialTime) / 1000) + "s");
                    if (seconds >= 5) {
                        speed = (double) (actualSteps - previousSteps) / seconds;
                        tvSpeed.setText(String.valueOf(new DecimalFormat("#.##").format(speed) + "p/s"));
                        previousTime = actualTime;
                        previousSteps = actualSteps;
                    }
                    if (speed >= 2 && !alertDialog.isShowing() && !(PhysicalStateActivity.this).isFinishing()) {
                        setAlertText("¡Atención!", "¡permanesca en reposo!");
                    }
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
     * Reinicio de contadores
     *
     * @param view
     */
    public void onClickRestart(View view) {
        tvSteps.setText("0");
        tvActiveTime.setText("0s");
        tvSpeed.setText("0p/s");
        tvDistance.setText("0m");
        previousTime = java.lang.System.currentTimeMillis();
        initialTime = java.lang.System.currentTimeMillis();
        previousSteps = 0;
        actualSteps = 0;
        activeTime = 0L;
        auxTime = 0L;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PhysicalStateActivity.ACTUAL_STEPS, actualSteps.toString());
        editor.putString(PhysicalStateActivity.INITIAL_TIME, initialTime.toString());
        editor.putString(PhysicalStateActivity.ACTIVE_TIME, activeTime.toString());
        editor.commit();
    }

    /**
     * Guarda la cantidad de pasos y el tiempo activo
     *
     * @param view
     */
    public void onClickBack(View view) {
        Intent intent = new Intent(PhysicalStateActivity.this, MenuActivity.class);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PhysicalStateActivity.ACTUAL_STEPS, actualSteps.toString());
        editor.putString(PhysicalStateActivity.ACTIVE_TIME, activeTime.toString());
        editor.commit();
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