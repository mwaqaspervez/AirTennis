package com.mwaqaspervez.airtennis.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mwaqaspervez.airtennis.R;
import com.mwaqaspervez.airtennis.Utils.NetworkSocket;

import org.json.JSONObject;

public class GameActivity extends AppCompatActivity implements SensorEventListener {


    private Sensor sensor;
    private SensorManager manager;
    private JSONObject object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        object = new JSONObject();
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        double force = 0.57 * Math.sqrt((event.values[0] * event.values[0]) + (event.values[1] * event.values[1]) + (event.values[2] * event.values[2]));
        //  Log.i(TAG + "Force: ", "" + force);
        if (force > 7.0) {
            try {
                object.put("socketId", getIntent().getStringExtra("QRString"));
                object.put("force", force);
                object.put("player", getIntent().getStringExtra("player"));
                NetworkSocket.getInstance().getSocket().emit("Force", object);
                manager.unregisterListener(this);
                Thread.sleep(2000);
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (manager != null)
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (manager != null)
            manager.unregisterListener(this);
    }
}
