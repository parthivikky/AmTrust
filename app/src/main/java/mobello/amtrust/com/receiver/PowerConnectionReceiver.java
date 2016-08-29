package mobello.amtrust.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import mobello.amtrust.com.model.BatteryChargingEvent;

/**
 * Created by Vicky on 6/2/2016.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        EventBus.getDefault().post(new BatteryChargingEvent("PowerSourcePlugged"));
    }
}
