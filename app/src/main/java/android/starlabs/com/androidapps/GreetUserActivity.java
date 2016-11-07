package android.starlabs.com.androidapps;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by AveNGeR on 01-07-2016.
 */
public class GreetUserActivity extends AppCompatActivity {

    private String fName;
    private String lName;
    private EditText etFirstName;
    private EditText etLastName;
    private TextView tvResult,tvvalidateFname,tvvalidateLname;
    private static final String TAG=GreetUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.greet_user_layout);
        super.onCreate(savedInstanceState);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etFirstName.setTextColor(Color.BLUE);
        etFirstName.setBackgroundColor(Color.YELLOW);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etLastName.setTextColor(Color.BLUE);
        etLastName.setBackgroundColor(Color.YELLOW);
        tvResult=(TextView) findViewById(R.id.textView);
        tvvalidateFname =(TextView)findViewById(R.id.tvvalidatefname);
        tvvalidateLname=(TextView)findViewById(R.id.tvvalidatelname);

    }



    public void doGreetUser(View view){
        tvvalidateFname.setText(null);
        tvvalidateLname.setText(null);
        tvResult.setText(null);
        fName= etFirstName.getText().toString();
        lName= etLastName.getText().toString();
        tvResult=(TextView) findViewById(R.id.textView);
        tvResult.setText("");
        if(fName.equals("")||lName.equals("")){
            ObjectAnimator animatefname =ObjectAnimator.ofFloat(etFirstName,"translationX",0,15,-15,0,15,-15,0,15,-15,0,15,-15,0,15,-15,0,15,-15,0);
            animatefname.setDuration(1500);
            ObjectAnimator animatelname =ObjectAnimator.ofFloat(etLastName,"translationX",0,15,-15,0,15,-15,0,15,-15,0,15,-15,0,15,-15,0,15,-15,0);
            animatelname.setDuration(1500);
            animatefname.start();
            tvvalidateFname.setText("* Mandatory");
            animatelname.start();
            tvvalidateLname.setText("* Mandatory");
        }
        else{
            tvResult.setText("Namaste\n"+fName+" "+lName);
            ObjectAnimator animateResult =ObjectAnimator.ofFloat(tvResult,"rotation",0,90,-90,180,-180,270,-270,270,-270,180,-180,90,-90,0);
            animateResult.setDuration(4000);
            animateResult.start();

        }
        Log.i(TAG,"doGreetUser called by user");
    }




}
