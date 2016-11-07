package android.starlabs.com.androidapps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class  PhotoEditorActivity extends AppCompatActivity {

    private ImageView ivFilterImage;
    private ImageButton ibtnGrayScale, ibtnBW, ibtnOriginal,ibtnSepia,ibtnSat,ibtnCustom,ibtnCustom1,ibtnCustom2,ibtnCustom3;
    public Bitmap bitmap;
    private static final String TAG = PhotoEditorActivity.class.getSimpleName();
    final int ACTIVITY_CAPTURE_IMAGE=1,ACTIVITY_SELECT_IMAGE=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);
        ivFilterImage=(ImageView)findViewById(R.id.ivImage);
        ibtnGrayScale=(ImageButton)findViewById(R.id.ibtnGrayScale);
        ibtnBW=(ImageButton)findViewById(R.id.ibtnBW);
        ibtnOriginal=(ImageButton)findViewById(R.id.ibtnOriginal);
        ibtnSepia=(ImageButton)findViewById(R.id.ibtnSepia);
        ibtnCustom=(ImageButton)findViewById(R.id.ibtnCustom);
        ibtnCustom1=(ImageButton)findViewById(R.id.ibtnCustom1);
        ibtnCustom2=(ImageButton)findViewById(R.id.ibtnCustom2);
        ibtnCustom3=(ImageButton)findViewById(R.id.ibtnCustom3);
        ibtnSat=(ImageButton)findViewById(R.id.ibtnSat);
        applyFilter();
    }

    public void getImage(View view){
        switch (view.getId()){
            case R.id.ibtnCameraLauncher:
                Intent intentcamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera, ACTIVITY_CAPTURE_IMAGE);
                break;
            case R.id.ibtnGallaryLauncher:
                Intent intentGallary=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallary, ACTIVITY_SELECT_IMAGE);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_CAPTURE_IMAGE && resultCode == RESULT_OK){
            Log.i(TAG,"Image captured from camera");
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            ivFilterImage.setImageBitmap(bp);

        }
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ivFilterImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

   protected void onStart() {
        super.onStart();



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            try{
                Intent imageRecievedIntent = getIntent();

                String intentAction = imageRecievedIntent.getAction();
                String intentType = imageRecievedIntent.getType();
                if (Intent.ACTION_SEND.equals(intentAction) && intentType != null) {
                    Uri contentUri = imageRecievedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                    ivFilterImage.setImageURI(contentUri);
                }
            }catch (Exception e){
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        } else {
            Intent imageRecievedIntent = getIntent();
            Uri uri = (Uri) imageRecievedIntent.getExtras().get("data");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            ivFilterImage.buildDrawingCache();
            bitmap = ivFilterImage.getDrawingCache();

            /*****************************/
            Bitmap original = toOriginal(bitmap);
            ibtnOriginal.setImageBitmap(original);

            /***********/
            Bitmap bw = toBW(bitmap);
            ibtnBW.setImageBitmap(bw);
            /********************/
            Bitmap grayBitmap = toGrayScale(bitmap);
            ibtnGrayScale.setImageBitmap(grayBitmap);

            /************************/
            Bitmap sepiaBitmap = toSephia(bitmap);
            ibtnSepia.setImageBitmap(sepiaBitmap);

            /************************/
            Bitmap customBitmap = toCustom(bitmap);
            ibtnCustom.setImageBitmap(customBitmap);

            /************************/
            Bitmap custom1Bitmap = toCustom1(bitmap);
            ibtnCustom1.setImageBitmap(custom1Bitmap);

            /************************/
            Bitmap custom2Bitmap = toCustom2(bitmap);
            ibtnCustom2.setImageBitmap(custom2Bitmap);

            /************************/
            Bitmap custom3Bitmap = toCustom3(bitmap);
            ibtnCustom3.setImageBitmap(custom3Bitmap);

            /************************/
            Bitmap satBitmap = toCustom3(bitmap);
            ibtnSat.setImageBitmap(satBitmap);

            /************************/
        }
    }

    public void applyFilter()
    {
        ibtnOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap original=toOriginal(bitmap);
                ivFilterImage.setImageBitmap(original);
            }

        });
        /////////////////////////////////////////
        ibtnBW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bw=toBW(bitmap);
                ivFilterImage.setImageBitmap(bw);

            }
        });
        ////////////////////////////////////////////
        ibtnGrayScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap grayBitmap=toGrayScale(bitmap);
                ivFilterImage.setImageBitmap(grayBitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnSepia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap sepiaBitmap=toSephia(bitmap);
                ivFilterImage.setImageBitmap(sepiaBitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap customBitmap=toCustom(bitmap);
                ivFilterImage.setImageBitmap(customBitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnCustom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap custom1Bitmap=toCustom1(bitmap);
                ivFilterImage.setImageBitmap(custom1Bitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnCustom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap custom2Bitmap=toCustom2(bitmap);
                ivFilterImage.setImageBitmap(custom2Bitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnCustom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap custom3Bitmap=toCustom3(bitmap);
                ivFilterImage.setImageBitmap(custom3Bitmap);
            }
        });
        //////////////////////////////////////////////////
        ibtnSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap satBitmap=toSat(bitmap);
                ivFilterImage.setImageBitmap(satBitmap);
            }
        });
        //////////////////////////////////////////////////

    }

    public Bitmap toOriginal(Bitmap bitmapOriginal) {

        int width, height;
        height = bitmapOriginal.getHeight();
        width = bitmapOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(1);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmapOriginal, 0, 0, paint);

        return bmpGrayscale;

    }

    public Bitmap toGrayScale(Bitmap bitmapOriginal) {

        int width, height;
        height = bitmapOriginal.getHeight();
        width = bitmapOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrix matB=new ColorMatrix();
        matB.setScale(1f, .95f, .82f, 1.0f);
        cm.setConcat(cm,matB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmapOriginal, 0, 0, paint);

        return bmpGrayscale;
    }

    public Bitmap toBW(Bitmap bitmapOriginal) {
        int width, height;
        height = bitmapOriginal.getHeight();
        width = bitmapOriginal.getWidth();
        Bitmap bmpMonochrome = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpMonochrome);
        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(2);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));
        canvas.drawBitmap(bitmapOriginal,0,0,paint);
        return bmpMonochrome;
    }

    public Bitmap toSat(Bitmap bitmapOriginal) {
        int width, height;
        height = bitmapOriginal.getHeight();
        width = bitmapOriginal.getWidth();
        Bitmap bmpMonochrome = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpMonochrome);
        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(2);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));
        canvas.drawBitmap(bitmapOriginal,0,0,paint);
        return bmpMonochrome;
    }

    public Bitmap toSephia(Bitmap bmpOriginal) {

        float[] sepMat = {
                0.3930000066757202f,
                0.7689999938011169f,
                0.1889999955892563f,
                0,
                0,
                0.3490000069141388f,
                0.6859999895095825f,
                0.1679999977350235f,
                0,
                0,
                0.2720000147819519f,
                0.5339999794960022f,
                0.1309999972581863f,
                0,
                0,
                0,
                0,
                0,
                1,
                0,
                0,
                0,
                0,
                0,
                1,0,0,0,0,1,1,1,1};

        ColorMatrix sepiaMatrix = new ColorMatrix();
        sepiaMatrix.set(sepMat);

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(sepiaMatrix);
        Bitmap rBitmap = bmpOriginal.copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);

        Canvas myCanvas = new Canvas(rBitmap);
        myCanvas.drawBitmap(rBitmap, 0, 0, paint);
        return rBitmap;

    }

    public Bitmap toCustom(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(2);
        cm.setScale(1f,1f,2,5);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public Bitmap toCustom1(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(2);
        cm.setScale(3f,2f,4f,4f);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public Bitmap toCustom2(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrix cm1=new ColorMatrix(cm);
        cm1.setScale(1,2f,1f,3);
        cm1.setConcat(cm,cm1);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm1);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public Bitmap toCustom3(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(1);
        ColorMatrix cm1=new ColorMatrix(cm);
        cm1.setScale(1,0.9f,0.9f,5);
        cm1.setConcat(cm,cm1);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm1);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public void saveImage(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Save Image to SD Card");
        final EditText imageName = new EditText(this);
        imageName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(imageName);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = imageName.getText().toString();
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String description = df.format(Calendar.getInstance().getTime());
                ivFilterImage.setDrawingCacheEnabled(true);
                Bitmap bm = ivFilterImage.getDrawingCache();
                String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(),bm,m_Text, description );
                Uri uri = Uri.parse(imagePath);
                Toast.makeText(PhotoEditorActivity.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();
                Intent intentGallery=new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(intentGallery);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}

