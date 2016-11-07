package android.starlabs.com.androidapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AveNGeR on 11-08-2016.
 */
public class DBAdapter {
    private static final String DB_NAME="Product_Database.db";
    private static final int DB_VERSION = 1;

    private Context context;
    private DBHelper dbhelper;

    private static final String TABLE_NAME="Product_Table";
    private static final String ID="ID";
    private static final String PROD_NAME="ProductName";
    private static final String NUM_OF_UNITS="NumOfUnits";
    private static final String UNITS="Units";
    private static final String UNIT_PRICE="UnitPrice";
    private static final String VENDOR_NAME="VendorName";
    private static final String PROD_DESC="Description";

    private static final String CREATE_TABLE="Create table if not exists "+TABLE_NAME+" ( "+ID+" Integer Primary Key Autoincrement, "+
            PROD_NAME+" Text Unique, "+NUM_OF_UNITS+" Integer, "+UNITS+" Text, "+UNIT_PRICE+" Decimal, "+VENDOR_NAME+" Text, "+PROD_DESC+" Text);";

    private SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context=context;
        dbhelper=new DBHelper();
    }

    public boolean saveProduct(String pName, int numofUnits,String units, double price, String vName, String prod_Description){
        ContentValues contentValues=new ContentValues();
        contentValues.put(PROD_NAME,pName);
        contentValues.put(NUM_OF_UNITS,numofUnits);
        contentValues.put(UNITS,units);
        contentValues.put(UNIT_PRICE,price);
        contentValues.put(VENDOR_NAME,vName);
        contentValues.put(PROD_DESC,prod_Description);

        return db.insert(TABLE_NAME,null,contentValues)>0?true:false;
    }

    public boolean deleteProduct(String id){
        return db.delete(TABLE_NAME, " ID = ?", new String[]{id}) > 0?true:false;
    }

    public boolean updateProduct(int oldID,int newID, String pName, int numofUnits,String units, double price, String vName, String prod_Description){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,newID);
        contentValues.put(PROD_NAME,pName);
        contentValues.put(NUM_OF_UNITS,numofUnits);
        contentValues.put(UNITS,units);
        contentValues.put(UNIT_PRICE,price);
        contentValues.put(VENDOR_NAME,vName);
        contentValues.put(PROD_DESC,prod_Description);

        return db.update(TABLE_NAME, contentValues, "ID =?", new String[]{String.valueOf(oldID)}) > 0;
    }

    public List<Product> getAllProducts(){
        List<Product> listOfProducts= new ArrayList<Product>();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,PROD_NAME,NUM_OF_UNITS,UNITS,UNIT_PRICE,VENDOR_NAME,PROD_DESC},null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Product product = cursorToProduct(cursor);
            listOfProducts.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return listOfProducts;
    }

    private Product cursorToProduct(Cursor cursor){
        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setProduct_Name(cursor.getString(1));
        product.setNumOfUnits(cursor.getInt(2));
        product.setUnits(cursor.getString(3));
        product.setUnitPrice(cursor.getDouble(4));
        product.setVendor_Name(cursor.getString(5));
        product.setProd_desc(cursor.getString(6));
        return product;

    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper() {
            super(context, DB_NAME,null,DB_VERSION);
            Log.i("Database_operation","Database Created");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Log.i("Table_operation","table Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(DBAdapter.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }
    }

    public void open(){
        db = dbhelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

}
