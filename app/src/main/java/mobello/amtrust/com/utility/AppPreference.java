package mobello.amtrust.com.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MobelloTech on 14-07-2015.
 */
public class AppPreference {

    private static final String prefernce_name = "AMTRUST";

    public static final String SESSION_NAME = "session_name";

    public static final String IS_LOGIN = "is_login";

    public static final String EMAIL = "email";

    public static final String FIRSTNAME = "first_name";

    public static final String LASTNAME = "last_name";

    public static final String MOBILE = "mobile";

    public static final String IS_CHANGE_PASSWORD = "is_change_password";


    public static boolean getBoolean(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(prefernce_name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(prefernce_name, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(prefernce_name, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(prefernce_name, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void clearPreference(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(prefernce_name, Context.MODE_PRIVATE).edit();
        editor.clear().commit();
    }
}
