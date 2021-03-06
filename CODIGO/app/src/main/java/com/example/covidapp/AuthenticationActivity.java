package com.example.covidapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class AuthenticationActivity extends AppCompatActivity {

    private Button btnAccept;
    private Button btnCancel;
    private Button btnSendCode;
    private EditText userCode;
    private Integer random = 1;
    private String number = "";
    private AlertDialog alertDialog;

    /**
     * Creación de la Activity Authentication
     * Generación de un numero random para autenticación
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AppInfo", "<<<<ON_CREATE AUTHENTICATION_ACTIVITY>>>>");
        setContentView(R.layout.activity_authentication);
        btnAccept = (Button) findViewById(R.id.btnAcceptAuth);
        btnCancel = (Button) findViewById(R.id.btnCancelAuth);
        btnSendCode = (Button) findViewById(R.id.btnSendCodeAuth);
        userCode = (EditText) findViewById(R.id.editTextCode);
        alertDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
        Intent intentL2 = getIntent();
        Bundle extras = intentL2.getExtras();
        number = (String) extras.get("numeroTelefono");
        random = (int) (Math.random() * 10000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSMS(number);
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
    }

    /**
     * Envia el codigo de validación
     *
     * @param number2
     */
    private void sendSMS(String number2) {
        String number = number2;
        random = (int) (Math.random() * 10000);
        String message = "Su codigo de verificacion es:" + random.toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(this, "El mensaje ha sido enviado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error en el enviar del mensaje", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AppInfo", "<<<<ON_STOP AUTHENTICATION_ACTIVITY>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AppInfo", "<<<<ON_DESTROY AUTHENTICATION_ACTIVITY>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AppInfo", "<<<<ON_PAUSE AUTHENTICATION_ACTIVITY>>>>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("AppInfo", "<<<<ON_RESTART AUTHENTICATION_ACTIVITY>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AppInfo", "<<<<ON_RESUME AUTHENTICATION_ACTIVITY>>>>");
    }

    /**
     * autenticacion y continuo en la pantalla de login
     *
     * @param view
     */
    public void onClickAccept(View view) {
        Intent intent;
        if (userCode.getText().toString().matches("")) {
            setAlertText("Error!", "Debe ingresar un codigo.");
        } else {
            if (random.toString().equals(userCode.getText().toString())) {
                intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                setAlertText("Error!", "Codigo Incorrecto. Intente nuevamente.");
            }
        }
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
     * Retorno a la vistia inicial
     *
     * @param view
     */
    public void onClickCancel(View view) {
        Intent intent;
        intent = new Intent(AuthenticationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Reenvio del mensaje
     *
     * @param view
     */
    public void onClickSendCode(View view) {
        sendSMS(number);
    }


}