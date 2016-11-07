package android.starlabs.com.androidapps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.starlabs.com.androidapps.utils.CheckAge;
import android.starlabs.com.androidapps.utils.CheckEmail;
import android.starlabs.com.androidapps.utils.CheckPassword;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by AveNGeR on 09-07-2016.
 */
public class UserRegistrationActivity extends AppCompatActivity {

    private EditText etDateofBirth;
    int year,date,month;
    private EditText etFirstName,etLastName,etEmail,etNewPassword,etConfirmNewPassword,etMobile,etdob;
    private String fName,lName,eMail,newPassword,confirmPassword,mobile,dob;
    private TextView tvLoginStatus,tvEmail,tvPassword,tvDateOfBirth;
    private ProgressDialog progressdialog;
    private static final String PREFS_TEMP="TempPrefs", PREFS_LOGIN_DETAILS="LoginDetailsPrefs";
    private SharedPreferences tempSharedPreferences,loginDetailsSharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usre_registration_layout);
        etDateofBirth = (EditText) findViewById(R.id.etDateOfBirth);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etemail);
        etNewPassword = (EditText) findViewById(R.id.etnewPassword);
        etConfirmNewPassword = (EditText) findViewById(R.id.etConfirmNewPassword);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etdob = (EditText) findViewById(R.id.etDateOfBirth);
        tvLoginStatus = (TextView) findViewById(R.id.tvLoginStatus);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
        tempSharedPreferences = getSharedPreferences(PREFS_TEMP, MODE_PRIVATE);
        loginDetailsSharedPreferences = getSharedPreferences(PREFS_LOGIN_DETAILS, MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPrevDetails();
    }

    public void getDate(View view) {
        Calendar c= Calendar.getInstance();
        int curr_year= c.get(Calendar.YEAR);
        int curr_month= c.get(Calendar.MONTH);
        int curr_date= c.get(Calendar.DAY_OF_MONTH);
       Dialog dialog = new DatePickerDialog(this,datePickerListener,curr_year,curr_month,curr_date);
        dialog.show();
    }


    DatePickerDialog.OnDateSetListener datePickerListener= new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int selected_year,int selected_month,int selected_day){
                year=selected_year;
                date=selected_day;
                month=selected_month;
                etDateofBirth.setText(new StringBuilder().append(date+"/"+(month+1)+"/"+year));
            }
    };

    public void validateLoginDetails(View view) {
        //progressdialog=ProgressDialog.show(this,"BenchMarking","Please wait..",true,false);
        tvLoginStatus.setText("");
        validateFirstName();
        validateLastName();
        validateEmail();
        validatePassword();
        validateDateofBirth();
        validateMobile();
        if (tvLoginStatus.getText().toString().equals("")) {
            String emailBody = "Hi "+fName+",\n\n\nYou have successfully registered on My Android Apps. Following are your login details.\n" +
                        "Username: " + eMail + "\nPasswrord: " + newPassword + "\n\nRegards, \nStarlabs Development Team.";
            sendMail(eMail, "Registration Successfull", emailBody);
        }
    }

    public void validateFirstName(){
        fName=etFirstName.getText().toString();
        if (fName.equals("")){
            etFirstName.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
            tvLoginStatus.setText("* All Fields are Mandatory");
        }else{
            etFirstName.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
        }
    }

    public void validateLastName(){
            lName=etLastName.getText().toString();
        if(lName.equals("")){
            etLastName.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
            tvLoginStatus.setText("* All Fields are Mandatory");
        }else{
            etLastName.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
        }
    }

    public void validateEmail(){
            eMail=etEmail.getText().toString();
        if (eMail.equals("")){
            etEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
            tvLoginStatus.setText("* All Fields are Mandatory.");
        }else{
            if(CheckEmail.isValidEmail(eMail)){
                tvEmail.setText("");
                etEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
            }else{
                etEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                tvEmail.setText("* Invalid Email");
                tvLoginStatus.setText("* Enter valid details in respective fields ");
            }
        }

    }

    public void validatePassword(){
            newPassword=etNewPassword.getText().toString();
            confirmPassword=etConfirmNewPassword.getText().toString();

        if(newPassword.equals("")||confirmPassword.equals("")){
            etNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
            etConfirmNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
            tvLoginStatus.setText("* All Fields are Mandatory");
        }else {
            if (!newPassword.equals(confirmPassword)) {
                etNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                etConfirmNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                tvPassword.setText("* Password donot match");
                tvLoginStatus.setText("* Enter valid details in respective fields ");
            }
            else if(!CheckPassword.isValidPassword(newPassword)){
                etNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                etConfirmNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                tvPassword.setText("* Password length should be between 6-16 characters");
                tvLoginStatus.setText("* Enter valid details in respective fields ");

            }
            else{
                tvPassword.setText("");
                etNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
                etConfirmNewPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));

            }

        }

    }

    public void validateDateofBirth(){
            dob=etDateofBirth.getText().toString();
            if(dob.equals("")){
            etDateofBirth.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                tvLoginStatus.setText("* All Fields are Mandatory");
        }else {
                if(!CheckAge.isValidAge(year)){
                    etDateofBirth.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                    tvDateOfBirth.setText("* Age should be more than 16 years");
                    tvLoginStatus.setText("* Enter valid details in respective fields ");
                }
                else {
                    tvDateOfBirth.setText("");
                    etDateofBirth.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
                }
            }
    }

    public void validateMobile(){
            mobile=etMobile.getText().toString();
            if(mobile.equals("")){
            etMobile.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                tvLoginStatus.setText("* All Fields are Mandatory");
        }else{
                if(mobile.length()!=10) {
                    etMobile.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_warning));
                    tvLoginStatus.setText("* Enter valid details in respective fields ");
                }else
             etMobile.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded_style));
            }
    }

    public void doResetAll(View view){
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etNewPassword.setText("");
        etConfirmNewPassword.setText("");
        etDateofBirth.setText("");
        etMobile.setText("");
        tvEmail.setText("");
        tvLoginStatus.setText("");
        tvPassword.setText("");
        tvDateOfBirth.setText("");

    }

    public void doResetAll(){
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etNewPassword.setText("");
        etConfirmNewPassword.setText("");
        etDateofBirth.setText("");
        etMobile.setText("");
        tvEmail.setText("");
        tvLoginStatus.setText("");
        tvPassword.setText("");
        tvDateOfBirth.setText("");

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= tempSharedPreferences.edit();
        editor.putString("FNAME",etFirstName.getText().toString());
        editor.putString("LNAME",etLastName.getText().toString());
        editor.putString("EMAIL",etEmail.getText().toString());
        editor.putString("DOB",etDateofBirth.getText().toString());
        editor.putString("MOB",etMobile.getText().toString());
        editor.commit();
    }

    public void setPrevDetails(){
        etFirstName.setText(tempSharedPreferences.getString("FNAME",""));
        etLastName.setText(tempSharedPreferences.getString("LNAME",""));
        etEmail.setText(tempSharedPreferences.getString("EMAIL",""));
        etMobile.setText(tempSharedPreferences.getString("MOB",""));
        etDateofBirth.setText(tempSharedPreferences.getString("DOB",""));
    }

    //Sending Registration Email
    private static final String username = "starlabs.developerteam@gmail.com";
    private static final String password = "starlabs@99";
    private String to, sub, bodyofEmail;
    private Session session;
    ProgressDialog pdialog = null;

    public void sendMail(String to, String sub, String bodyofEmail){
        this.to = to;
        this.sub = sub;
        this.bodyofEmail = bodyofEmail;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        pdialog = ProgressDialog.show(this, "", "Verifying Details...", true);
        RetreiveFeedTask task = new RetreiveFeedTask();
        try{
        task.execute();}catch(Exception e){
            throw new RuntimeException(e);
        }

    }
    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(sub);
                message.setText(bodyofEmail);
                Transport.send(message);
            } catch (Exception e) {
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.dismiss();
                            tvLoginStatus.setText("Registration Unsucessfull. Make sure your internet is working.");
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (tvLoginStatus.getText().toString().equals("")) {
                pdialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(UserRegistrationActivity.this).create();
                // Setting Dialog Title
                alertDialog.setTitle("Registration Successful");
                // Setting Dialog Message
                alertDialog.setMessage("Welcome! Please check your email for Registration details");
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        SharedPreferences.Editor editor= loginDetailsSharedPreferences.edit();
                        editor.putString(etEmail.getText().toString(),etNewPassword.getText().toString());
                        editor.commit();
                        doResetAll();
                        Intent launchNext = new Intent(getApplicationContext(), UserLoginActivity.class);
                        launchNext.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchNext);
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        }
    }
}


