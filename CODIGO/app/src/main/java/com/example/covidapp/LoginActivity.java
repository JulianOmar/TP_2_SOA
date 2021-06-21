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
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

    public Button btnRegister;
    public Button btnLogin;
    public EditText loginEmail;
    public EditText loginPassword;
    private SharedPreferences sp;
    private String userEmail;
    private String userToken;

    private AlertDialog alertDialog;

    /**
     * Creación de la Artivity Login
     * Creación del contador de pasos
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginEmail = (EditText) findViewById(R.id.editTextEmailAddress);
        loginPassword = (EditText) findViewById(R.id.editTextPassword);
        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        sp = this.getSharedPreferences(Utils.SP_STEP_TIME, Context.MODE_PRIVATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AppInfo", "<<<<ON_STOP LOGIN_ACTIVITY>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AppInfo", "<<<<ON_DESTROY LOGIN_ACTIVITY>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AppInfo", "<<<<ON_PAUSE LOGIN_ACTIVITY>>>>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("AppInfo", "<<<<ON_RESTART LOGIN_ACTIVITY>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AppInfo", "<<<<ON_RESUME LOGIN_ACTIVITY>>>>");
    }

    /**
     * Redireccionamiento a la Activity Register
     *
     * @param view
     */
    public void onClickRegister(View view) {
        Intent intent;
        intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Verificacion de campos ingresados y redireccion a LoginAsyncTask para comunicación con el servidor
     *
     * @param view
     */
    public void onClickLogin(View view) {
        if (checkFields()) {
            new LoginAsyncTask(LoginActivity.this).execute(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
    }

    /**
     * Se dispara la Activity Menu
     *
     * @param strings
     */
    public void runingActivity(String... strings) {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        userEmail = strings[0];
        userToken = strings[2];
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String eventDescription = "User Login " + userEmail + " at " + formatter.format(date).toString();
        new EventAsyncTask(LoginActivity.this).execute(Utils.TYPE_EVENT, eventDescription, userToken);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PhysicalStateActivity.ACTUAL_STEPS, "");
        editor.putString(PhysicalStateActivity.ACTIVE_TIME, "");
        editor.commit();
        startActivity(intent);
        finish();
    }

    /**
     * verificación de los campos y formatos sean los correctos
     *
     * @return
     */
    public boolean checkFields() {
        if (loginEmail.getText().toString().matches("") || loginPassword.getText().toString().matches("")) {
            setAlertText("Error de Logueo!", "Debe completar todos los campos.");
            return false;
        } else if (!Utils.validate(loginEmail.getText().toString())) {
            setAlertText("Error de Logueo!", "Debe ingresar un mail valido.");
            return false;
        } else if (loginPassword.getText().toString().length() < 8) {
            setAlertText("Error de Logueo!", "Debe ingresar una contraseña valida.");
            return false;
        }
        return true;
    }

    /**
     * Mensaje de alerta
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