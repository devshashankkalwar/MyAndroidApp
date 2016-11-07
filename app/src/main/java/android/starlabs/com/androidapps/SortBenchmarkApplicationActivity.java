package android.starlabs.com.androidapps;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.starlabs.com.androidapps.utils.Bubblesort;
import android.starlabs.com.androidapps.utils.Calculator;
import android.starlabs.com.androidapps.utils.HeapSort;
import android.starlabs.com.androidapps.utils.Insertionsort;
import android.starlabs.com.androidapps.utils.MergeSort;
import android.starlabs.com.androidapps.utils.Quicksort;
import android.starlabs.com.androidapps.utils.Selectionsort;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * Created by AveNGeR on 04-07-2016.
 */
public class SortBenchmarkApplicationActivity extends AppCompatActivity{

    private static int[] array;
    private int size;
    private EditText arraysize;
    private RadioGroup rgcomplex;
    private TextView statuslabel1, statuslabel2;
    private TextView tvbubsort,tvselsort,tvinsersort,tvquicksort,tvmergesort,tvheapsort;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark_app_layout);
        arraysize =(EditText)findViewById(R.id.etarraysize);
        rgcomplex=(RadioGroup)findViewById(R.id.rgcomplex);
        statuslabel1= (TextView)findViewById(R.id.tvstatus1);
        tvbubsort =(TextView)findViewById(R.id.tvbubsort);
        tvselsort=(TextView)findViewById(R.id.tvselsort);
        tvinsersort=(TextView)findViewById(R.id.tvinsersort);
        tvquicksort=(TextView)findViewById(R.id.tvquicksort);
        tvmergesort=(TextView)findViewById(R.id.tvmergesort);
        tvheapsort=(TextView)findViewById(R.id.tvheapsort);
        statuslabel2=(TextView)findViewById(R.id.tvstatus2);


    }

    public void generateArray(View view){
        try{
            size = Integer.parseInt(arraysize.getText().toString());
            switch(rgcomplex.getCheckedRadioButtonId()){
                case (R.id.rbtnbestcase):
                array = Calculator.createBestCase(size);
                statuslabel1.setText("Best Case Array of Size "+size+" is Generated.");
                    break;

                case (R.id.rbtnavgcase):
                array = Calculator.generateRandom(size);
                statuslabel1.setText("Random Array of Size "+size+" is Generated.");
                    break;

                case (R.id.rbtnworstcase):
                array = Calculator.createWorstCase(size);
                statuslabel1.setText("Worst Case Array of Size "+size+" is Generated.");
                    break;
            }
        }catch (NumberFormatException e){
            Toast.makeText(SortBenchmarkApplicationActivity.this, "Please Enter the Size of the array", Toast.LENGTH_LONG).show();
        }
    }

    public void doBenchmark(final View view) {

            if (array!=null) {
                progressdialog=ProgressDialog.show(this,"BenchMarking","Please wait..",true,false);
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        switch (view.getId()) {
                            case R.id.btnbubsort:
                                setText(tvbubsort,new Bubblesort(array).bubSort());
                                progressdialog.dismiss();
                                break;
                            case R.id.btnselsort:
                                setText(tvselsort,new Selectionsort(array).selSort());
                                progressdialog.dismiss();
                                break;
                            case R.id.btninsersort:
                                setText(tvinsersort,new Insertionsort(array).insertSort());
                                progressdialog.dismiss();
                                break;
                            case R.id.btnquicksort:
                                setText(tvquicksort,new Quicksort(array).sort());
                                progressdialog.dismiss();
                                break;
                            case R.id.btnmergesort:
                                setText(tvmergesort,new MergeSort(array).sort());
                                progressdialog.dismiss();
                                break;
                            case R.id.btnheapsort:
                                setText(tvheapsort,new HeapSort(array).doheapsort());
                                progressdialog.dismiss();
                                break;

                            case R.id.btnbenchmarkall:
                                setText(tvbubsort,new Bubblesort(array).bubSort());
                                setText(tvselsort,new Selectionsort(array).selSort());
                                setText(tvinsersort,new Insertionsort(array).insertSort());
                                setText(tvquicksort,new Quicksort(array).sort());
                                setText(tvmergesort,new MergeSort(array).sort());
                                setText(tvheapsort,new HeapSort(array).doheapsort());
                                progressdialog.dismiss();
                                break;
                        }
                    }
                });
              th.start();
            //statuslabel2.setText("BenchMarking Done!!");
            }
             else{
                    Toast.makeText(SortBenchmarkApplicationActivity.this, "Please generate a valid array", Toast.LENGTH_LONG).show();
                }

   }


    public void setText(final TextView tv, final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(text+" ms");
            }
        });
    }


    public void doReset(View view){
        arraysize.setText("");
        statuslabel1.setText("");
        tvbubsort.setText("");
        tvselsort.setText("");
        tvinsersort.setText("");
        tvquicksort.setText("");
        tvmergesort.setText("");
        tvheapsort.setText("");
        statuslabel2.setText("");
        array=null;

    }
}


