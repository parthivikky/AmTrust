package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.BatteryChargingEvent;

public class BatteryChargingTestActivity extends AppCompatActivity {

    private TextView title;
    private TextView countDownTextView;
    private Button stopButton;
    private LinearLayout countDownLinearLayout;
    private CountDownTimer countDownTimer;
    private boolean testIsStarted = false;
    private ImageView testResultImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_to_all_test);
        initUI();
    }

    private void initUI() {
        title = (TextView) findViewById(R.id.title);
        title.setText("Battery Charging");
        countDownTextView = (TextView) findViewById(R.id.count_down_text_view);
        stopButton = (Button) findViewById(R.id.stop_button);
        countDownLinearLayout = (LinearLayout) findViewById(R.id.count_down_layer);
        testResultImageView = (ImageView) findViewById(R.id.test_result_image);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onBatteryChargingEvent(BatteryChargingEvent event) {
        if (testIsStarted)
            checkBatteryState();
    }

    public void onTestClick(View view) {// start testing the battery charging
        testIsStarted = true;
        startCounting();
        view.setVisibility(View.GONE);//hide the test now button
        countDownLinearLayout.setVisibility(View.VISIBLE);//show the countdown and stop button
    }

    private void startCounting() {

        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTextView.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                finish();
            }
        };
        countDownTimer.start();
    }

    public void checkBatteryState() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);

        int chargeState = 0;
        if (batteryStatus != null) {
            chargeState = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        }
        String strState;

        switch (chargeState) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
            case BatteryManager.BATTERY_STATUS_FULL:
                strState = "charging";
                testResultImageView.setVisibility(View.VISIBLE);//show correct(right tick) image
                countDownTimer.cancel();//stop the timer
                setResultAndFinish();
                break;
            default:
                strState = "not charging";
        }

        //Toast.makeText(BatteryChargingTestActivity.this, strState, Toast.LENGTH_SHORT).show();

    }

    public void onStop(View view) {//called when stop button is clicked
        countDownTimer.cancel();
        finish();
    }

    private void setResultAndFinish() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}

