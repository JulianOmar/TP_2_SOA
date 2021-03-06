package com.example.covidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    public Button btnAccept;
    public Button btnCancel;
    private EditText nameOrigin;
    private EditText lastnameOrigin;
    private EditText dniOrigin;
    private EditText emailOrigin;
    private EditText passwordOrigin;
    private EditText commissionOrigin;
    private EditText groupOrigin;
    public ProgressBar progressBar;

    private AlertDialog alertDialog;

    public IntentFilter filter;

    /**
     * Creacion de al activity Register
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("AppInfo", "<<<<ON_CREATE REGISTERA_ACTIVITY>>>>");
        setContentView(R.layout.activity_register);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        nameOrigin = (EditText) findViewById(R.id.editTextName);
        lastnameOrigin = (EditText) findViewById(R.id.editTextLastname);
        dniOrigin = (EditText) findViewById(R.id.editTextDni);
        emailOrigin = (EditText) findViewById(R.id.editTextEmail);
        passwordOrigin = (EditText) findViewById(R.id.editTextPassword);
        commissionOrigin = (EditText) findViewById(R.id.editTextCommission);
        groupOrigin = (EditText) findViewById(R.id.editTextGroup);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AppInfo", "<<<<ON_STOP REGISTERA_ACTIVITY>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AppInfo", "<<<<ON_DESTROY REGISTERA_ACTIVITY>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AppInfo", "<<<<ON_PAUSE REGISTERA_ACTIVITY>>>>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("AppInfo", "<<<<ON_RESTART REGISTERA_ACTIVITY>>>>");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AppInfo", "<<<<ON_START REGISTERA_ACTIVITY>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AppInfo", "<<<<ON_RESUME REGISTERA_ACTIVITY>>>>");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Verificacion de los campos ingresados, en caso valido se inicia RegisterAsyncTask para regirstar usuario
     * por medio de comunicaci??n con el servidor
     *
     * @param view
     */
    public void onClickAccept(View view) {
        if (verifyRegisterFields()) {
            new RegisterAsyncTask(RegisterActivity.this).execute(nameOrigin.getText().toString(), lastnameOrigin.getText().toString(),
                    dniOrigin.getText().toString(), emailOrigin.getText().toString(),
                    passwordOrigin.getText().toString(), commissionOrigin.getText().toString(),
                    groupOrigin.getText().toString());
        }
    }

    /**
     * En caso de cancelar se regresa a la Activity Login
     *
     * @param view
     */
    public void onClickCancel(View view) {
        Intent intent;
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void runingActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Verificacion de los campos ingresados
     *
     * @return
     */
    private boolean verifyRegisterFields() {
        if (nameOrigin.getText().toString().matches("") || lastnameOrigin.getText().toString().matches("")
                || dniOrigin.getText().toString().matches("") || emailOrigin.getText().toString().matches("")
                || passwordOrigin.getText().toString().matches("") || commissionOrigin.getText().toString().matches("")
                || groupOrigin.getText().toString().matches("")
        ) {
            setAlertText("Error de Registro!", "Debe completar todos los campos.");
            return false;
        } else if (!Utils.validate(emailOrigin.getText().toString())) {
            setAlertText("Error de Registro!", "Debe ingresar un mail valido.");
            return false;
        } else if (passwordOrigin.getText().toString().length() < 8) {
            setAlertText("Error de Registro!", "Debe ingresar una contrase??a de 8 caracteres o m??s.");
            return false;
        } else if (dniOrigin.getText().toString().length() < 7 || dniOrigin.getText().toString().length() > 8) {
            setAlertText("Error de Registro!", "Debe ingresar un DNI valido.");
            return false;
        }
        return true;
    }

    /**
     * Mensaje de alerta de error
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