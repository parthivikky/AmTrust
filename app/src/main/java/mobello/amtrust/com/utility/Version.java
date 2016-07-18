package mobello.amtrust.com.utility;

import android.os.Build;

/**
 * Created by Parthi on 05-Jul-16.
 */
public class Version {

    public static boolean isBelowLollipop(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? true : false;
    }

    public static boolean isBelowKitkat(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ? true : false;
    }

    public static boolean isEqualOrBelowKitkat(){
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT ? true : false;
    }

    public static boolean isBelowMarshmallow(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ? true : false;
    }
}
