package mobello.amtrust.com.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Parthi on 27-May-16.
 */
public class AMSTextView extends TextView {

    private static final String asset = "OpenSans.ttf";

    public AMSTextView(Context context) {
        super(context);
        setCustomFont(context);
    }

    public AMSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    private boolean setCustomFont(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Regular.ttf");
        setTypeface(typeface);
        return true;
    }
}
