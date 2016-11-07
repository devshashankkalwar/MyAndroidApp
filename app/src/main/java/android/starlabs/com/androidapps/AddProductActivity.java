package android.starlabs.com.androidapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    EditText etProductName,etNumberofUnits,etUnitPrice,etVendorName,etProductDetail;
    DBAdapter dbAdapter;
    Spinner unitsSpinner;
    ArrayAdapter<CharSequence> adapter;
    String units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etProductName=(EditText)findViewById(R.id.etProductName);
        etNumberofUnits=(EditText)findViewById(R.id.etNumberofUnits);
        etUnitPrice=(EditText)findViewById(R.id.etUnitPrice);
        etVendorName=(EditText)findViewById(R.id.etVendorName);
        etProductDetail=(EditText)findViewById(R.id.etProductDetail);
        dbAdapter = new DBAdapter(getBaseContext());
        unitsSpinner =(Spinner)findViewById(R.id.unitsSpinner);
        addSpinner();
    }

    public void addSpinner(){
        adapter = ArrayAdapter.createFromResource(this,R.array.units,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(adapter);
        unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                units= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveNewProduct(View view){
        if(TextUtils.isEmpty(etProductName.getText().toString())||TextUtils.isEmpty(etNumberofUnits.getText().toString())||
                TextUtils.isEmpty(etUnitPrice.getText().toString())) {
            if(TextUtils.isEmpty(etProductName.getText().toString()))
                etProductName.setError("Cannot be Empty");
            if (TextUtils.isEmpty(etNumberofUnits.getText().toString()))
                etNumberofUnits.setError("Cannot be Empty");
            if (TextUtils.isEmpty(etUnitPrice.getText().toString()))
                etUnitPrice.setError("Cannot be Empty");
        }
        else {
            dbAdapter.open();
            boolean isInserted = dbAdapter.saveProduct(etProductName.getText().toString(),
                    Integer.parseInt(etNumberofUnits.getText().toString()),
                    units,
                    Double.parseDouble(etUnitPrice.getText().toString()),
                    etVendorName.getText().toString(),
                    etProductDetail.getText().toString());
            if (isInserted) {
                Log.i("Insertion", "1 Row Inserted");
                Toast.makeText(AddProductActivity.this, "Product added succesfully into Database ", Toast.LENGTH_LONG).show();
                dbAdapter.close();
                finish();
            } else {
                Log.i("Insertion", "Insertion failed");
                Toast.makeText(AddProductActivity.this, "Product not added... Try Again...Check if same product is already there in DB ", Toast.LENGTH_LONG).show();
            }

        }
    }
}
