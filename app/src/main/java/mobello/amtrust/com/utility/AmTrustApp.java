package mobello.amtrust.com.utility;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 19-May-16.
 */
@ReportsCrashes(
        customReportContent = { ReportField.BRAND,ReportField.PHONE_MODEL,ReportField.ANDROID_VERSION,
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,  ReportField.STACK_TRACE,
                ReportField.CRASH_CONFIGURATION, ReportField.USER_CRASH_DATE},
        reportType = HttpSender.Type.FORM,
        httpMethod = HttpSender.Method.POST,
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_report_message)
public class AmTrustApp extends Application {

    public static AmTrustApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Iconify.with(new FontAwesomeModule());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
        MultiDex.install(this);
    }

    public static AmTrustApp getInstance(){
        return instance;
    }
}
