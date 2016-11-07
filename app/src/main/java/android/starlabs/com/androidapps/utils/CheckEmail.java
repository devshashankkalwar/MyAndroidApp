package android.starlabs.com.androidapps.utils;

/**
 * Created by AveNGeR on 12-07-2016.
 */
public class CheckEmail {
    private static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static boolean isValidEmail(String email){
        return  email.matches(EMAIL_REGEX);
    }
}
