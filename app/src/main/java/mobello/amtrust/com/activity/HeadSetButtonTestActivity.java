package mobello.amtrust.com.activity;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.HeadSetButtonEvent;
import mobello.amtrust.com.receiver.HeadSetButtonStateReceiver;

public class HeadSetButtonTestActivity extends AppCompatActivity {

    //private HeadSetButtonStateReceiver mHeadSetButtonStateReceiver;
    private ComponentName mHeadSetButtonStateReceiver;
    private ImageView right;
    private TextView title;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_set_button_test);

        initUI();

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mHeadSetButtonStateReceiver = new ComponentName(this, HeadSetButtonStateReceiver.class);

    }

    private void initUI() {
        right = (ImageView) findViewById(R.id.right);
        title = (TextView) findViewById(R.id.title);
        title.setText("Headset Button");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        /*IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
        filter.setPriority(2147483647);
        registerReceiver(mHeadSetButtonStateReceiver, filter);*/
        super.onResume();
        am.registerMediaButtonEventReceiver(mHeadSetButtonStateReceiver);

    }

    @Override
    public void onPause() {
        /*unregisterReceiver(mHeadSetButtonStateReceiver);*/
        am.unregisterMediaButtonEventReceiver(mHeadSetButtonStateReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onHeadSetButtonEvent(HeadSetButtonEvent event) {
        if (event.message.equals("1"))
            right.setImageResource(R.drawable.right);
    }
}
