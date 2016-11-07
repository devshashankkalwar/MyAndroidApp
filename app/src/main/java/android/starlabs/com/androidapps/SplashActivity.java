package android.starlabs.com.androidapps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo=(ImageView)findViewById(R.id.ivlogo);
        Animation animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.splash_animation);
        //animation.setDuration(2000);
        ivLogo.startAnimation(animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getBaseContext(),UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        },6000);
    }
}
