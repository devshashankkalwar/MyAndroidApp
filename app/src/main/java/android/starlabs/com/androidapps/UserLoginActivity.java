package android.starlabs.com.androidapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by AveNGeR on 09-07-2016.
 */
public class UserLoginActivity extends AppCompatActivity {
    private EditText etUsernameLogin,etPassworLogin;
    private static final String PREFS_LOGIN_DETAILS="LoginDetailsPrefs";
    private SharedPreferences loginDetailsSharedPreferences;
    private String username,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_layout);
        etUsernameLogin=(EditText)findViewById(R.id.etUsernameLogin);
        etPassworLogin=(EditText)findViewById(R.id.etPsswordLogin);
        loginDetailsSharedPreferences =getSharedPreferences(PREFS_LOGIN_DETAILS,MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(loginDetailsSharedPreferences.getBoolean("LOGIN_STATUS",false)){
            Intent intentAppNavigator = new Intent(getBaseContext(),ApplicationNavigatorActivity.class);
            intentAppNavigator.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentAppNavigator);
            finish();
        }
    }



    public void doUserRegistration(View view){
        Intent intentUserRegistration= new Intent(getBaseContext(),UserRegistrationActivity.class);
        startActivity(intentUserRegistration);
    }

    public void doLogin(View view) {
        username = etUsernameLogin.getText().toString();
        password = etPassworLogin.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            if (loginDetailsSharedPreferences.getString(username, "").equals(etPassworLogin.getText().toString())) {
                SharedPreferences.Editor editor = loginDetailsSharedPreferences.edit();
                editor.putBoolean("LOGIN_STATUS", true);
                editor.commit();
                Intent intentAppNavigator = new Intent(getBaseContext(), ApplicationNavigatorActivity.class);
                intentAppNavigator.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentAppNavigator);
                finish();

            } else
                Toast.makeText(UserLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(UserLoginActivity.this, "Please Enter the Username and Pasword", Toast.LENGTH_SHORT).show();
    }


}
