package mobello.amtrust.com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.HeadSetMicrophoneEvent;
import mobello.amtrust.com.model.TestEvent;
import mobello.amtrust.com.receiver.HeadSetMicrophoneStateReceiver;
import mobello.amtrust.com.receiver.TestReceiver;
import mobello.amtrust.com.utility.Helper;

public class HeadSetMicrophoneTestActivity extends AppCompatActivity {

    public static void startForResult(Activity activity, int request_code) {
        activity.startActivityForResult(new Intent(activity, HeadSetMicrophoneTestActivity.class), request_code);
    }


    private static final int RECORD_AUDIO_PERMISSIONS_REQUEST_CODE = 10;

    private TextView txtAbout, txtInstruction, txtCountDown;
    private LinearLayout tickContainer, countDownContainer;
    private TestReceiver testReceiver;
    private boolean isMicrophoneAvailable = false, isHeadSetConnected = false;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_to_all_test);
        initUI();
        testReceiver = new TestReceiver();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSIONS_REQUEST_CODE);
    }

    private void initUI() {
        ((TextView) findViewById(R.id.title)).setText("Headset Microphone");
        txtCountDown = _findViewById(R.id.txt_count_down);
        tickContainer = _findViewById(R.id.tickContainer);
        countDownContainer = _findViewById(R.id.count_down_container);
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    @Override
    public void onStart() {
        registerReceiver(testReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        EventBus.getDefault().register(this);
        super.onStart();
    }


    @Override
    public void onStop() {
        unregisterReceiver(testReceiver);
        EventBus.getDefault().unregister(this);
        if (timer != null)
            timer.cancel();
        super.onStop();
    }

    @Subscribe
    public void onTestEvent(TestEvent event) {
        if (event.intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            Log.i("isHeadSetConnected",isHeadSetConnected + "");
            switch (event.intent.getIntExtra("state", -1)) {
                case 0:
                    isHeadSetConnected = false;
                    break;
                case 1:
                    isHeadSetConnected = true;
                    break;
            }
            Log.i("isHeadSetConnected",isHeadSetConnected + "");
        }
    }

    private void startSpeech() {
        try {
            final MediaRecorder recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/dev/null");
            recorder.prepare();
            recorder.start();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (recorder.getMaxAmplitude() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recorder.stop();
                                showTick();
                            }
                        });
                    }
                }
            }, 0, 1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(HeadSetMicrophoneTestActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
            break;
        }
    }

    public void onStopClick(View view) {
        finish();
    }

    public void onTestClick(View view) {
        if (isHeadSetConnected) {
            view.setVisibility(View.GONE);
            countDownContainer.setVisibility(View.VISIBLE);
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
            startSpeech();
        } else {
            Helper.showMessageToast(this, "Please conncet your headset");
        }
    }
}
