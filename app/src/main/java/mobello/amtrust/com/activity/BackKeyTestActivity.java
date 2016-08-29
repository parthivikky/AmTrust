package mobello.amtrust.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobello.amtrust.com.R;

public class BackKeyTestActivity extends AppCompatActivity {
    private TextView title;
    private LinearLayout countDownLinearLayout;
    private TextView countDownTextView;
    private CountDownTimer countDownTimer;
    private Button stopButton;
    private ImageView testResultImageView;
    private boolean testIsStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_key_test);

        initUI();
    }

    private void initUI() {
        title = (TextView) findViewById(R.id.title);
        title.setText("Back key");
        countDownLinearLayout = (LinearLayout) findViewById(R.id.count_down_layer);
        countDownTextView = (TextView) findViewById(R.id.count_down_text_view);
        stopButton = (Button) findViewById(R.id.stop_button);
        testResultImageView = (ImageView) findViewById(R.id.test_result_image);
    }

    public void testBackKey(View view) {
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

    @Override
    public void onBackPressed() {
        if (testIsStarted) {
            testResultImageView.setVisibility(View.VISIBLE);//show correct(right tick) image
            countDownTimer.cancel();//stop the timer
            setResultAndFinish();
        } else
            super.onBackPressed();
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
