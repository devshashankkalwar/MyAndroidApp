package android.starlabs.com.androidapps.utils;

/**
 * Created by AveNGeR on 12-07-2016.
 */
public class CheckPassword {
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-_!@#$%^&*()+]{6,16}$";
    public static boolean isValidPassword(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
