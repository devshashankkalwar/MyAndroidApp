package android.starlabs.com.androidapps.utils;

import java.util.Calendar;

/**
 * Created by AveNGeR on 12-07-2016.
 */
public class CheckAge {
    public static boolean isValidAge(int year){
        Calendar c =Calendar.getInstance();
        int curr_year=c.get(Calendar.YEAR);
        if(curr_year-year>=16)
            return true;
        else
            return false;
    }
}
