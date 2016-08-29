package mobello.amtrust.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import mobello.amtrust.com.model.TestEvent;

/**
 * Created by Parthi on 22-Aug-16.
 */
public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new TestEvent(intent));
    }
}
