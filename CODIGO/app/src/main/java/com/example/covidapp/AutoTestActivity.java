package com.example.covidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;



public class AutoTestActivity extends AppCompatActivity {

    private CheckBox cb00;
    private CheckBox cb01;
    private CheckBox cb02;
    private CheckBox cb03;
    private CheckBox cb04;
    private CheckBox cb05;
    private CheckBox cb06;
    private Button btnCancel;
    private Button btnAccept;


    private AlertDialog alertDialog;
    private SharedPreferences sp;
    private JSONArray jsonArray;

    /**
     * Se genera la Activity AutoTest
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        cb00 = (CheckBox) findViewById(R.id.checkBox00);
        cb01 = (CheckBox) findViewById(R.id.checkBox01);
        cb02 = (CheckBox) findViewById(R.id.checkBox02);
        cb03 = (CheckBox) findViewById(R.id.checkBox03);
        cb04 = (CheckBox) findViewById(R.id.checkBox04);
        cb05 = (CheckBox) findViewById(R.id.checkBox05);
        cb06 = (CheckBox) findViewById(R.id.checkBox06);
        btnCancel = (Button) findViewById(R.id.btnCancelForm);
        btnAccept = (Button) findViewById(R.id.btnAcceptForm);
        alertDialog = new AlertDialog.Builder(AutoTestActivity.this).create();
    }


    /**
     * @return
     */
    private boolean hasSymptoms() {
        if (cb00.isChecked() || cb01.isChecked() || cb02.isChecked() ||
                cb03.isChecked() || cb04.isChecked() || cb05.isChecked() || cb06.isChecked()) {
            return true;
        }
        return false;
    }


    public void onClickCancel(View view) {
        Intent intent = new Intent(AutoTestActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSend(View view) {
        if (hasSymptoms()) {
            runingActivity();
        } else {
            setAlertTextMenuButton("Notificacion", "Usted no presenta sintomas de Covid 19");
        }
    }

    public void runingActivity(String... strings) {
        Intent intent = new Intent(AutoTestActivity.this, AlertCovidActivity.class);
        startActivity(intent);
        finish();
    }

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

    /**
     * Alerta de errores
     *
     * @param title
     * @param message
     */
    public void setAlertTextMenuButton(String title, String message) {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(AutoTestActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog.show();
    }

}