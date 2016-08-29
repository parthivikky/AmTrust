package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.HeadSetEvent;
import mobello.amtrust.com.model.TestEvent;
import mobello.amtrust.com.receiver.HeadSetStateReceiver;
import mobello.amtrust.com.receiver.TestReceiver;

public class HeadsetTestActivity extends AppCompatActivity {

    public static void startForResult(Activity activity, int request_code) {
        activity.startActivityForResult(new Intent(activity, HeadsetTestActivity.class), request_code);
    }

    private static boolean isTestStarted = false;

    private TextView txtAbout, txtInstruction, txtCountDown;
    private TestReceiver testReceiver;
    private ImageView imgHeader;
    private LinearLayout tickContainer, countDownContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_to_all_test);
        initUI();
        testReceiver = new TestReceiver();
    }

    private void initUI() {
        ((TextView) findViewById(R.id.title)).setText("HeadSet");
        txtCountDown = _findViewById(R.id.txt_count_down);
        tickContainer = _findViewById(R.id.tickContainer);
        countDownContainer = _findViewById(R.id.count_down_container);
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }


    @Subscribe
    public void onTestEvent(TestEvent event) {
        if (event.intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            switch (event.intent.getIntExtra("state", -1)) {
                case 0:
                    isTestStarted = true;
                    break;
                case 1:
                    if (isTestStarted) {
                        unregisterReceiver(testReceiver);
                        EventBus.getDefault().unregister(this);
                        showTick();
                    }
                    break;
            }
        }
    }

    public void onTestClick(View view) {
        view.setVisibility(View.GONE);
        countDownContainer.setVisibility(View.VISIBLE);
        this.registerReceiver(testReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        EventBus.getDefault().register(this);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                txtCountDown.setText("" + l / 1000);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    public void showTick() {
        tickContainer.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tickContainer.setVisibility(View.GONE);
                setResult(RESULT_OK);
                finish();
            }
        }, 2000);
    }

    public void onStopClick(View view) {
        finish();
    }


}
