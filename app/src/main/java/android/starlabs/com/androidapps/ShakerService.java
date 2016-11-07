package android.starlabs.com.androidapps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by AveNGeR on 31-08-2016.
 */
public class ShakerService extends Service implements SensorEventListener {

    SensorManager sManager;
    //ShakeEventListner shakeEventListener;
    private static final float SHAKE_THRESHOLD = 800;
    private long lastUpdate;

    private float x;
    private float y;
    private float z;

    private float last_x;
    private float last_y;
    private float last_z;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i("services","Starting shake services");
    }

    /*@Override
   /* public int onStartCommand(Intent intent, int flags, int startId) {
        //shakeEventListener = new ShakeEventListner();
        sManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i("services","Starting shake services");
        return S;
    }*/

    /*@Override
    /*public void onDestroy() {
        sManager.unregisterListener(shakeEventListener);
    }*/



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            x = sensorEvent.values[SensorManager.DATA_X];
            y = sensorEvent.values[SensorManager.DATA_Y];
            z = sensorEvent.values[SensorManager.DATA_Z];

            float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                Intent intent = new Intent(getBaseContext(),SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d("sensor", "shake detected w/ speed: " + speed);
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}


