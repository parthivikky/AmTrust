package mobello.amtrust.com.utility;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import org.acra.ACRA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mobello.amtrust.com.widget.ProgressHUD;

/**
 * Created by Parthi on 18-May-16.
 */
public class Helper {

    public static ProgressHUD progressHUD;

    public static void showProgress(Context context) {
        if (context != null) {
            progressHUD = ProgressHUD.show(context, "Loading...", true, true);
            progressHUD.setCancelable(false);
        }
    }

    public static void dismissProgress() {
        progressHUD.dismiss();
    }

    public static String getCountryCode(Context context){
        if(context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String countryIso = telephonyManager.getSimCountryIso();
            return Iso2Phone.getPhone(countryIso);
        }
        return  null;
    }

    public static String getIMEINumber(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static void showMessageToast(Context context, String msg) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void sendReport(Context context , Exception exception){
        if(context != null) {
            BugSender bugReportSender = new BugSender(context, exception);
            ACRA.getErrorReporter().setReportSender(bugReportSender);
        }
    }

    public static final String convertMd5(final String key) {
        try {
            if (key != null) {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(key.getBytes());
                byte messageDigest[] = digest.digest();

                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        else
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
