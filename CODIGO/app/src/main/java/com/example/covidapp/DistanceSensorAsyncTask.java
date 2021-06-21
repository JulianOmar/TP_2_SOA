package com.example.covidapp;

import android.os.AsyncTask;
import android.util.Log;

public class DistanceSensorAsyncTask extends AsyncTask<Void, Void, Boolean> {


    private long initialTime;
    private com.example.covidapp.MenuActivity menuActivity;

    public DistanceSensorAsyncTask(com.example.covidapp.MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        this.initialTime = System.currentTimeMillis();
        long actualTime;
        while ((actualTime = (System.currentTimeMillis() - initialTime)) <= 1000 && menuActivity.isCloseDistance()) {

        }
        if (actualTime > 1000) {
            Log.i("Debug34", String.valueOf(actualTime));
            return false;
        } else {
            Log.i("Debug39", String.valueOf(actualTime));
            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            this.menuActivity.runingAutoTestActivity();
        }
    }
}