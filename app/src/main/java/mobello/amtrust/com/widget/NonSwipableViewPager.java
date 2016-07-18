package mobello.amtrust.com.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Parthi on 01-Jun-16.
 */
public class NonSwipableViewPager extends ViewPager {

    private boolean isEnabled;

    public NonSwipableViewPager(Context context) {
        super(context);
        this.isEnabled = true;
    }

    public NonSwipableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
