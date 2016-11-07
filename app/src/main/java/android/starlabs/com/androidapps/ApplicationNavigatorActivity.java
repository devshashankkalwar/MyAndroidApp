package android.starlabs.com.androidapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by AveNGeR on 04-07-2016.
 */
public class ApplicationNavigatorActivity extends AppCompatActivity {

    public SharedPreferences backgroundPreferences;
    public static final String PREFS_NAME="background_Preference";
    LinearLayout navigatorLayout;
    private ImageButton btnCalculator,btnWhatsapp,btnGallery,btnVendordb,btnPhotoEdit,btnAlertApp;
    private Button  btnGreetUser, btnSortBenchmark,btnAboutActors;
    SharedPreferences loginDetailsSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainapp_navigator);
        btnCalculator = (ImageButton)findViewById(R.id.btnCalculator);
        btnSortBenchmark = (Button) findViewById(R.id.btnSortBenchmark);
        btnGreetUser = (Button) findViewById(R.id.btnGreetUser);
        btnWhatsapp = (ImageButton) findViewById(R.id.btnWhatsapp);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);
        btnVendordb =(ImageButton)findViewById(R.id.btnVendordb);
        btnPhotoEdit =(ImageButton)findViewById(R.id.btnPhotoEdit);
        btnAlertApp =(ImageButton)findViewById(R.id.btnAlertApp);
        btnAboutActors =(Button)findViewById(R.id.btnAboutActors);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        navigatorLayout = (LinearLayout) findViewById(R.id.navigatorLayout);
        navigatorLayout.setBackgroundResource(backgroundPreferences.getInt("background",R.drawable.background));
        btnCalculator.setBackgroundColor(backgroundPreferences.getInt("tile_color",Color.CYAN));
        btnGreetUser.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnSortBenchmark.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnWhatsapp.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnGallery.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnVendordb.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnAlertApp.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnPhotoEdit.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
        btnAboutActors.setBackgroundColor(backgroundPreferences.getInt("tile_color", Color.CYAN));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Settings");
        menu.add("About");
        menu.add("Help");
        menu.add("Logout");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Settings")){
            Intent intentSettings= new Intent(getBaseContext(),SettingsActivity.class);
            startActivity(intentSettings);
        }
        if(item.getTitle().equals("About")){
            Intent intentAbout= new Intent(getBaseContext(),AboutActivity.class);
            startActivity(intentAbout);
        }
        if(item.getTitle().equals("Help")){
            Intent intentHelp= new Intent(getBaseContext(),HelpActivity.class);
            startActivity(intentHelp);
        }
        if(item.getTitle().equals("Logout")){
            loginDetailsSharedPreferences=getSharedPreferences("LoginDetailsPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor= loginDetailsSharedPreferences.edit();
            editor.putBoolean("LOGIN_STATUS",false);
            editor.commit();
            Intent launchNext = new Intent(getApplicationContext(), UserLoginActivity.class);
            launchNext.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(launchNext);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void doNavigation(View view){

        switch(view.getId()){

            case R.id.btnGreetUser:
                Intent intentGreetUser= new Intent(getBaseContext(),GreetUserActivity.class);
                startActivity(intentGreetUser);
                break;
            case R.id.btnCalculator:
                Intent intentSimpleCalc= new Intent(getBaseContext(),CalculatorActivity.class);
                startActivity(intentSimpleCalc);
                break;
            case R.id.btnSortBenchmark:
                Intent intentBenchmarkApp= new Intent(getBaseContext(),SortBenchmarkApplicationActivity.class);
                startActivity(intentBenchmarkApp);
                break;
            case R.id.btnGallery:
                Intent intentGallery=new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                intentGallery.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentGallery);
                break;
            case R.id.btnWhatsapp:
                Intent intentWhatsapp = new Intent();
                intentWhatsapp.setAction(Intent.ACTION_SEND);
                intentWhatsapp.putExtra(Intent.EXTRA_TEXT, "Hey Whats up..");
                intentWhatsapp.setType("text/plain");
                intentWhatsapp.setPackage("com.whatsapp");
                intentWhatsapp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentWhatsapp);
                break;
            case R.id.btnVendordb:
                Intent intentVendordb = new Intent(getBaseContext(),VendorManagementActivity.class);
                startActivity(intentVendordb);
                break;
            case R.id.btnPhotoEdit:
                Intent intentPhotEdit = new Intent(getBaseContext(),PhotoEditorActivity.class);
                startActivity(intentPhotEdit);
                break;
            case R.id.btnAboutActors:
                Intent intentAboutActors = getPackageManager().getLaunchIntentForPackage("starlabs.org.aboutactors");
                if(intentAboutActors!=null)
                    startActivity(intentAboutActors);
                else
                    Toast.makeText(this,"The Apllication is not found. Make sure it is installed on the device",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(backgroundPreferences.getBoolean("launch_onshake",false)) {
            Intent intent = new Intent(getBaseContext(), ShakerService.class);
            startService(intent);
            Log.i("services", "Service Starting");
        }
    }
}
