package android.starlabs.com.androidapps;

/**
 * Created by AveNGeR on 12-08-2016.
 */
public class Product {

    private String product_Name;
    private String vendor_Name;
    private String prod_desc;
    private String units;
    private int id;
    private int numOfUnits;
    private double unitPrice;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getVendor_Name() {
        return vendor_Name;
    }

    public void setVendor_Name(String vendor_Name) {
        this.vendor_Name = vendor_Name;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return product_Name;
    }
}
