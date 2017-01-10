package com.mwaqaspervez.airtennis.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mwaqaspervez.airtennis.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private static final String TAG = "Debugger";
    private Socket mSocket;
    private Sensor sensor;
    private SensorManager manager;
    private String QRCodeString;
    private JSONObject object;

    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject object = (JSONObject) args[0];
            try {
                Log.i(TAG, object.getString("code"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.bt_scan_code).setOnClickListener(this);
        findViewById(R.id.bt_how_to_play).setOnClickListener(this);
        findViewById(R.id.bt_desktop_app).setOnClickListener(this);
        findViewById(R.id.bt_about_us).setOnClickListener(this);
        object = new JSONObject();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            else {
                QRCodeString = result.getContents();
                Log.i("Socket", "connecting");

                try {
                    mSocket = IO.socket("http://192.168.1.5:4050");
                    Log.i(TAG + "Socket:", "Initialized");
                } catch (Exception e) {
                    Log.e(TAG + "SocketError : ", e.getMessage());
                }
                if (!mSocket.connected())
                    mSocket.connect();

                mSocket.on("connected", listener);
                manager = (SensorManager) getSystemService(SENSOR_SERVICE);
                sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onSensorChanged(SensorEvent event) {

        double force = 0.57 * Math.sqrt((event.values[0] * event.values[0]) + (event.values[1] * event.values[1]) + (event.values[2] * event.values[2]));
        Log.i(TAG + "Force: ", "" + force);
        if (force > 7.0) {
            try {
                object.put("socketId", QRCodeString);
                object.put("force", force);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("Force", object);
            manager.unregisterListener(this);
            try {
                Thread.sleep(1000);
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {

            case R.id.bt_scan_code:
                new IntentIntegrator(this).
                        setBeepEnabled(false).
                        initiateScan();
                break;

            case R.id.bt_how_to_play:
                startActivity(new Intent(this, HowToPlay.class));
                break;

            case R.id.bt_desktop_app:

                new AlertDialog.Builder(this)
                        .setTitle("Get App")
                        .setMessage("Link: " + getResources().getString(R.string.desktop_app))
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Copy To ClipBoard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Website", getResources().getString(R.string.desktop_app));
                                clipboard.setPrimaryClip(clip);
                                Snackbar.make(view, "Copied !", Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .create().show();
                break;
            
            case R.id.bt_about_us:
                startActivity(new Intent(this, AboutUs.class));
                break;
        }
    }
}