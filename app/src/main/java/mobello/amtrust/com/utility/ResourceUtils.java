package mobello.amtrust.com.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.ContextCompatApi24;
import android.util.TypedValue;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 20-May-16.
 */
public class ResourceUtils {

    public static String getString(int resId) {
        return AmTrustApp.getInstance().getString(resId);
    }

    public static int getColor(int resId) {
        if (Build.VERSION.SDK_INT >= 23)
            return ContextCompat.getColor(AmTrustApp.getInstance(), resId);
        else
            return AmTrustApp.getInstance().getResources().getColor(resId);
    }

    public static int getAttribute(int attrId){
        TypedValue typedValue = new TypedValue();
        if (AmTrustApp.getInstance().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true))
            return typedValue.data;
        return Color.TRANSPARENT;
    }

    public static Drawable getDrawble(Context context, int resId){
        if(!Version.isBelowLollipop()){
            return context.getResources().getDrawable(R.drawable.placeholder);
        }else{
            return context.getDrawable(R.drawable.placeholder);
        }
    }
}
