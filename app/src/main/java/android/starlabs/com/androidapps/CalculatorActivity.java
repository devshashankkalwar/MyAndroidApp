package android.starlabs.com.androidapps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by AveNGeR on 01-07-2016.
 */

public class CalculatorActivity extends AppCompatActivity {

    private EditText firstNum;
    private EditText secondNum;
    private TextView tv;
    private double num1,num2,result=0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        firstNum = (EditText) findViewById(R.id.etFirstNum);
        firstNum.setTextColor(Color.BLUE);
        secondNum = (EditText) findViewById(R.id.etSecondNum);
        secondNum.setTextColor(Color.BLUE);
        tv = (TextView) findViewById(R.id.tvResult);
        tv.setBackgroundColor(Color.LTGRAY);
    }

    public void doCalculation(View view){
        try {
            num1 = Double.parseDouble(firstNum.getText().toString());
            num2 = Double.parseDouble(secondNum.getText().toString());
            switch(view.getId()){
                case R.id.btnAdd:
                    result=num1+num2;
                    tv.setText(num1+" + "+num2+" = \n"+result);
                    break;
                case R.id.btnSub:
                    result=num1-num2;
                    tv.setText(num1+" - "+num2+" = \n"+result);
                    break;
                case R.id.btnproduct:
                    result=num1*num2;
                    tv.setText(num1+" * "+num2+" = \n"+result);
                    break;
                case R.id.btndivision:
                    result=num1/num2;
                    tv.setText(num1+" / "+num2+" = \n"+result);
                    break;
                case R.id.btnMod:
                    if(num2!=0) {
                        result = num1 % num2;
                        tv.setText(num1 + " % " + num2 + " = \n" + result);
                    }else
                        tv.setText("Second Number shouln't be zero");
                    break;
            }
        }catch (NumberFormatException e) {
            Toast.makeText(CalculatorActivity.this, "Please Enter First Number and Second Number", Toast.LENGTH_LONG).show();
        }
    }


    public void doReset(View view){
        firstNum.setText("");
        secondNum.setText("");
        tv.setText("");
    }

}
