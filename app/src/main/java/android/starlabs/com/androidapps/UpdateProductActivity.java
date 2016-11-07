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

public class UpdateProductActivity extends AppCompatActivity {

    EditText etUpProductId, etUpProductName, etUpNumberofUnits, etUpUnitPrice, etUpVendorName, etUpProductDetail;
    DBAdapter dbAdapter;
    Bundle bundle;
    Spinner UpunitsSpinner;
    ArrayAdapter<CharSequence> adapter;
    String units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        etUpProductId = (EditText) findViewById(R.id.etUpProductId);
        etUpProductName = (EditText) findViewById(R.id.etUpProductName);
        etUpNumberofUnits = (EditText) findViewById(R.id.etUpNumberofUnits);
        etUpUnitPrice = (EditText) findViewById(R.id.etUpUnitPrice);
        etUpVendorName = (EditText) findViewById(R.id.etUpVendorName);
        etUpProductDetail = (EditText) findViewById(R.id.etUpProductDetail);
        UpunitsSpinner = (Spinner) findViewById(R.id.UpunitsSpinner);
        addSpinner();
        dbAdapter = new DBAdapter(getBaseContext());
        bundle = getIntent().getExtras();
        etUpProductId.setText(bundle.getString("id"));
        etUpProductName.setText(bundle.getString("name"));
        etUpNumberofUnits.setText(bundle.getString("numofunits"));
        UpunitsSpinner.setSelection(adapter.getPosition(bundle.getString("units")));
        etUpUnitPrice.setText(bundle.getString("price"));
        etUpVendorName.setText(bundle.getString("vendor"));
        etUpProductDetail.setText(bundle.getString("description"));

    }

    public void addSpinner(){
        adapter = ArrayAdapter.createFromResource(this,R.array.units,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UpunitsSpinner.setAdapter(adapter);
        UpunitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                units= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void doUpdate(View view) {
        if(TextUtils.isEmpty(etUpProductId.getText().toString())||TextUtils.isEmpty(etUpProductName.getText().toString())||
                TextUtils.isEmpty(etUpNumberofUnits.getText().toString())||TextUtils.isEmpty(etUpUnitPrice.getText().toString())) {
            if(TextUtils.isEmpty(etUpProductId.getText().toString()))
                etUpProductId.setError("Cannot be Empty");
            if (TextUtils.isEmpty(etUpProductName.getText().toString()))
                etUpProductName.setError("Cannot be Empty");
            if (TextUtils.isEmpty(etUpNumberofUnits.getText().toString()))
                etUpNumberofUnits.setError("Cannot be Empty");
            if (TextUtils.isEmpty(etUpUnitPrice.getText().toString()))
                etUpUnitPrice.setError("Cannot be Empty");
        }
        else {
            dbAdapter.open();
            boolean isUpdated = dbAdapter.updateProduct(Integer.parseInt(bundle.getString("id")),
                    Integer.parseInt(etUpProductId.getText().toString()),
                    etUpProductName.getText().toString(),
                    Integer.parseInt(etUpNumberofUnits.getText().toString()),
                    units,
                    Double.parseDouble(etUpUnitPrice.getText().toString()),
                    etUpVendorName.getText().toString(),
                    etUpProductDetail.getText().toString()
            );
            if (isUpdated) {
                Log.i("Updation", "1 Row Updated");
                Toast.makeText(UpdateProductActivity.this, "Product Updated succesfully into Database ", Toast.LENGTH_LONG).show();
                dbAdapter.close();
                finish();
            } else {
                Log.i("Updation", "Updation Failed");
                Toast.makeText(UpdateProductActivity.this, "Product not updated Try again.. ", Toast.LENGTH_LONG).show();
            }
        }


    }
}