package mobello.amtrust.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import mobello.amtrust.com.model.HeadSetMicrophoneEvent;


/**
 * Created by Vicky on 5/30/2016.
 */
public class HeadSetMicrophoneStateReceiver extends BroadcastReceiver {

    private String pluggedState = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    //Headset is unplugged
                    pluggedState = "0";
                    break;
                case 1:
                    //Headset is plugged
                    pluggedState = "1";
                    break;
                default:
                    pluggedState = "I have no idea what the headset state is";
            }
            EventBus.getDefault().post(new HeadSetMicrophoneEvent(pluggedState));
        }
    }
}
