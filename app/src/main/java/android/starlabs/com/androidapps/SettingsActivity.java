package android.starlabs.com.androidapps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

/**
 * Created by AveNGeR on 13-07-2016.
 */
public class SettingsActivity extends AppCompatActivity {

    LinearLayout ltsettings;
    RadioGroup rgTileeColor;
    RadioGroup rgBackground;
    Switch shakeSwitch;
    SharedPreferences backgroundPreferences;
    public static final String PREFS_NAME = "background_Preference";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        backgroundPreferences = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        ltsettings = (LinearLayout)findViewById(R.id.ltsettings);
        ltsettings.setBackgroundResource(backgroundPreferences.getInt("background", R.drawable.background));
        rgTileeColor = (RadioGroup) findViewById(R.id.rgTileeColor);
        rgBackground = (RadioGroup) findViewById(R.id.rgBackground);
        shakeSwitch = (Switch) findViewById(R.id.shakeswitch);
        setLaunchOptions();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(backgroundPreferences.getBoolean("launch_onshake",false))
            shakeSwitch.setChecked(true);
        else
            shakeSwitch.setChecked(false);
    }

    public void setBackground(View view) {
        SharedPreferences.Editor editor = backgroundPreferences.edit();
        switch (rgBackground.getCheckedRadioButtonId()) {
            case R.id.rbBeach:
                ltsettings.setBackgroundResource(R.drawable.beach);
                editor.putInt("background", R.drawable.beach);
                editor.commit();
                break;
            case R.id.rbSunset:
                ltsettings.setBackgroundResource(R.drawable.sunset);
                editor.putInt("background", R.drawable.sunset);
                editor.commit();
                break;
            case R.id.rbStones:
                ltsettings.setBackgroundResource(R.drawable.stones);
                editor.putInt("background", R.drawable.stones);
                editor.commit();
                break;
            case R.id.rbFlowers:
                ltsettings.setBackgroundResource(R.drawable.flowers);
                editor.putInt("background", R.drawable.flowers);
                editor.commit();
                break;
            case R.id.rbWaterdrops:
                ltsettings.setBackgroundResource(R.drawable.waterdrops);
                editor.putInt("background", R.drawable.waterdrops);
                editor.commit();
                break;
            case R.id.rbdefault:
                ltsettings.setBackgroundResource(R.drawable.background);
                editor.putInt("background", R.drawable.background);
                editor.commit();
                break;
        }


    }
    public void setTileColor(View view){
        SharedPreferences.Editor editor = backgroundPreferences.edit();
        switch(rgTileeColor.getCheckedRadioButtonId()){
            case R.id.rbRed:
                editor.putInt("tile_color",Color.RED);
                editor.commit();
                break;
            case R.id.rbGreen:
                editor.putInt("tile_color",Color.GREEN);
                editor.commit();
                break;
            case R.id.rbYellow:
                editor.putInt("tile_color",Color.YELLOW);
                editor.commit();
                break;
            case R.id.rbGrey:
                editor.putInt("tile_color",Color.LTGRAY);
                editor.commit();
                break;
            case R.id.rbPink:
                editor.putInt("tile_color",Color.MAGENTA);
                editor.commit();
                break;
            case R.id.rbBLue:
                editor.putInt("tile_color",Color.CYAN);
                editor.commit();
                break;
            case R.id.rbTransparent:
                int color = Color.parseColor("#46fffdb8");
                editor.putInt("tile_color",color);
                editor.commit();
                break;
        }
    }

    public void setLaunchOptions(){
        shakeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = backgroundPreferences.edit();
                    editor.putBoolean("launch_onshake", true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = backgroundPreferences.edit();
                    editor.putBoolean("launch_onshake", false);
                    editor.commit();
                }
            }
        });

    }
}
