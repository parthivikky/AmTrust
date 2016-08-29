package mobello.amtrust.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import mobello.amtrust.com.model.HeadSetButtonEvent;

/**
 * Created by Vicky on 5/31/2016.
 */
public class HeadSetButtonStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event != null) {
                int action = event.getAction();
                /*if (action == KeyEvent.KEYCODE_HEADSETHOOK) {*/
                Log.d("KEY ACTION : ", "Action =" + action);
                Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new HeadSetButtonEvent("1"));
            }
        }
    }
}
