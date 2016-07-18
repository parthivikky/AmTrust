package mobello.amtrust.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.security.InvalidParameterException;

import mobello.amtrust.com.R;


public class BlurredImageView extends ImageView {

    public boolean isBlurred = false;

    public float radius, downSampling = 1;
    public boolean keepOriginal = false;

    public Bitmap bitmap;

    public BlurredImageView(Context context) {
        super(context);
        antiPreview();
    }

    public BlurredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (antiPreview()) return;
        final TypedArray obtained = context.obtainStyledAttributes(attrs, R.styleable.BlurredImageView);
        setRadius(obtained.getFloat(R.styleable.BlurredImageView_radius, radius));
        keepOriginal = obtained.getBoolean(R.styleable.BlurredImageView_keepOriginal, keepOriginal);
        downSampling = obtained.getFloat(R.styleable.BlurredImageView_downSampling, downSampling);
    }

    /**
     * Preview crashes in RenderScript code
     */
    boolean antiPreview() {
        if (isInEditMode()) setRadius(0);
        return isInEditMode();
    }

    public void setRadius(float radius) {
        if (radius < 0 || radius > 1) throw new InvalidParameterException();
        this.radius = radius;
        isBlurred = false;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int blurRadius;
        if (isBlurred || radius <= 0 || radius > 1) return;
        final int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (radius == 0.1) {
            blurRadius = (int) (radius * 001.f);
        } else
            blurRadius = (int) (radius * 25.f);
        if (blurRadius == 0) return;

        isBlurred = true; // should be called before Util.drawViewToBitmap (or you can bump into recursion)

        if (bitmap == null) bitmap = drawViewToBitmap(this, width, height, 1);
        final Bitmap blurred = apply(getContext(), bitmap, blurRadius);
        setImageBitmap(blurred);

        if (!keepOriginal) {
            bitmap.recycle();
            bitmap = null;
        }
    }


    public static Bitmap drawViewToBitmap(View view, int width, int height, int downSampling) {
        return drawViewToBitmap(view, width, height, 0f, 0f, downSampling);
    }

    public static Bitmap drawViewToBitmap(View view, int width, int height, float translateX,
                                          float translateY, int downSampling) {
        float scale = 1f / downSampling;
        int bmpWidth = (int) (width * scale - translateX / downSampling);
        int bmpHeight = (int) (height * scale - translateY / downSampling);
        Bitmap dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        c.translate(-translateX / downSampling, -translateY / downSampling);
        if (downSampling > 1) {
            c.scale(scale, scale);
        }
        view.draw(c);
        return dest;
    }

    public static Bitmap apply(Context context, Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

        // clean up renderscript resources
        rs.destroy();
        input.destroy();
        output.destroy();
        script.destroy();

        return bitmap;
    }
}
