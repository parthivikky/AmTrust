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

    public static final String BATTERY_CHARGING_TEST = "Battery Charging";
    public static final String BATTERY_TEMPERATURE_TEST = "Battery Temperature";
    public static final String CALL_RECEIVER_TEST = "Call Receiver";
    public static final String HEADSET_TEST = "Headset";
    public static final String HEADSET_MICROPHONE_TEST = "Headset Microphone";
    public static final String HEADSET_BUTTON_TEST = "Headset Button";
    public static final String MICROPHONE_TEST = "Microphone";
    public static final String SPEAKER = "Speaker";
    public static final String BACK_CAMERA_TEST = "Back Camera";
    public static final String CAMERA_FLASH_TEST = "Camera Flash";
    public static final String FRONT_CAMERA_TEST = "Front Camera";
    public static final String VIDEO_TEST = "Video";
    public static final String AUTO_FOCUS_TEST = "Auto Focus";
    public static final String BLUETOOTH_TEST = "Bluetooth";
    public static final String CARRIER_SIGNAL_TEST = "Carrier Signal";
    public static final String CARRIER_SIGNAL_DUAL_TEST = "Carrier Signal Dual";
    public static final String WIFI_TEST = "Wifi";
    public static final String BACK_KEY_TEST = "Back Key";
    public static final String POWER_KEY_TEST = "Power Key";
    public static final String IMEI_VALIDATION_TEST = "IMEI Validation";
    public static final String IMEI_PRESENCE_TEST = "IMEI Presence";
    public static final String IMEI2_PRESENCE_TEST = "IMEI2 Presence";
    public static final String MAC_VALIDATION_TEST = "Mac Validation";
    public static final String MENU_KEY_TEST = "Menu Key";
    public static final String NFC_TEST = "NFC";
    public static final String VOLUME_KEYS_TEST = "Volume Keys";
    public static final String RECENT_APP_KEY_TEST = "Recent App Key";
    public static final String SEARCH_KEY_TEST = "Search Key";
    public static final String PERFORMANCE_TEST = "Performance";
    public static final String RAM_SIZE_TEST = "Ram Size";
    public static final String SD_CARD_TEST = "SD Card";
    public static final String VIBRATION_TEST = "Vibration";
    public static final String STORAGE_TEST = "Storage";
    public static final String LCD_BACKLIGHT_TEST = "LCD Backlight";
    public static final String LCD_COLOR_TEST = "LCD Color";
    public static final String SCREEN_TEST = "Screen";
    public static final String TOUCH_TEST = "Touch";
    public static final String GPS_CONNECT_TEST = "GPS Connect";
    public static final String ACCELEROMETER_TEST = "Accelerometer";
    public static final String COMPASS_TEST = "Compass";
    public static final String LIGHT_TEST = "Light";
    public static final String PROXIMITY_TEST = "Proximity";


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
