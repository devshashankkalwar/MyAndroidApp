package android.starlabs.com.androidapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VendorManagementActivity extends AppCompatActivity {

    private DBAdapter dbAdapter;
    ListView lvProductList;
    Product product;
    EditText etSearch;
    List<Product> listOfProducts;
    ArrayAdapter<Product> adapter;
    List<Product> copyofProductlist;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_management);
        dbAdapter = new DBAdapter(this);
        lvProductList = (ListView) findViewById(R.id.productList);

        etSearch =(EditText)findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                filterList(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbAdapter.open();
        copyofProductlist = dbAdapter.getAllProducts();
        dbAdapter.close();
    }

    private void filterList(String text) {
        text=text.toLowerCase(Locale.getDefault());
        listOfProducts.clear();
        if (text.equals(""))
            listOfProducts.addAll(copyofProductlist);
        else{
            for(Product product:copyofProductlist){
                if(product.getProduct_Name().toLowerCase(Locale.getDefault()).contains(text))
                    listOfProducts.add(product);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbAdapter.open();
        listOfProducts = dbAdapter.getAllProducts();
        adapter = new ArrayAdapter<Product>(getBaseContext(), android.R.layout.simple_list_item_1, listOfProducts);
        lvProductList.setAdapter(adapter);
        lvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                product = (Product) lvProductList.getItemAtPosition(itemPosition);
                String productDetails = "ID :"+product.getId() + "\nName :" + product.getProduct_Name() + "\nNum Of Units :" + product.getNumOfUnits()+" "+product.getUnits() + "\nUnit Price : Rs." + product.getUnitPrice()+
                                            "\nVendor Name :"+product.getVendor_Name()+"\nProduct Details :"+product.getProd_desc();
                showProductDetails("Product Details",productDetails,product);
            }
        });
    }

    public void showProductDetails(String title, String productDetails, final Product product){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(productDetails);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent updateActivityIntent = new Intent(getBaseContext(),UpdateProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",String.valueOf(product.getId()));
                bundle.putString("name",product.getProduct_Name());
                bundle.putString("numofunits",String.valueOf(product.getNumOfUnits()));
                bundle.putString("units",product.getUnits());
                bundle.putString("price",String.valueOf(product.getUnitPrice()));
                bundle.putString("vendor",product.getVendor_Name());
                bundle.putString("description",product.getProd_desc());
                updateActivityIntent.putExtras(bundle);
                startActivity(updateActivityIntent);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDeleted = dbAdapter.deleteProduct(""+product.getId());
                if (isDeleted==true) {
                    Log.i("Deletion", "1 Row Deleted");
                    onResume();
                    Toast.makeText(VendorManagementActivity.this, "Product Deleted Successfully ", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.i("Deletion", "Deletion Failed");
                    Toast.makeText(VendorManagementActivity.this, "Product Not Deleted ", Toast.LENGTH_LONG).show();
                    }
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("About");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("About")){
            Intent intentAboutVendorManagementApp= new Intent(getBaseContext(),AboutVendorManagement.class);
            startActivity(intentAboutVendorManagementApp);
        }
        return super.onOptionsItemSelected(item);
    }

    public void addProduct(View view) {
        Intent intentaddProduct = new Intent(getBaseContext(), AddProductActivity.class);
        startActivity(intentaddProduct);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbAdapter.close();
    }
}


