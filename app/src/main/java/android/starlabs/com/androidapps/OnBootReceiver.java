package android.starlabs.com.androidapps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by AveNGeR on 31-08-2016.
 */
public class OnBootReceiver extends BroadcastReceiver {

    private Sensor sensor;
    public static final String PREFS_NAME = "background_Preference";
    SharedPreferences backgroundPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        backgroundPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (backgroundPreferences.getBoolean("launch_onshake", false)) {
            intent = new Intent(context, ShakerService.class);
            context.startService(intent);
            Log.i("services", "Service Starting");

        /*SensorManager sManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(new ShakeEventListener(context), sensor, SensorManager.SENSOR_DELAY_NORMAL); // or other delay*/
        }
    }
}


